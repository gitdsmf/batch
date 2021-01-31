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

import com.dsmf.suivie.jeuf.model.Talibe;
import com.dsmf.suivie.jeuf.processor.TalibeProcessor;


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
    public FlatFileItemReader<Talibe> reader() {
        FlatFileItemReader<Talibe> reader = new FlatFileItemReader<Talibe>();
        reader.setResource(new ClassPathResource("fichier-talibe.csv"));
        //reader.setResource(new FileSystemResource("C:/workspaces/csv/fichier-talibe.csv"));

        reader.setLineMapper(new DefaultLineMapper<Talibe>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] {"prenom", "nom","email", "isDieuwrigneDaara","isPercepteur", "idDaara"});

            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper() {{
                setTargetType(Talibe.class);
            }});
        }});
        return reader;
    }


    @Bean
    public TalibeProcessor processor() {
        return new TalibeProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Talibe> writer() {
        JdbcBatchItemWriter<Talibe> writer = new JdbcBatchItemWriter<Talibe>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO TALIBE (PRENOM,NOM,EMAIL,IS_DIEUWRIGNE_DAARA,IS_PERCEPTEUR,DAARA) " +
                "VALUES (:prenom, :nom,:email, :isDieuwrigneDaara, :isPercepteur, :idDaara)" + "ON DUPLICATE KEY UPDATE "
        		+ " PRENOM=:prenom,"
        		+ " NOM=:nom, "
        		+ " EMAIL=:email ");
        writer.setDataSource(dataSource);
        return writer;
    }
    
	
    @Bean
    public Job importTalibeJob() {
    	  return jobBuilderFactory.get("importTalibeJob")
    	    .incrementer(new RunIdIncrementer())
    	    .flow(step1())
    	    .end()
    	    .build();
    	 }

    @Bean
    public Step step1() {
     return stepBuilderFactory.get("step1").<Talibe, Talibe> chunk(3)
       .reader(reader())
       .processor(processor())
       .writer(writer())
       .build();
    }
//    @Bean
//    public Step step1() {
//     return stepBuilderFactory.get("step1").<Zone, Zone> chunk(3)
//       .reader(reader())
//       .processor(processor())
//       .writer(writer())
//       .build();
//    }


}
