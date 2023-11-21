package com.fish.fishNet.Service;

import com.fish.fishNet.Dtos.EspeciesDTO;
import com.fish.fishNet.Model.Especies;
import org.springframework.stereotype.Service;

//@Service
public interface EspeciesService extends BaseService<Especies, Integer> {

    boolean especieYaExiste(EspeciesDTO especiesDTO);
}
