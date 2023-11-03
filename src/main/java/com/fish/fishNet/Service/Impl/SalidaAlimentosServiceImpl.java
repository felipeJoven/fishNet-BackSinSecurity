package com.fish.fishNet.Service.Impl;

import com.fish.fishNet.Model.EntradaAlimentos;
import com.fish.fishNet.Repository.EntradaAlimentosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fish.fishNet.Model.SalidaAlimentos;
import com.fish.fishNet.Repository.BaseRespository;
import com.fish.fishNet.Repository.SalidaAlimentosRepository;
import com.fish.fishNet.Service.SalidaAlimentosService;

@Service
public class SalidaAlimentosServiceImpl extends BaseServiceImpl<SalidaAlimentos, Integer> implements SalidaAlimentosService {

    @Autowired
    private SalidaAlimentosRepository salidaAlimentosRepository;
    @Autowired
    private EntradaAlimentosRepository entradaAlimentosRepository;

    public SalidaAlimentosServiceImpl(BaseRespository<SalidaAlimentos, Integer> baseRespository){
        super(baseRespository);
    }

}