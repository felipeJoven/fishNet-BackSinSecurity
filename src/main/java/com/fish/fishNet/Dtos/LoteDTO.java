package com.fish.fishNet.Dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LoteDTO {
    private String nombreLote;
    private Integer numeroAnimales;
    private LocalDate fechaSiembra;
    private Integer proveedorId;
    private Integer especiesId;
    private Integer unidadProductivaId;
}
