package com.dsmf.suivie.jeuf.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@XmlRootElement(name = "TALIBE")
@Data
public class Talibe {

	private Long identifiant;
	
	@XmlElement(name = "nom")
	private String nom;
	
	@XmlElement(name = "prenom")
	private String prenom;
	
	@XmlElement(name = "email")
	private String email;
	
	@XmlElement(name = "isDieuwrigneDaara")
	private int isDieuwrigneDaara;
	
	@XmlElement(name = "isPercepteur")
	private int isPercepteur;
	
	@XmlElement(name = "idDaara")
	private Long idDaara;
	
}
