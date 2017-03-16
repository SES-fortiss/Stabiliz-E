#!/usr/bin/env python
import pika
import uuid
import json

class FortissRpcClient(object):
    def __init__(self, queue):
        self.connection = pika.BlockingConnection(pika.ConnectionParameters(
            host="localhost"))

        self.channel = self.connection.channel()

        result = self.channel.queue_declare(exclusive=True)
        self.callback_queue = result.method.queue
        self.queue = queue

        self.channel.basic_consume(self.on_response, no_ack=True,
                queue=self.callback_queue)

    def on_response(self, ch, method, props, body):
        if self.corr_id == props.correlation_id:
            self.response = body

    def destroy(self):
        print("Destroy")
        self.channel.stop_consuming()
        self.connection.close()

    def call(self, method, params):
        self.response = None
        self.corr_id = str(uuid.uuid4())
        mbody = {"id":1,"method": method,"version":"1.2","params": params}
        print(mbody)
        self.channel.basic_publish(exchange='',
                routing_key=self.queue,
                properties=pika.BasicProperties(
                    reply_to = self.callback_queue,
                    correlation_id = self.corr_id,
                    ),
                body=str(mbody))
        while self.response is None:
            self.connection.process_data_events()
        data = json.loads(self.response.decode('UTF-8'))
        return data["result"]