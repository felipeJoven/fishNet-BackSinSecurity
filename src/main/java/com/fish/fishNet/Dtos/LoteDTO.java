package com.fish.fishNet.Dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LoteDTO {
    private String nombreLote;
    private int numeroAnimales;
    private LocalDate fechaSiembra;
    private int proveedorId;
    private int especiesId;
    private int unidadProductivaId;
}
