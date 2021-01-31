package com.dsmf.suivie.jeuf.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table
@Data
public class Utilisateur {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_UTILISATEUR")
	private Long userId;

	private String username;

	private String password;

	private String role;
	
	@Column(name = "COMPTE_ACTIVE")
	private boolean activate;
	
	private Long idTalibe;
}
