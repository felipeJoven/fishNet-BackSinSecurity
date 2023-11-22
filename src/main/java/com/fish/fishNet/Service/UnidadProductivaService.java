package com.fish.fishNet.Service;

import com.fish.fishNet.Dtos.UnidadProductivaDTO;
import com.fish.fishNet.Model.UnidadProductiva;

public interface UnidadProductivaService extends BaseService<UnidadProductiva, Integer> {

    boolean existByUnidadProductiva(UnidadProductivaDTO unidadProductivaDTO);

    UnidadProductiva updateOverride(Integer id, UnidadProductiva unidadProductiva) throws Exception;
}