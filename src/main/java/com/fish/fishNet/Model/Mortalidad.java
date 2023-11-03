package com.fish.fishNet.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "mortalidad")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Mortalidad extends Base {

    @Column(name = "animales_muertos", nullable = false)
    private Integer animalesMuertos;

    @Column(name = "observacion", nullable = false)
    private String observacion;


    // Relaciones
    // Lote
    @ManyToOne(optional = false)
    @JoinColumn(name = "lote_id", nullable = false, foreignKey = @ForeignKey(name = "fk_mortalidad_lote"))
    private Lote lote;

}
