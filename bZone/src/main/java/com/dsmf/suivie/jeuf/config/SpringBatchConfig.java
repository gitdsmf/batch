package com.dsmf.suivie.jeuf.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.dsmf.suivie.jeuf.model.Zone;
import com.dsmf.suivie.jeuf.processor.ZoneProcessor;


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
    public FlatFileItemReader<Zone> reader() {
        FlatFileItemReader<Zone> reader = new FlatFileItemReader<Zone>();
       reader.setResource(new ClassPathResource("fichier-zone.csv"));
        //reader.setResource(new FileSystemResource("C:/workspaces/csv/fichier-zone.csv"));
        reader.setLineMapper(new DefaultLineMapper<Zone>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] {"nom", "idKourel"});
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper() {{
                setTargetType(Zone.class);
            }});
        }});
        return reader;
    }


    @Bean
    public ZoneProcessor processor() {
        return new ZoneProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Zone> writer() {
        JdbcBatchItemWriter<Zone> writer = new JdbcBatchItemWriter<Zone>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
//        writer.setSql("INSERT INTO zone (NOM,KOUREL) " +
//                "VALUES (:nom, :idKourel)");
        writer.setSql("INSERT INTO zone (NOM,KOUREL) " +
                "VALUES (:nom, :idKourel)" + "ON DUPLICATE KEY UPDATE "
        		+ " nom=:nom,"
        		+ " KOUREL=:idKourel ");
        writer.setDataSource(dataSource);
        return writer;
    }
    
	
    @Bean
    public Job importZoneJob() {
    	  return jobBuilderFactory.get("importZoneJob")
    	    .incrementer(new RunIdIncrementer())
    	    .flow(step1())
    	    .end()
    	    .build();
    	 }

    @Bean
    public Step step1() {
     return stepBuilderFactory.get("step1").<Zone, Zone> chunk(3)
       .reader(reader())
       .processor(processor())
       .writer(writer())
       .build();
    }


}
