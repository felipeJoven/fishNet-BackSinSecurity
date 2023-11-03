package com.fish.fishNet.Repository;

import com.fish.fishNet.Model.EntradaAlimentos;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EntradaAlimentosRepository extends BaseRespository<EntradaAlimentos, Integer>{

    // Método para sumar los kilos disponibles para una factura
    @Query("SELECT SUM(e.numeroKilos) FROM EntradaAlimentos e WHERE e.numeroFactura = :numeroFactura")
    double sumKilosDisponibles(@Param("numeroFactura") String numeroFactura);

    // Método para actualizar el campo numeroKilos en EntradaAlimentos
    @Modifying
    @Query("UPDATE EntradaAlimentos e SET e.numeroKilos = :kilosDisponibles WHERE e.numeroFactura = :numeroFactura")
    void updateKilosDisponibles(@Param("numeroFactura") String numeroFactura, @Param("kilosDisponibles") double kilosDisponibles);
}
