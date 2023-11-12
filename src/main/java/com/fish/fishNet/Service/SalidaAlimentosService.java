package com.fish.fishNet.Service;

import com.fish.fishNet.Model.SalidaAlimentos;
import com.fish.fishNet.Dtos.SalidaAlimentosDto;

public interface SalidaAlimentosService extends BaseService<SalidaAlimentos, Integer> {

    SalidaAlimentos salidaAlimento(SalidaAlimentosDto salidaAlimentosDto);
}