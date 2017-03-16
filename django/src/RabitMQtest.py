import pika

try:
    unicode = unicode
except NameError:
    # 'unicode' is undefined, must be Python 3
    str = str
    unicode = str
    bytes = bytes
    basestring = (str,bytes)
else:
    # 'unicode' exists, must be Python 2
    str = str
    unicode = unicode
    bytes = str
    basestring = basestring

#! Establish the connection to our target
connection = pika.BlockingConnection(pika.ConnectionParameters('localhost'))
channel = connection.channel()

#! Creating the queue we'll use to send stuff with
channel.queue_declare(queue='hello')

#! Sending the text 'Hello World' to queue 'hello'
channel.basic_publish(exchange='',
                      routing_key='hello',
                      body='Hello World!')
#! Printing the information we just sent					  
print (" [x] Sent 'Hello World!'")

#! Closing the connection
connection.close()

