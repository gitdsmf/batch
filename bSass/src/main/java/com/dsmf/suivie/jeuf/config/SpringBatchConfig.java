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

import com.dsmf.suivie.jeuf.mapper.SassRowMapper;
import com.dsmf.suivie.jeuf.model.Sass;
import com.dsmf.suivie.jeuf.processor.SassProcessor;


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
	public ItemStreamReader<Sass> reader() {
		JdbcCursorItemReader<Sass> reader = new JdbcCursorItemReader<Sass>();
		reader.setDataSource(dataSource);
		reader.setSql("SELECT DISTINCT ID_TALIBE, SASS FROM TALIBE");
		reader.setRowMapper(new SassRowMapper());
		return reader;
	}

    @Bean
    public SassProcessor processor() {
        return new SassProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Sass> writer() {
        JdbcBatchItemWriter<Sass> writer = new JdbcBatchItemWriter<Sass>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO SASS (ID_SASS,annee_evenement,devise,montant_sass,ndigueul,talibe) " +
                "VALUES (:idSass, :anneeEvenement, :devise, :montantSass, :idNdigueul, :idTalibe)" + "ON DUPLICATE KEY UPDATE "
        		+ " ID_SASS=:idSass");
        writer.setDataSource(dataSource);
        return writer;
    }
    
	
    @Bean
    public Job importUTILISATEURJob() {
    	  return jobBuilderFactory.get("importSassJob")
    	    .incrementer(new RunIdIncrementer())
    	    .flow(step1())
    	    .end()
    	    .build();
    	 }

    @Bean
    public Step step1() {
     return stepBuilderFactory.get("step1").<Sass, Sass> chunk(3)
       .reader(reader())
       .processor(processor())
       .writer(writer())
       .build();
    }

}
