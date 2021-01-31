package com.dsmf.suivie.jeuf.processor;

import org.springframework.batch.item.ItemProcessor;

import com.dsmf.suivie.jeuf.model.Sass;

public class SassProcessor implements ItemProcessor<Sass, Sass>{
	
	@Override
	public Sass process(Sass item) throws Exception {
		
		return item;
	}

}

