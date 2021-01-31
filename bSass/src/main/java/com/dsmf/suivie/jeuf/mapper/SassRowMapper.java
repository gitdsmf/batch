package com.dsmf.suivie.jeuf.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.dsmf.suivie.jeuf.model.Sass;

public class SassRowMapper implements RowMapper<Sass> {

	@Override
	public Sass mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Sass sass = new Sass();
		sass.setIdTalibe(Long.valueOf(rs.getString("ID_TALIBE")));
		sass.setIdSass(Long.valueOf(rs.getString("ID_TALIBE")));
		sass.setAnneeEvenement("2020");
		sass.setDevise("FCFA");
		sass.setIdNdigueul(1L);
		sass.setMontantSass(Double.valueOf(rs.getString("sass")));
		
		return sass;
	}
 

}
