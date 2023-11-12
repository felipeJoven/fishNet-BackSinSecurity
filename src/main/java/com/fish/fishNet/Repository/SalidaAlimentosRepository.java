package com.fish.fishNet.Repository;   

import com.fish.fishNet.Model.SalidaAlimentos;
import org.springframework.data.jpa.repository.Query;

public interface SalidaAlimentosRepository extends BaseRespository<SalidaAlimentos, Integer> {

    @Query(value = "SELECT COALESCE(SUM(numero_kilos), 0) AS kilos FROM salida_alimentos WHERE numero_factura = ?1 AND tipo_alimento_id = ?2", nativeQuery = true)
    Integer totalSalidas(String numeroFactura, int tipoAlimentoId);
}
