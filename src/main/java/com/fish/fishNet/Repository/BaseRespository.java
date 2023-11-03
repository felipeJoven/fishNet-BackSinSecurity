package com.fish.fishNet.Repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.fish.fishNet.Model.Base;


@NoRepositoryBean
public interface BaseRespository <E extends Base, ID extends Serializable> extends JpaRepository<E, ID>{    
}
