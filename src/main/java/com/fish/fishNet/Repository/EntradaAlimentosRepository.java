package com.fish.fishNet.Repository;

import com.fish.fishNet.Model.EntradaAlimentos;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface EntradaAlimentosRepository extends BaseRespository<EntradaAlimentos, Integer>{

    EntradaAlimentos findByNumeroFactura(String numeroFactura);

    @Query(value = "SELECT COALESCE(SUM(numero_kilos), 0) AS kilos FROM entrada_alimentos WHERE numero_factura = ?1 AND tipo_alimento_id = ?2", nativeQuery = true)
    Integer totalEntradas(String numeroFactura, int tipoAlimentoId);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM EntradaAlimentos e WHERE e.fechaVencimiento = :fechaVencimiento AND e.numeroFactura = :numeroFactura AND e.registroIca = :registroIca AND e.numeroKilos = :numeroKilos AND e.tipoAlimento.id = :tipoAlimentoId AND e.proveedor.id = :proveedorId")
    boolean existByEntradaAlimento(
            @Param("fechaVencimiento") LocalDate fechaVencimiento,
            @Param("numeroFactura") String numeroFactura,
            @Param("registroIca") String registroIca,
            @Param("numeroKilos") int numeroKilos,
            @Param("tipoAlimentoId") Integer tipoAlimentoId,
            @Param("proveedorId") Integer proveedorId
    );

}
