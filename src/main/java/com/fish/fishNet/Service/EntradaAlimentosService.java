package com.fish.fishNet.Service;

import com.fish.fishNet.Model.EntradaAlimentos;
import com.fish.fishNet.Dtos.EntradaAlimentosDto;

public interface EntradaAlimentosService extends BaseService<EntradaAlimentos, Integer> {

    boolean entradaAlimentosYaExiste(EntradaAlimentosDto nuevaEntradaDto) throws Exception;
}
