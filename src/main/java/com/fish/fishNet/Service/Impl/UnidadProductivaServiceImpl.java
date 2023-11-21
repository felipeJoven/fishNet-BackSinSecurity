package com.fish.fishNet.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fish.fishNet.Model.UnidadProductiva;
import com.fish.fishNet.Repository.BaseRespository;
import com.fish.fishNet.Repository.UnidadProductivaRepository;
import com.fish.fishNet.Service.UnidadProductivaService;

@Service
public class UnidadProductivaServiceImpl extends BaseServiceImpl<UnidadProductiva, Integer> implements UnidadProductivaService {

    @Autowired
    private UnidadProductivaRepository unidadProductivaRepository; 

    public UnidadProductivaServiceImpl(BaseRespository<UnidadProductiva, Integer> baseRespository){
        super(baseRespository);
    }
}