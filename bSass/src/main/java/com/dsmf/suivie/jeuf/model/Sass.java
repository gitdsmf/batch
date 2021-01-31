package com.dsmf.suivie.jeuf.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@XmlRootElement(name = "Sass")
@Data
public class Sass {

	private Long identifiant;
	
	@XmlElement(name = "idSass")
	private Long idSass;
	
	@XmlElement(name = "montantSass")
	private double montantSass;
	
	@XmlElement(name = "devise")
	private String devise;
	
	@XmlElement(name = "idTalibe")
	private Long idTalibe;
	
	@XmlElement(name = "idNdigueul")
	private Long idNdigueul;
	
	@XmlElement(name = "anneeEvenement")
	private String anneeEvenement;
}
