package com.fish.fishNet.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "unidad_productiva")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UnidadProductiva extends Base {
    
    @Column(name = "nombre_unidad_p", nullable = false)
    private String nombreUnidadP;
    
    @Column(name = "area", nullable = false)
    private String area;
    
    @Column(name = "coordenadas", nullable = false)
    private String coordenadas;

    @Column(name = "observacion", nullable = false)
    private String observacion;

    @Column(name = "profundidad", nullable = false)
    private String profundidad;

}