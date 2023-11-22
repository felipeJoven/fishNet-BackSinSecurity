package com.fish.fishNet.Service.Impl;

import com.fish.fishNet.Dtos.EspeciesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fish.fishNet.Model.Especies;
import com.fish.fishNet.Repository.BaseRespository;
import com.fish.fishNet.Repository.EspeciesRepository;
import com.fish.fishNet.Service.EspeciesService;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Service
public class EspeciesServiceImpl extends BaseServiceImpl<Especies, Integer> implements EspeciesService {

    private static final String MENSAJE_ERROR_ESPECIE = "La especie ya existe";
    private static final String MENSAJE_ERROR_ID = "No se encontró la especie con ese ID: .";


    @Autowired
    private EspeciesRepository especiesRepository;

    public EspeciesServiceImpl(BaseRespository<Especies, Integer> baseRespository){
        super(baseRespository);
    }

    @Override
    public boolean especieYaExiste(EspeciesDTO nuevaEspecieDto) {
        Especies especies = new Especies();
        String nombreEspecie = nuevaEspecieDto.getNombreEspecie();
        // Realiza la verificación en el repositorio
        boolean existeEspecie = especiesRepository.existByEspecie(
                nombreEspecie
        );
        if (existeEspecie) {
            // Si existe si la especie hay excepción
            throw new IllegalArgumentException(MENSAJE_ERROR_ESPECIE);
        } else {
            // Si no existe, puedes continuar con la creación de la entrada
            Especies nuevaEspecie = new Especies();
            nuevaEspecie.setNombreEspecie(nombreEspecie);
            // Se realiza set para que muestre la fecha de creación en la base de datos
            nuevaEspecie.setFechaCreacion(LocalDate.now());
            especiesRepository.save(nuevaEspecie);
        }
        return true;
    }

    public Especies updateOverride(Integer id, Especies especies) throws Exception {
        Optional<Especies> optionalEspecies = especiesRepository.findById(id);

        if (optionalEspecies.isPresent()) {
            Especies especieSaved = optionalEspecies.get();

            if(especieSaved.getNombreEspecie() != especies.getNombreEspecie()){
                // Verificar si los nuevos valores generaran duplicados excluyendo la entidad actual
                boolean exist = especiesRepository.existByEspecie(
                        especies.getNombreEspecie(),
                        id
                );
                if (!exist) {
                    return updateEspecie(especieSaved, especies);
                } else {
                    throw new IllegalArgumentException(MENSAJE_ERROR_ESPECIE);
                }
            }
        } else {
            throw new Exception(MENSAJE_ERROR_ID + id);
        }
        return especies;
    }

    private Especies updateEspecie(Especies especieSaved, Especies especies) {
        // Actualizar la entidad con los nuevos valores
        especieSaved.setNombreEspecie(especies.getNombreEspecie());

        especiesRepository.save(especieSaved);
        return especieSaved;
    }
}
