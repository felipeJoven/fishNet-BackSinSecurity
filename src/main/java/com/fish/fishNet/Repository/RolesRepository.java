package com.fish.fishNet.Repository;

import com.fish.fishNet.Model.Roles;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends BaseRespository<Roles, Integer>{

    //Metodo para poder buscar un rol mediante su nombre
    Optional<Roles> findByName(String name);

}
