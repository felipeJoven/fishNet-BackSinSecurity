package com.fish.fishNet.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fish.fishNet.Model.EntradaAlimentos;
import com.fish.fishNet.Repository.BaseRespository;
import com.fish.fishNet.Repository.EntradaAlimentosRepository;
import com.fish.fishNet.Service.EntradaAlimentosService;

@Service
public class EntradaAlimentosServiceImpl extends BaseServiceImpl<EntradaAlimentos, Integer> implements EntradaAlimentosService {

    @Autowired
    private EntradaAlimentosRepository entradaAlimentosRepository;

    public EntradaAlimentosServiceImpl(BaseRespository<EntradaAlimentos, Integer> baseRespository){
        super(baseRespository);
    }

}
