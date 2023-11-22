package com.fish.fishNet.Service;

import com.fish.fishNet.Dtos.EspeciesDTO;
import com.fish.fishNet.Model.Especies;

public interface EspeciesService extends BaseService<Especies, Integer> {

    boolean especieYaExiste(EspeciesDTO especiesDTO);

    Especies updateOverride(Integer id, Especies especies) throws Exception;
}
