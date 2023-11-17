package com.fish.fishNet.Repository;

import com.fish.fishNet.Model.EntradaAlimentos;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface EntradaAlimentosRepository extends BaseRespository<EntradaAlimentos, Integer>{

    EntradaAlimentos findByNumeroFactura(String numeroFactura);

    @Query(value = "SELECT kilos_inicial FROM entrada_alimentos WHERE numero_factura = ?1 AND tipo_alimento_id = ?2", nativeQuery = true)
    Integer totalEntradas(String numeroFactura, int tipoAlimentoId);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM EntradaAlimentos e WHERE e.numeroFactura = :numeroFactura AND e.tipoAlimento.id = :tipoAlimentoId")
    boolean existByEntradaAlimento(
            @Param("numeroFactura") String numeroFactura,
            @Param("tipoAlimentoId") Integer tipoAlimentoId
    );

}
