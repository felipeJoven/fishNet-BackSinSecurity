package com.fish.fishNet.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fish.fishNet.Model.TipoProveedor;
import com.fish.fishNet.Repository.BaseRespository;
import com.fish.fishNet.Repository.TipoProveedorRepository;
import com.fish.fishNet.Service.TipoProveedorService;

@Service
public class TipoProveedorServiceImpl extends BaseServiceImpl<TipoProveedor, Integer> implements TipoProveedorService {

    @Autowired
    private TipoProveedorRepository tipoProveedorRepository;

    public TipoProveedorServiceImpl(BaseRespository<TipoProveedor, Integer> baseRespository){
        super(baseRespository);}
}
