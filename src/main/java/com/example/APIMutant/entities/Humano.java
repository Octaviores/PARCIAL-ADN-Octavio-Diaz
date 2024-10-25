package com.example.APIMutant.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;


@Entity
@Table(name = "humano")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class Humano implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "nombre")
    private String nombre;


    @Column(name = "apellido")
    private String apellido;

    @Column(name = "DNA")
    private String DNA;

    @Column(name = "Mutant")
    private boolean isMutant;

    // Metodo para convertir String[] a cadena para almacenamiento
    public void setDNA(String[] dnaArray) {
        this.DNA = String.join(",", dnaArray);
    }

    // Metodo para convertir la cadena de vuelta a String[]
    public String[] getDNAArray() {
        return this.DNA != null ? this.DNA.split(",") : new String[0];
    }

}

