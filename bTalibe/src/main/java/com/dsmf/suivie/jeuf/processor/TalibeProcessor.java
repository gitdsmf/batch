package com.dsmf.suivie.jeuf.processor;

import org.springframework.batch.item.ItemProcessor;

import com.dsmf.suivie.jeuf.model.Talibe;

public class TalibeProcessor implements ItemProcessor<Talibe, Talibe>{

	@Override
	public Talibe process(Talibe item) throws Exception {
		// TODO Auto-generated method stub
		return item;
	}

}

