package com.fish.fishNet.Service.Impl;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.fish.fishNet.Model.Base;
import com.fish.fishNet.Repository.BaseRespository;
import com.fish.fishNet.Service.BaseService;

import jakarta.transaction.Transactional;

public abstract class BaseServiceImpl<E extends Base, ID extends Serializable> implements BaseService<E, ID> {
    
    protected BaseRespository<E, ID> baseRepository;

    public BaseServiceImpl(BaseRespository<E, ID> baseRepository) {
        this.baseRepository = baseRepository;
    }

    @Override
    @Transactional
    public List<E> findAll() throws Exception {
        try {
            List<E> entities = baseRepository.findAll();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage()); 
            
        }
    }

    @Override
    @Transactional
    public E findById(ID id) throws Exception {
        try {
            Optional<E> entityOptional = baseRepository.findById(id);
            return entityOptional.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());            
        }
    }

    @Override
    @Transactional
    public E save(E entity) throws Exception {
        try {
            entity.setFechaCreacion(LocalDate.now());
            return baseRepository.save(entity);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public E update(ID id, E entity) throws Exception {
        try {
            Optional<E> entityOptional = baseRepository.findById(id);
            entity.setFechaCreacion(entityOptional.get().getFechaCreacion());
            return baseRepository.save(entity);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean delete(ID id) throws Exception {
        try {
            if(baseRepository.existsById(id)){
                baseRepository.deleteById(id);
                return true;
            }else{
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());            
        }
    }
}
