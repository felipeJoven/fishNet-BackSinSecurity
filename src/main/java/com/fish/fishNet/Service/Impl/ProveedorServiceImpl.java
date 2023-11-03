package com.fish.fishNet.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fish.fishNet.Model.Proveedor;
import com.fish.fishNet.Repository.BaseRespository;
import com.fish.fishNet.Repository.ProveedorRepository;
import com.fish.fishNet.Service.ProveedorService;

@Service
public class ProveedorServiceImpl extends BaseServiceImpl<Proveedor, Integer> implements ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository; 

    public ProveedorServiceImpl(BaseRespository<Proveedor, Integer> baseRespository){
        super(baseRespository);
    }
}
