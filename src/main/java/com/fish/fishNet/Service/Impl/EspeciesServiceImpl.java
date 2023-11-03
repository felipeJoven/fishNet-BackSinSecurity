package com.fish.fishNet.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fish.fishNet.Model.Especies;
import com.fish.fishNet.Repository.BaseRespository;
import com.fish.fishNet.Repository.EspeciesRepository;
import com.fish.fishNet.Service.EspeciesService;

@Service
public class EspeciesServiceImpl extends BaseServiceImpl<Especies, Integer> implements EspeciesService {

    @Autowired
    private EspeciesRepository especiesRepository; 

    public EspeciesServiceImpl(BaseRespository<Especies, Integer> baseRespository){
        super(baseRespository);
    }
}
