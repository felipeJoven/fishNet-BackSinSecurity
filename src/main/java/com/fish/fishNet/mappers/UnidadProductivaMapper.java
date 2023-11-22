package com.fish.fishNet.mappers;

import com.fish.fishNet.Dtos.UnidadProductivaDTO;
import com.fish.fishNet.Model.UnidadProductiva;

public class UnidadProductivaMapper {

    public UnidadProductiva convertDtoToEntity(UnidadProductivaDTO unidadProductivaDTO, Integer id) {
        UnidadProductiva unidadProductiva = new UnidadProductiva();
        unidadProductiva.setId(id);
        unidadProductiva.setObservacion(unidadProductivaDTO.getObservacion());
        return unidadProductiva;
    }

    public UnidadProductivaDTO convertEntityToDto(UnidadProductiva unidadProductiva) {
        UnidadProductivaDTO unidadProductivaDTO = new UnidadProductivaDTO();
        unidadProductivaDTO.setNombreUnidadP(unidadProductiva.getNombreUnidadP());
        return  unidadProductivaDTO;
    }
}
