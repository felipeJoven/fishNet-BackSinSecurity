package com.fish.fishNet.Dtos;

import lombok.Data;

@Data
public class SalidaAlimentosDto {
    private String numeroFactura;
    private Integer kilosSalida;
    private Integer lote;
}
