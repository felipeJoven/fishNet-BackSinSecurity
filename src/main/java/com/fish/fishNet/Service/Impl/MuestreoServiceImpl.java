package com.fish.fishNet.Service.Impl;

import com.fish.fishNet.Model.Muestreo;
import com.fish.fishNet.Repository.BaseRespository;
import com.fish.fishNet.Repository.MuestreoRepository;
import com.fish.fishNet.Service.MuestreoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MuestreoServiceImpl extends BaseServiceImpl<Muestreo, Integer> implements MuestreoService {

    @Autowired
    private MuestreoRepository muestreoRepository;

    public MuestreoServiceImpl(BaseRespository<Muestreo, Integer> baseRepository) {
        super(baseRepository);
    }
}
