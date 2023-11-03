package com.fish.fishNet.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "proveedor")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Proveedor extends Base {

    @Column(name = "nombre", nullable = false)
    private String nombre;
    
    @Column(name = "apellido", nullable = false)
    private String apellido;

    @Column(name = "telefono", nullable = false)
    private Integer telefono;

    @Column(name = "correo", nullable = false)
    private String correo;

    @Column(name = "direccion", nullable = false)
    private String direccion;

    @Column(name = "razon_social", nullable = false)
    private String razonSocial;

    @Column(name = "numero_identificacion", nullable = false)
    private Integer numeroIdentificacion;


    // Relaciones

    // Tipo de Proveedor
    @ManyToOne(optional = false)
    @JoinColumn(name = "tipo_proveedor_id", nullable = false, foreignKey = @ForeignKey(name = "fk_proveedor_tipo_proveedor"))
    private TipoProveedor tipoProveedor;

    // Tipo de Identificación
    @ManyToOne(optional = false)
    @JoinColumn(name = "tipo_identificacion_id", nullable = false, foreignKey = @ForeignKey(name = "fk_proveedor_tipo_identificacionr"))
    private TipoIdentificacion tipoIdentificacion;

}
