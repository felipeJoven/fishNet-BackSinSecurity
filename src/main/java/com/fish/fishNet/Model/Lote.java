package com.fish.fishNet.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "lote")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Lote extends Base {
    
    @Column(name = "nombre_lote", nullable = false)
    private String nombreLote;

    @Column(name = "numero_animales", nullable = false)
    private int  numeroAnimales;

    @Column(name = "fecha_siembra", nullable = false)
    private LocalDate fechaSiembra;

    @Column(name = "animales_inicial")
    private Integer animalesInicial;

    @Transient
    private Long diasDeSiembra;


    // Relaciones

    // Proveedor
    @ManyToOne(optional = false)
    @JoinColumn(name = "proveedor_id", nullable = false, foreignKey = @ForeignKey(name = "fk_lote_proveedor"))
    private Proveedor proveedor;

    // Especies
    @ManyToOne(optional = false)
    @JoinColumn(name = "especies_id", nullable = false, foreignKey = @ForeignKey(name = "fk_lote_especies"))
    private Especies especies;

    // Unidad productiva
    @ManyToOne(optional = false)
    @JoinColumn(name = "unidad_productiva_id", nullable = false, foreignKey = @ForeignKey(name = "fk_lote_unidad_productiva"))
    private UnidadProductiva unidadProductiva;

}
