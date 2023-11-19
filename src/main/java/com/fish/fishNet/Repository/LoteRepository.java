package com.fish.fishNet.Repository;

import com.fish.fishNet.Model.Lote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LoteRepository extends BaseRespository<Lote, Integer>{

    @Query("SELECT ((SELECT COUNT(*) FROM Pesca WHERE lote.id = :loteId) +\n" +
            "        (SELECT COUNT(*) FROM Mortalidad WHERE lote.id = :loteId)) > 0 AS result")
    boolean salenAnimales(@Param("loteId") Integer loteId);
}
