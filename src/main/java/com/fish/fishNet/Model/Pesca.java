package com.fish.fishNet.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pesca")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pesca extends Base {
    
    @Column(name="animales_pescados", nullable = false)
    private int animalesPescados;
    
    @Column(name="peso_promedio", nullable = false)
    private double pesoPromedio;

    // Lote
    @ManyToOne(optional = false)
    @JoinColumn(name = "lote_id", nullable = false, foreignKey = @ForeignKey(name = "fk_pesca_lote"))
    private Lote lote;
    
}
