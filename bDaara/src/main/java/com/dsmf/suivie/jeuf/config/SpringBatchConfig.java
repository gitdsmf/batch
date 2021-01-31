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

import com.dsmf.suivie.jeuf.model.Daara;
import com.dsmf.suivie.jeuf.processor.DaaraProcessor;


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
    public FlatFileItemReader<Daara> reader() {
        FlatFileItemReader<Daara> reader = new FlatFileItemReader<Daara>();
        reader.setResource(new ClassPathResource("fichier-daara.csv"));
        //reader.setResource(new FileSystemResource("C:/workspaces/csv/fichier-daara.csv"));

        reader.setLineMapper(new DefaultLineMapper<Daara>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] {"nom", "pays","espace", "idZone"});
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper() {{
                setTargetType(Daara.class);
            }});
        }});
        return reader;
    }


    @Bean
    public DaaraProcessor processor() {
        return new DaaraProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Daara> writer() {
        JdbcBatchItemWriter<Daara> writer = new JdbcBatchItemWriter<Daara>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO Daara (NOM,PAYS,ZONE) " +
                "VALUES (:nom, :pays, :idZone)" + "ON DUPLICATE KEY UPDATE "
        		+ " NOM=:nom,"
        		+ " PAYS=:pays, "
        		+ " ZONE=:idZone ");
        writer.setDataSource(dataSource);
        return writer;
    }
    
	
    @Bean
    public Job importDaaraJob() {
    	  return jobBuilderFactory.get("importDaaraJob")
    	    .incrementer(new RunIdIncrementer())
    	    .flow(step1())
    	    .end()
    	    .build();
    	 }

    @Bean
    public Step step1() {
     return stepBuilderFactory.get("step1").<Daara, Daara> chunk(3)
       .reader(reader())
       .processor(processor())
       .writer(writer())
       .build();
    }
//    @Bean
//    public Step step1() {
//     return stepBuilderFactory.get("step1").<Daara, Daara> chunk(3)
//       .reader(reader())
//       .processor(processor())
//       .writer(writer())
//       .build();
//    }


}
