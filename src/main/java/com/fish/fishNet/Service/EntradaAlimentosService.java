package com.fish.fishNet.Service;

import com.fish.fishNet.Model.EntradaAlimentos;
import com.fish.fishNet.Dtos.EntradaAlimentosDTO;

public interface EntradaAlimentosService extends BaseService<EntradaAlimentos, Integer> {

    boolean entradaAlimentosYaExiste(EntradaAlimentosDTO nuevaEntradaDto) throws Exception;
}
