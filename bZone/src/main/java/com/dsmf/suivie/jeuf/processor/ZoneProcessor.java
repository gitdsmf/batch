package com.dsmf.suivie.jeuf.processor;

import org.springframework.batch.item.ItemProcessor;

import com.dsmf.suivie.jeuf.model.Zone;

public class ZoneProcessor implements ItemProcessor<Zone, Zone>{

	@Override
	public Zone process(Zone item) throws Exception {
		// TODO Auto-generated method stub
		return item;
	}

}

