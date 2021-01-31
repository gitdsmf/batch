package com.dsmf.suivie.jeuf.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@XmlRootElement(name = "ZONE")
@Data
public class Zone {

	private Long identifiant;
	
	@XmlElement(name = "nom")
	private String nom;
	
	@XmlElement(name = "idKourel")
	private Long idKourel;
}
