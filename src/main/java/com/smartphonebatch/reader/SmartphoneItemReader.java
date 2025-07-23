package com.smartphonebatch.reader;

import com.smartphonebatch.model.Smartphone;
import jakarta.annotation.PostConstruct;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class SmartphoneItemReader extends FlatFileItemReader<Smartphone> {

    @Value("${smartphone.filepath}")
    private String filePath;

    public SmartphoneItemReader() {
        // Ne fais rien ici pour éviter d'empêcher Spring de créer le bean
    }

    @PostConstruct
    public void init() throws IOException {
        System.out.println("Initialisation du Reader...");

        //String filePath = "C:/Users/<user>/IdeaProjects/smartphonebatch7 - CSV-BDD-controller-Thymeleaf JPA Angular/src/main/resources/smartphones.csv";
        // Chargement du fichier via le classpath
        ClassPathResource classpathresource = new ClassPathResource("smartphones.csv");
        String filePath = classpathresource.getFile().getAbsolutePath();
        System.out.println("Chemin du fichier : " + filePath);

        File file = new File(filePath);

        if (file.exists() && file.isFile()) {
            this.setResource(new FileSystemResource(filePath));
        } else {
            throw new IOException("Le fichier n'existe pas à l'emplacement donné.");
        }

        this.setLinesToSkip(1);

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(";");
        tokenizer.setNames("marque", "modele", "os", "annee", "tailleEcran", "prix");

        BeanWrapperFieldSetMapper<Smartphone> mapper = new BeanWrapperFieldSetMapper<>();
        mapper.setTargetType(Smartphone.class);

        DefaultLineMapper<Smartphone> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(mapper);

        this.setLineMapper(lineMapper);
    }
}