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
@Table(name = "salida_alimentos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalidaAlimentos extends Base {
    
    @Column(name = "numero_factura", nullable = false)
    private String numeroFactura;
    
    @Column(name = "numero_kilos", nullable = false)
    private Integer kilosSalida;

    // Relaciones

    // Lote
    @ManyToOne(optional = false)
    @JoinColumn(name = "lote_id", nullable = false, foreignKey = @ForeignKey(name = "fk_salida_alimentos_lote"))
    private Lote lote;
    
}
