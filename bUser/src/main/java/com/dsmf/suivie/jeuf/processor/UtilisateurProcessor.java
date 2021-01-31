package com.dsmf.suivie.jeuf.processor;

import java.util.Date;
import java.util.List;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.dsmf.suivie.jeuf.dao.IUtilisateurDao;
import com.dsmf.suivie.jeuf.model.Utilisateur;
import com.dsmf.suivie.jeuf.utils.UserUtils;

public class UtilisateurProcessor implements ItemProcessor<Utilisateur, Utilisateur>{
	
	final static String URLPROD = "http://46.30.203.33:4200/";
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	IUtilisateurDao utilisateurDao;
	@Override
	public Utilisateur process(Utilisateur item) throws Exception {
		List<Utilisateur> listeUtilisateur = utilisateurDao.getUsersByEmail(item.getUsername());
		if (listeUtilisateur.isEmpty()) {
			SimpleMailMessage mail = new SimpleMailMessage();
			String RandomPassword = UserUtils.generateRandomString(6);
			StringBuilder sbd = new StringBuilder();
			mail.setFrom("noreply.dsmf.notification@gmail.com");
			mail.setTo(item.getUsername());
			mail.setSentDate(new Date());
			mail.setSubject("Vos identifiants de connexion");
			sbd.append("Connecter vous à l'application suivi Jeuff : ").append("\n");
			//sbd.append(InetAddress.getLocalHost()).append(":4200").append("\n");
			sbd.append(URLPROD).append("\n").append("\n");
			sbd.append("Identifiant : ").append(item.getUsername()).append("\n");
			sbd.append("Mot de passe : ").append(RandomPassword);
			mail.setText(sbd.toString());
			javaMailSender.send(mail);
			item.setPassword(UserUtils.generateBCrypt(RandomPassword));		
		} else {
			Utilisateur utilisateur = listeUtilisateur.get(0);
			item.setPassword(utilisateur.getPassword());
		}
		
		return item;
	}

}

