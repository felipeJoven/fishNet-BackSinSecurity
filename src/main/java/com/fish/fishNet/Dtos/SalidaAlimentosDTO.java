package com.fish.fishNet.Dtos;

import lombok.Data;

@Data
public class SalidaAlimentosDTO {
    private String numeroFactura;
    private Double numeroKilos;
    private int loteId;
    private int tipoAlimentoId;
}
