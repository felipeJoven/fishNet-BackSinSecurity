package com.fish.fishNet.Repository;

import com.fish.fishNet.Model.Especies;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EspeciesRepository extends BaseRespository<Especies, Integer>{

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Especies e WHERE e.nombreEspecie = :nombreEspecie and e.id != :id")
    boolean existByEspecie(
            @Param("nombreEspecie") String nombreEspecie,
            @Param("id") Integer id
    );

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Especies e WHERE e.nombreEspecie = :nombreEspecie")
    boolean existByEspecie(
            @Param("nombreEspecie") String nombreEspecie
    );
}
