package com.fish.fishNet.Service.Impl;

import com.fish.fishNet.Dtos.UnidadProductivaDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fish.fishNet.Model.UnidadProductiva;
import com.fish.fishNet.Repository.BaseRespository;
import com.fish.fishNet.Repository.UnidadProductivaRepository;
import com.fish.fishNet.Service.UnidadProductivaService;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
public class UnidadProductivaServiceImpl extends BaseServiceImpl<UnidadProductiva, Integer> implements UnidadProductivaService {

    private static final String MENSAJE_ERROR_UNIDAD = "Ya existe una unidad productiva con el mismo nombre o coordenadas.";
    private static final String MENSAJE_ERROR_ID = "No se encontró la unidad productiva con ese ID: .";

    @Autowired
    private UnidadProductivaRepository unidadProductivaRepository;

    public UnidadProductivaServiceImpl(BaseRespository<UnidadProductiva, Integer> baseRespository){
        super(baseRespository);
    }

    @Override
    public boolean existByUnidadProductiva(UnidadProductivaDTO unidadProductivaDTO) {
        UnidadProductiva unidadProductiva = new UnidadProductiva();
        String nombreUnidadP = unidadProductivaDTO.getNombreUnidadP();
        String area = unidadProductivaDTO.getArea();
        String coordenadas = unidadProductivaDTO.getCoordenadas();
        String observacion = unidadProductivaDTO.getObservacion();
        Double profundidad = unidadProductivaDTO.getProfundidad();
        // Realiza la verificación en el repositorio
        boolean existeUnidadP = unidadProductivaRepository.existByUnidadProductiva(nombreUnidadP, coordenadas);
        if (existeUnidadP) {
            // Si existe la unidad productiva hay excepción
            throw new IllegalArgumentException(MENSAJE_ERROR_UNIDAD);
        } else {
            // Si no existe, sigue la creación de la unidad productiva
            UnidadProductiva nuevaUnidadP = new UnidadProductiva();
            nuevaUnidadP.setNombreUnidadP(nombreUnidadP);
            nuevaUnidadP.setArea(area);
            nuevaUnidadP.setCoordenadas(coordenadas);
            nuevaUnidadP.setObservacion(observacion);
            nuevaUnidadP.setProfundidad(profundidad);
            // Se realiza set para que muestre la fecha de creación en la base de datos
            nuevaUnidadP.setFechaCreacion(LocalDate.now());
            unidadProductivaRepository.save(nuevaUnidadP);
        }
        return true;
    }

    public UnidadProductiva updateOverride(Integer id, UnidadProductiva unidadProductiva) throws Exception {
        Optional<UnidadProductiva> optionalUnidadProductiva = unidadProductivaRepository.findById(id);

        if (optionalUnidadProductiva.isPresent()) {
            UnidadProductiva unidadProductivaSaved = optionalUnidadProductiva.get();

            if(
                    !Objects.equals(unidadProductivaSaved.getCoordenadas(), unidadProductiva.getCoordenadas()) ||
                    !Objects.equals(unidadProductivaSaved.getNombreUnidadP(), unidadProductiva.getNombreUnidadP())
            ) {
                // Verificar si los nuevos valores generaran duplicados excluyendo la entidad actual
                boolean exist = unidadProductivaRepository.existByUnidadProductiva(
                        unidadProductiva.getNombreUnidadP(),
                        unidadProductiva.getCoordenadas(),
                        id
                );
                if (!exist) {
                    return updateUnidadProductiva(unidadProductivaSaved, unidadProductiva);
                } else {
                    throw new IllegalArgumentException(MENSAJE_ERROR_UNIDAD);
                }
            } else {
                return updateUnidadProductiva(unidadProductivaSaved, unidadProductiva);
            }
        } else {
            throw new Exception(MENSAJE_ERROR_ID + id);
        }
    }

    private UnidadProductiva updateUnidadProductiva(UnidadProductiva unidadProductivaSaved, UnidadProductiva unidadProductiva) {
        // Actualizar la entidad con los nuevos valores
        unidadProductivaSaved.setNombreUnidadP(unidadProductiva.getNombreUnidadP());
        unidadProductivaSaved.setArea(unidadProductiva.getArea());
        unidadProductivaSaved.setCoordenadas(unidadProductiva.getCoordenadas());
        unidadProductivaSaved.setObservacion(unidadProductiva.getObservacion());
        unidadProductivaSaved.setProfundidad(unidadProductiva.getProfundidad());

        unidadProductivaRepository.save(unidadProductivaSaved);
        return unidadProductivaSaved;
    }
}