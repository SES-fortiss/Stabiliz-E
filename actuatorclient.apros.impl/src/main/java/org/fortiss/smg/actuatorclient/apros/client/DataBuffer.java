package org.fortiss.smg.actuatorclient.apros.client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class DataBuffer {
	private int inMemorySize;
	private int size;
	private boolean running;
	private List<Object> memoryBuff;
	//private FileBuff fileBuff;
	private List<Object> fileBuff;
	private ReentrantLock lock;
	private Condition empty;  
	
	public DataBuffer(int inMemorySize){
		this.inMemorySize = inMemorySize;
		this.size = 0;		
		this.memoryBuff = new ArrayList<Object>();
		//this.fileBuff = new FileBuff();
		this.fileBuff = new ArrayList<Object>();		
		this.lock = new ReentrantLock();
		this.empty = lock.newCondition(); 
		this.running = true; 
	}
	
	public int length(){
		return size;
	}
	
	public void push(Object item){
		lock.lock();
		if (memoryBuff.size() <= inMemorySize){
			memoryBuff.add(item);
		}else{
			fileBuff.add(memoryBuff.get(0));
			memoryBuff.remove(0);
			memoryBuff.add(item);
		}
		size++;     
		empty.signal();
		lock.unlock();		
	}
	
	public Object pop(){		
		lock.lock();		
		while (running && memoryBuff.size() == 0){			
			try {
				empty.awaitNanos(1000000000);
			} catch (InterruptedException e) {
				break;
			} 
		}
        if (!running) {return null;}        
        Object data = null;
        try{
        	data = fileBuff.get(0);
            fileBuff.remove(0);	
        }catch(Exception e){}
        
        if (data == null){
        	if (memoryBuff.size() > 0){
        		data = memoryBuff.get(0);	
        		memoryBuff.remove(0);
        	}        	
        }
        if (data != null){
        	this.size--;
        }
        lock.unlock();
        return data;
	}
	
	public void close(){
		running = false;
		//fileBuff.close();		
	}         
}
