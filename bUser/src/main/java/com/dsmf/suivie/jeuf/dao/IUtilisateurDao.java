package com.dsmf.suivie.jeuf.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dsmf.suivie.jeuf.model.Utilisateur;

@Repository
public interface IUtilisateurDao extends JpaRepository<Utilisateur, Long> {
	@Query(" SELECT u " 
			+ " FROM Utilisateur u " 
			+ " WHERE u.username =	?1 ")
	public List<Utilisateur> getUsersByEmail(String email);

}
