package com.dsmf.suivie.jeuf.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

import com.dsmf.suivie.jeuf.mapper.UtilisateurRowMapper;
import com.dsmf.suivie.jeuf.model.Utilisateur;
import com.dsmf.suivie.jeuf.processor.UtilisateurProcessor;


@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {
    @Autowired
    EntityManagerFactory emf;
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;
    
	@Bean
	public ItemStreamReader<Utilisateur> reader() {
		JdbcCursorItemReader<Utilisateur> reader = new JdbcCursorItemReader<Utilisateur>();
		reader.setDataSource(dataSource);
		reader.setSql("SELECT DISTINCT ID_TALIBE, EMAIL, IS_DIEUWRIGNE_DAARA FROM TALIBE");
		reader.setRowMapper(new UtilisateurRowMapper());
		return reader;
	}

    @Bean
    public UtilisateurProcessor processor() {
        return new UtilisateurProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Utilisateur> writer() {
        JdbcBatchItemWriter<Utilisateur> writer = new JdbcBatchItemWriter<Utilisateur>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO UTILISATEUR (ID_UTILISATEUR,USERNAME,PASSWORD,ROLE,COMPTE_ACTIVE,ID_TALIBE) " +
                "VALUES (:userId, :username, :password, :role, :activate, :idTalibe)" + "ON DUPLICATE KEY UPDATE "
        		+ " USERNAME=:username,"
        		+ " PASSWORD=:password, "
        		+ " ROLE=:role ");
        writer.setDataSource(dataSource);
        return writer;
    }
    
	
    @Bean
    public Job importUTILISATEURJob() {
    	  return jobBuilderFactory.get("importUTILISATEURJob")
    	    .incrementer(new RunIdIncrementer())
    	    .flow(step1())
    	    .end()
    	    .build();
    	 }

    @Bean
    public Step step1() {
     return stepBuilderFactory.get("step1").<Utilisateur, Utilisateur> chunk(3)
       .reader(reader())
       .processor(processor())
       .writer(writer())
       .build();
    }

}
