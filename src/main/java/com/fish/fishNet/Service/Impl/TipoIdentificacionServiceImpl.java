package com.fish.fishNet.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fish.fishNet.Model.TipoIdentificacion;
import com.fish.fishNet.Repository.BaseRespository;
import com.fish.fishNet.Repository.TipoIdentificacionRepository;
import com.fish.fishNet.Service.TipoIdentificacionService;

@Service
public class TipoIdentificacionServiceImpl extends BaseServiceImpl<TipoIdentificacion, Integer> implements TipoIdentificacionService {

    @Autowired
    private TipoIdentificacionRepository tipoIdentificacionRepository;

    public TipoIdentificacionServiceImpl(BaseRespository<TipoIdentificacion, Integer> baseRespository){
        super(baseRespository);
    }
}