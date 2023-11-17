package com.fish.fishNet.Repository;   

import com.fish.fishNet.Model.Usuario;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends BaseRespository<Usuario, Integer> {

    //Metodo para poder buscar un usuario mediante su nombre
    Optional<Usuario> findByUsername(String username);

    //Metodo para verificar si un usuario existe en la BD
    Boolean existsByUsername(String username);
}
