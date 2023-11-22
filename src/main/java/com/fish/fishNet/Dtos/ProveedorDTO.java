package com.fish.fishNet.Dtos;

import lombok.Data;

@Data
public class ProveedorDTO {
    private String nombre;
    private String apellido;
    private int telefono;
    private String correo;
    private String direccion;
    private String razonSocial;
    private int numeroIdentificacion;
    private int tipoProveedorId;
    private int tipoIdentificacionId;
}
