package com.dsmf.suivie.jeuf.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.dsmf.suivie.jeuf.model.Jeuff;

public class JeuffRowMapper implements RowMapper<Jeuff> {

	@Override
	public Jeuff mapRow(ResultSet rs, int rowNum) throws SQLException {   
		
		Jeuff jeuff = new Jeuff();
		jeuff.setIdTalibe(Long.valueOf(rs.getString("ID_TALIBE")));
		//jeuff.setIdJeuff(Long.valueOf(rs.getString("ID_TALIBE")));
		jeuff.setAnneeEvenement("2020");
		jeuff.setDevise("FCFA");
		jeuff.setIdNdigueul(1L);
		jeuff.setMontantJeuff(Double.valueOf(rs.getString("REALMAGAL")));
		jeuff.setIdPercepteur(1L);
		jeuff.setModeEnvoi("REMISE_MAIN_PROPRE");
	    jeuff.setEtatValidationJeuff("VALIDE");
		try {
			Date dateJeuf = new SimpleDateFormat("yyyy-MM-dd").parse("2020-05-31");
			jeuff.setDateJeuff(dateJeuf);
		} catch (ParseException e) {
			e.printStackTrace();
		}  
		
		
		return jeuff;
	}
 

}
