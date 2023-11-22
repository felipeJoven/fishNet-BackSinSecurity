package com.fish.fishNet.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "muestreo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Muestreo extends Base {

    @Column(name="peso_inicial", nullable = false)
    private double pesoInicial;

    @Column(name="peso_actual", nullable = false)
    private double pesoActual;

    // Lote
    @ManyToOne(optional = false)
    @JoinColumn(name = "lote_id", nullable = false, foreignKey = @ForeignKey(name = "fk_muestreo_lote"))
    private Lote lote;
}
