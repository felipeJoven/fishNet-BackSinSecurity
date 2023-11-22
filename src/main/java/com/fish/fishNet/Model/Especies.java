package com.fish.fishNet.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "especies")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Especies extends Base {
   
    @Column(name="nombre_especie", nullable = false)
    private String nombreEspecie;

}
