package com.fish.fishNet.Service.Impl;

import com.fish.fishNet.Model.Lote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fish.fishNet.Repository.BaseRespository;
import com.fish.fishNet.Repository.LoteRepository;
import com.fish.fishNet.Service.LoteService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LoteServiceImpl extends BaseServiceImpl<Lote, Integer> implements LoteService {

    @Autowired
    private LoteRepository loteRepository;

    public LoteServiceImpl(BaseRespository<Lote, Integer> baseRespository){

        super(baseRespository);
    }

    @Override
    public Lote save(Lote lote) {
        // animalesInicial toma el valor de numeroAnimales
        try {
            if (lote.getAnimalesInicial() == null) {
                lote.setAnimalesInicial(lote.getNumeroAnimales());
                return super.save(lote);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el lote", e);
        }
        return lote;
    }


    @Override
    public List<Lote> findAll() {
        try {
            List<Lote> lote = super.findAll();
            lote.forEach(this::calcularDias);
            return lote;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al calcular dias.", e);
        }

    }

    @Override
    public Lote findById(Integer id) {
        try {
            Lote lote = super.findById(id);
            if (lote != null) {
                calcularDias(lote);
            }
            return lote;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al calcular dias.", e);
        }
    }

    private void calcularDias(Lote lote) {
        if (lote.getFechaSiembra() != null) {
            LocalDate fechaActual = LocalDate.now();
            lote.setDiasDeSiembra(ChronoUnit.DAYS.between(lote.getFechaSiembra(), fechaActual));
        }
    }


}
