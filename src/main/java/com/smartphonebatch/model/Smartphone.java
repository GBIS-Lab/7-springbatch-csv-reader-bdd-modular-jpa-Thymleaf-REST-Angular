package com.smartphonebatch.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "smartphones")
@Data
public class Smartphone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String marque;
    private String modele;
    //private String capacite;
    private String os;

    @Column(name = "annee_sortie")
    private Integer annee;

    private double tailleEcran;
    private double prix;
}
