package com.evoke.demo.employee;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheEventLogger implements CacheEventListener<Object, Object> {
	
	Logger log = LoggerFactory.getLogger(CacheEventLogger.class);

	@Override
	public void onEvent(CacheEvent<? extends Object, ? extends Object> cacheEvent) {

		log.info("Key: {} | EventType: {} | Old value: {} | New value: {}",
	             cacheEvent.getKey(), cacheEvent.getType(), cacheEvent.getOldValue(), 
	             cacheEvent.getNewValue());
		
	}

}
