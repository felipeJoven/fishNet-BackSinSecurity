package com.fish.fishNet.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fish.fishNet.Model.TipoAlimento;
import com.fish.fishNet.Repository.BaseRespository;
import com.fish.fishNet.Repository.TipoAlimentoRepository;
import com.fish.fishNet.Service.TipoAlimentoService;

@Service
public class TipoAlimentoServiceImpl extends BaseServiceImpl<TipoAlimento, Integer> implements TipoAlimentoService {

    @Autowired
    private TipoAlimentoRepository tipoAlimentoRepository; 

    public TipoAlimentoServiceImpl(BaseRespository<TipoAlimento, Integer> baseRespository){
        super(baseRespository);
    }
}
