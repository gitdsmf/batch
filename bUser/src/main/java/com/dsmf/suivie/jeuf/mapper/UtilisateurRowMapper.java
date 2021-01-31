package com.dsmf.suivie.jeuf.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.dsmf.suivie.jeuf.model.Utilisateur;

public class UtilisateurRowMapper implements RowMapper<Utilisateur> {

	@Override
	public Utilisateur mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Utilisateur utilisateur = new Utilisateur();
		String roleName = "USER";
		utilisateur.setUserId(Long.valueOf(rs.getString("ID_TALIBE")));
		utilisateur.setUsername(rs.getString("EMAIL"));
		if ("1".equals(rs.getString("IS_DIEUWRIGNE_DAARA"))) {
			roleName = "ADMIN";
		}
		utilisateur.setRole(roleName);
		utilisateur.setActivate(true);
		utilisateur.setIdTalibe(Long.valueOf(rs.getString("ID_TALIBE")));
		
		return utilisateur;
	}
 

}
