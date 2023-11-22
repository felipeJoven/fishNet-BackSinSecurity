package com.fish.fishNet.Service;

import com.fish.fishNet.Model.SalidaAlimentos;
import com.fish.fishNet.Dtos.SalidaAlimentosDTO;

public interface SalidaAlimentosService extends BaseService<SalidaAlimentos, Integer> {

    SalidaAlimentos salidaAlimento(SalidaAlimentosDTO salidaAlimentosDto) throws Exception;
}