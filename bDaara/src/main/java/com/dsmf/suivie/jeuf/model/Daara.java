package com.dsmf.suivie.jeuf.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@XmlRootElement(name = "Daara")
@Data
public class Daara {

	private Long identifiant;
	
	@XmlElement(name = "nom")
	private String nom;
	
	@XmlElement(name = "idZone")
	private Long idZone;
	
	@XmlElement(name = "pays")
	private String pays;
	
	@XmlElement(name = "espace")
	private String espace;
}
