package com.fish.fishNet.Dtos;

import lombok.Data;

@Data
public class SalidaAlimentosDto {
    private String numeroFactura;
    private int numeroKilos;
    private int loteId;
    private int tipoAlimentoId;
}
