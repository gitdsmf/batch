package com.dsmf.suivie.jeuf.processor;

import org.springframework.batch.item.ItemProcessor;

import com.dsmf.suivie.jeuf.model.Jeuff;

public class JeuffProcessor implements ItemProcessor<Jeuff, Jeuff>{
	
	@Override
	public Jeuff process(Jeuff item) throws Exception {
		
		return item;
	}

}

