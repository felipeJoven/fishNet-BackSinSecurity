package com.fish.fishNet.Service.Impl;

import com.fish.fishNet.Model.Lote;
import com.fish.fishNet.Repository.LoteRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fish.fishNet.Model.Mortalidad;
import com.fish.fishNet.Repository.BaseRespository;
import com.fish.fishNet.Repository.MortalidadRepository;
import com.fish.fishNet.Service.MortalidadService;

import java.util.Optional;

@Service
@Slf4j
public class MortalidadServiceImpl extends BaseServiceImpl<Mortalidad, Integer> implements MortalidadService {

    @Autowired
    private MortalidadRepository mortalidadRepository;

    @Autowired
    private LoteRepository loteRepository;

    public MortalidadServiceImpl(BaseRespository<Mortalidad, Integer> baseRespository){
        super(baseRespository);
    }

    // Descontar peces muertos del Lote correspondiente
    @Override
    public Mortalidad save(Mortalidad mortalidad) {
        try {
            // Obtener el lote correspondiente
            Optional<Lote> optionalLote = loteRepository.findById(mortalidad.getLote().getId());
            if(optionalLote.isPresent()) {
                Lote lote = optionalLote.get();
                int animalesMuertos = Integer.parseInt(String.valueOf(mortalidad.getAnimalesMuertos()));
                int numeroAnimales = Integer.parseInt(String.valueOf(lote.getNumeroAnimales()));
                // Calcular el nuevo número de animales en el lote después de la mortalidad
                int numeroAnimalesActual = numeroAnimales - animalesMuertos;

                // Asegurarse de que el nuevo número de animales no sea negativo
                if (numeroAnimalesActual < 0) {
                    throw new IllegalArgumentException("El número de peces muertos no puede ser mayor a los existentes en el lote.");
                }

                // Se actualiza el número de peces en el lote
                lote.setNumeroAnimales(Integer.valueOf(String.valueOf(numeroAnimalesActual)));
                loteRepository.save(lote);

                mortalidad.setAnimalesMuertos(animalesMuertos);

                return super.save(mortalidad);
            }
            return null;

        } catch (Exception e) {
            throw new RuntimeException("Error al guardar la Mortalidad.", e);
        }
    }

    @Override
    public Mortalidad update(Integer id, Mortalidad mortalidad) {
        try {
            // Obtener la mortalidad existente por su ID
            Mortalidad mortalidadExistente = findById(id);

            // Obtener el lote correspondiente
            Lote lote = mortalidadExistente.getLote();

            // Restar los animales muertos actuales de la mortalidad existente al número de animales en el lote
            int animalesMuertosAntes = Integer.parseInt(String.valueOf(mortalidadExistente.getAnimalesMuertos()));
            int animalesMuertosAhora = Integer.parseInt(String.valueOf(mortalidad.getAnimalesMuertos()));
            int numeroAnimales = Integer.parseInt(String.valueOf(lote.getNumeroAnimales()));
            int animalesInicial = Integer.parseInt(String.valueOf(lote.getAnimalesInicial()));
            int numeroAnimalesActual = 0;

            // Asegurarse de que el nuevo número de animales no sea negativo
            if (animalesMuertosAhora < 0) {
                throw new IllegalArgumentException("El número de peces muertos no puede ser negativo");
            }

            //Validar que muertos no sea mayor que el lote
            if (animalesMuertosAhora <= animalesInicial) {
                if(animalesMuertosAntes > animalesMuertosAhora) {
                    numeroAnimalesActual = numeroAnimales + (animalesMuertosAntes - animalesMuertosAhora);
                } else  {
                    numeroAnimalesActual = numeroAnimales - (animalesMuertosAhora - animalesMuertosAntes);
                }

                // Actualizar el número de peces en el lote
                lote.setNumeroAnimales(Integer.valueOf(String.valueOf(numeroAnimalesActual)));

                // Guardar los cambios en el lote
                loteRepository.save(lote);

                // Actualizar la mortalidad
                mortalidadExistente.setAnimalesMuertos(mortalidad.getAnimalesMuertos());
                mortalidadExistente.setObservacion(mortalidad.getObservacion());

                // Guardar los cambios en la mortalidad
                return mortalidadRepository.save(mortalidadExistente);
            } else {
                throw new IllegalArgumentException("El número de peces muertos no puede ser mayor a los existentes en el lote.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar la Mortalidad.", e);
        }
    }

    @Override
    @Transactional
    public boolean delete(Integer id) throws Exception {
        try {
            Optional<Mortalidad> mortalidadOptional = baseRepository.findById(id);

            if (mortalidadOptional.isPresent()) {
                Mortalidad mortalidad = mortalidadOptional.get();
                int animalesMuertos = mortalidad.getAnimalesMuertos();
                Lote lote = mortalidad.getLote();
                int numeroAnimales = lote.getNumeroAnimales();
                numeroAnimales += animalesMuertos;
                lote.setNumeroAnimales(numeroAnimales);
                loteRepository.save(lote);
                baseRepository.deleteById(id);
                return true;
            }

            return false;
        } catch (Exception e) {
            throw new Exception("Error al eliminar Mortalidad", e);
        }
    }

}
