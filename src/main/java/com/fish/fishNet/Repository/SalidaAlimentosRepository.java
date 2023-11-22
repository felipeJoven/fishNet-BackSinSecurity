package com.fish.fishNet.Repository;

import com.fish.fishNet.Model.SalidaAlimentos;
import com.fish.fishNet.Model.TipoAlimento;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SalidaAlimentosRepository extends BaseRespository<SalidaAlimentos, Integer> {

    @Query(value = "SELECT COALESCE(SUM(numero_kilos), 0) AS kilos FROM salida_alimentos WHERE numero_factura = ?1 AND tipo_alimento_id = ?2", nativeQuery = true)
    Integer totalSalidas(String numeroFactura, int tipoAlimentoId);

    List<SalidaAlimentos> findByNumeroFacturaAndTipoAlimento(String numeroFactura, TipoAlimento tipoAlimento);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM SalidaAlimentos s WHERE s.numeroFactura = :numeroFactura AND s.tipoAlimento.id = :tipoAlimentoId")
    boolean tieneSalida(
            @Param("numeroFactura") String numeroFactura,
            @Param("tipoAlimentoId") Integer tipoAlimentoId
    );
}
