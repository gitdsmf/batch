package com.dsmf.suivie.jeuf.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@XmlRootElement(name = "JEUFF")
@Data
public class Jeuff {

	@XmlElement(name = "idJeuff")
	private Long idJeuff;
	
	@XmlElement(name = "idTalibe")
	private Long idTalibe;
	
	@XmlElement(name = "idNdigueul")
	private Long idNdigueul;
	
	@XmlElement(name = "anneeEvenement")
	private String anneeEvenement;
	
	@XmlElement(name = "dateJeuff")
	private Date dateJeuff;
	
	@XmlElement(name = "montantJeuff")
	private double montantJeuff;
	
	@XmlElement(name = "devise")
	private String devise;
	
	@XmlElement(name = "etatValidationJeuff")
	private String etatValidationJeuff;
	
	@XmlElement(name = "idPercepteur")
	private Long idPercepteur;
	
	@XmlElement(name = "modeEnvoi")
	private String modeEnvoi;
	
}
