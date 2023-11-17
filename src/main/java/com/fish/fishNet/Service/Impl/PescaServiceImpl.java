package com.fish.fishNet.Service.Impl;

import com.fish.fishNet.Model.Lote;
import com.fish.fishNet.Repository.LoteRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fish.fishNet.Model.Pesca;
import com.fish.fishNet.Repository.BaseRespository;
import com.fish.fishNet.Repository.PescaRepository;
import com.fish.fishNet.Service.PescaService;

import java.util.Optional;

@Service
@Slf4j
public class PescaServiceImpl extends BaseServiceImpl<Pesca, Integer> implements PescaService {

    @Autowired
    private PescaRepository pescaRepository;

    @Autowired
    private LoteRepository loteRepository;

    public PescaServiceImpl(BaseRespository<Pesca, Integer> baseRespository){
        super(baseRespository);
    }

    // Descontar peces pescados del Lote correspondiente
    @Override
    public Pesca save(Pesca pesca) {
        try {
            // Obtener el lote correspondiente
            Optional<Lote> optionalLote = loteRepository.findById(pesca.getLote().getId());
            if(optionalLote.isPresent()) {
                Lote lote = optionalLote.get();
                int animalesPescados = Integer.parseInt(String.valueOf(pesca.getAnimalesPescados()));
                int numeroAnimales = Integer.parseInt(String.valueOf(lote.getNumeroAnimales()));
                // Calcular el nuevo número de animales en el lote después de la pesca
                int numeroAnimalesActual = numeroAnimales - animalesPescados;

                // Asegurarse de que el nuevo número de animales no sea negativo
                if (numeroAnimalesActual < 0) {
                    throw new IllegalArgumentException("El número de peces pescados no puede ser mayor a los existentes en el lote.");
                }

                // Se actualiza el número de peces en el lote
                lote.setNumeroAnimales(Integer.valueOf(String.valueOf(numeroAnimalesActual)));
                loteRepository.save(lote);

                pesca.setAnimalesPescados(animalesPescados);

                return super.save(pesca);
            }
            return  null;


        } catch (Exception e) {
            throw new RuntimeException("Error al guardar la Pesca.", e);
        }
    }

    @Override
    public Pesca update(Integer id, Pesca pesca) {
        try {
            // Obtener la pesca existente por su ID
            Pesca pescaExistente = findById(id);

            // Obtener el lote correspondiente
            Lote lote = pescaExistente.getLote();

            // Restar los animales muertos actuales de la pesca existente al número de animales en el lote
            int animalesPescadosAntes = Integer.parseInt(String.valueOf(pescaExistente.getAnimalesPescados()));
            int animalesPescadosAhora = Integer.parseInt(String.valueOf(pesca.getAnimalesPescados()));
            int numeroAnimales = Integer.parseInt(String.valueOf(lote.getNumeroAnimales()));
            int animalesInicial = Integer.parseInt(String.valueOf(lote.getAnimalesInicial()));
            int numeroAnimalesActual = 0;

            // Asegurarse de que el nuevo número de animales no sea negativo
            if (animalesPescadosAhora < 0) {
                throw new IllegalArgumentException("El número de peces pescados no puede ser negativo");
            }

            //Validar que muertos no sea mayor que el lote
            if (animalesPescadosAhora <= animalesInicial) {
                if(animalesPescadosAntes > animalesPescadosAhora) {
                    numeroAnimalesActual = numeroAnimales + (animalesPescadosAntes - animalesPescadosAhora);
                } else  {
                    numeroAnimalesActual = numeroAnimales - (animalesPescadosAhora - animalesPescadosAntes);
                }

                // Actualizar el número de peces en el lote
                lote.setNumeroAnimales(Integer.valueOf(String.valueOf(numeroAnimalesActual)));

                // Guardar los cambios en el lote
                loteRepository.save(lote);

                // Actualizar la pesca
                pescaExistente.setAnimalesPescados(pesca.getAnimalesPescados());
                pescaExistente.setPesoPromedio(pesca.getPesoPromedio());

                // Guardar los cambios en la pesca
                return pescaRepository.save(pescaExistente);
            } else {
                throw new IllegalArgumentException("El número de peces pescados no puede ser mayor a los existentes en el lote.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar la Pesca.", e);
        }
    }

    @Override
    @Transactional
    public boolean delete(Integer id) throws Exception {
        try {
            Optional<Pesca> pescaOptional = baseRepository.findById(id);

            if (pescaOptional.isPresent()) {
                Pesca pesca = pescaOptional.get();
                int animalesPescados = pesca.getAnimalesPescados();
                Lote lote = pesca.getLote();
                int numeroAnimales = lote.getNumeroAnimales();
                numeroAnimales += animalesPescados;
                lote.setNumeroAnimales(numeroAnimales);
                loteRepository.save(lote);
                baseRepository.deleteById(id);
                return true;
            }

            return false;
        } catch (Exception e) {
            throw new Exception("Error al eliminar Pesca", e);
        }
    }
}
