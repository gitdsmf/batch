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

import com.dsmf.suivie.jeuf.mapper.JeuffRowMapper;
import com.dsmf.suivie.jeuf.model.Jeuff;
import com.dsmf.suivie.jeuf.processor.JeuffProcessor;


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
	public ItemStreamReader<Jeuff> reader() {
		JdbcCursorItemReader<Jeuff> reader = new JdbcCursorItemReader<Jeuff>();
		reader.setDataSource(dataSource);
		reader.setSql("SELECT DISTINCT ID_TALIBE, REALMAGAL FROM TALIBE");
		reader.setRowMapper(new JeuffRowMapper());
		return reader;
	}

    @Bean
    public JeuffProcessor processor() {
        return new JeuffProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Jeuff> writer() {
        JdbcBatchItemWriter<Jeuff> writer = new JdbcBatchItemWriter<Jeuff>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO jeuff (annee_evenement, date_jeuff, devise,etat_validation_jeuff ,mode_envoi , montant_jeuff,ndigueul,percepteur,talibe) " +
                "VALUES (:anneeEvenement, :dateJeuff, :devise, :etatValidationJeuff, :modeEnvoi, :montantJeuff, :idNdigueul, :idPercepteur, :idTalibe)");
        writer.setDataSource(dataSource);
        return writer;
    }
    
	
    @Bean
    public Job importUTILISATEURJob() {
    	  return jobBuilderFactory.get("importJeuffJob")
    	    .incrementer(new RunIdIncrementer())
    	    .flow(step1())
    	    .end()
    	    .build();
    	 }

    @Bean
    public Step step1() {
     return stepBuilderFactory.get("step1").<Jeuff, Jeuff> chunk(3)
       .reader(reader())
       .processor(processor())
       .writer(writer())
       .build();
    }

}
