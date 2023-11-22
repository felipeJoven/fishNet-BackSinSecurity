package com.fish.fishNet.Service.Impl;

import com.fish.fishNet.Model.*;
import com.fish.fishNet.Repository.BaseRespository;
import com.fish.fishNet.Repository.MuestreoRepository;
import com.fish.fishNet.Service.LoteService;
import com.fish.fishNet.Service.MuestreoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class MuestreoServiceImpl extends BaseServiceImpl<Muestreo, Integer> implements MuestreoService {

    @Autowired
    private MuestreoRepository muestreoRepository;
    @Autowired
    private LoteService loteService;

    public MuestreoServiceImpl(BaseRespository<Muestreo, Integer> baseRepository) {
        super(baseRepository);
    }

    @Override
    public Muestreo save(Muestreo muestreo) {
        // pesoInicial toma el valor de pesoActual
        try {
            if (muestreo.getPesoInicial() == null) {
                muestreo.setPesoInicial(muestreo.getPesoActual());
                return super.save(muestreo);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el muestreo", e);
        }
        return muestreo;
    }

    @Override
    @Transactional
    public Muestreo update(Integer id, Muestreo muestreo) throws Exception {
        try {
            // Obtener el muestreo existente por su ID
            Muestreo muestreoExistente = findById(id);
            if (muestreoExistente == null) {
                System.out.print(" Antes de validar muestreo" );
                throw new Exception("El muestreo no existe.");
            }

            // Actualizar el campo del muestreo existente con los valores del muestreo recibido
            muestreoExistente.setPesoActual(muestreo.getPesoActual());

            // Corrige las llamadas a los servicios para obtener las entidades relacionadas
            Lote lote = loteService.findById(muestreo.getLote().getId());
            // Verifica si las entidades se encontraron en la base de datos
            if (Objects.isNull(lote)) {
                throw new Exception("Lote no encontrado.");
            }
            // Establece las relaciones en el lote existente
            muestreoExistente.setLote(lote);
            // Guardar el muestreo actualizado en la base de datos
            return muestreoRepository.save(muestreoExistente);
        } catch (Exception e) {
            throw new Exception("Error al actualizar el muestreo." + e.getMessage());
        }
    }
}
