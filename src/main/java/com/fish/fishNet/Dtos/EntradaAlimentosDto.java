package com.fish.fishNet.Dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EntradaAlimentosDto {

    private String numeroFactura;
    private LocalDate fechaVencimiento;
    private String registroIca;
    private Integer numeroKilos;
    private Integer tipoAlimentoId;
    private Integer proveedorId;
}