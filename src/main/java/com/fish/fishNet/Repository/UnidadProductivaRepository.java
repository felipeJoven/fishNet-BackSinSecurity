package com.fish.fishNet.Repository;

import com.fish.fishNet.Model.UnidadProductiva;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UnidadProductivaRepository extends BaseRespository<UnidadProductiva, Integer>{

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM UnidadProductiva u WHERE u.nombreUnidadP = :nombreUnidadP OR u.coordenadas = :coordenadas")
    boolean existByUnidadProductiva(
            @Param("nombreUnidadP") String nombreUnidadP,
            @Param("coordenadas") String coordenadas
    );
}
