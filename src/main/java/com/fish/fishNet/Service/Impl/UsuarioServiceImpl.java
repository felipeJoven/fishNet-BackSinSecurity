package com.fish.fishNet.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fish.fishNet.Model.Usuario;
import com.fish.fishNet.Repository.BaseRespository;
import com.fish.fishNet.Repository.UsuarioRepository;
import com.fish.fishNet.Service.UsuarioService;

@Service
public class UsuarioServiceImpl extends BaseServiceImpl<Usuario, Integer> implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository; 

    public UsuarioServiceImpl(BaseRespository<Usuario, Integer> baseRespository){
        super(baseRespository);
    }

    public boolean passwordsEqual(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }
}
