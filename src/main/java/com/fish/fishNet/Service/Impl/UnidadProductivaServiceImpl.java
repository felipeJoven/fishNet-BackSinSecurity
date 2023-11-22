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
import java.util.Optional;

@Service
public class UnidadProductivaServiceImpl extends BaseServiceImpl<UnidadProductiva, Integer> implements UnidadProductivaService {

    private static final String MENSAJE_ERROR_UNIDAD = "Ya existe una unidad productiva con el mismo nombre o coordenadas.";
    private static final String MENSAJE_ERROR_ID = "No se encontró la unidad productiva con ID: .";

    @Autowired
    private UnidadProductivaRepository unidadProductivaRepository;

    @Autowired
    private ModelMapper modelMapper;

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
            // Si existe si la unidad productiva hay excepción
            throw new IllegalArgumentException(MENSAJE_ERROR_UNIDAD);
        } else {
            // Si no existe, puedes continuar con la creación de la entrada
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

            // Verificar si los nuevos valores generarían duplicados excluyendo la entidad actual
            if (unidadProductivaRepository.existByUnidadProductiva(
                    unidadProductiva.getNombreUnidadP(),
                    unidadProductiva.getCoordenadas()
                    )) {
                throw new IllegalArgumentException(MENSAJE_ERROR_UNIDAD);
            }

            // Actualizar la entidad con los nuevos valores
            unidadProductivaSaved.setNombreUnidadP(unidadProductiva.getNombreUnidadP());
            unidadProductivaSaved.setArea(unidadProductiva.getArea());
            unidadProductivaSaved.setCoordenadas(unidadProductiva.getCoordenadas());
            unidadProductivaSaved.setObservacion(unidadProductiva.getObservacion());
            unidadProductivaSaved.setProfundidad(unidadProductiva.getProfundidad());

            // Guardar la entidad actualizada
            unidadProductivaRepository.save(unidadProductivaSaved);
            return unidadProductivaSaved;
        } else {
            throw new Exception(MENSAJE_ERROR_ID + id);
        }
    }
}