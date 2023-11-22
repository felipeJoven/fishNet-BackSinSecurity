package com.fish.fishNet.Repository;

import com.fish.fishNet.Model.Proveedor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProveedorRepository extends BaseRespository<Proveedor, Integer>{

    @Query("SELECT p FROM Proveedor p JOIN FETCH p.tipoProveedor tp WHERE tp.tipoProveedor = 'Peces'")
    List<Proveedor> findProveedoresForLote(String peces);

    @Query("SELECT p FROM Proveedor p JOIN FETCH p.tipoProveedor tp WHERE tp.tipoProveedor = 'Alimento'")
    List<Proveedor> findProveedoresForEntrada(String alimento);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Proveedor p WHERE p.razonSocial = :razonSocial AND p.tipoProveedor.id = :tipoProveedorId and p.id != :id")
    boolean existByProveedor(
            @Param("razonSocial") String razonSocial,
            @Param("tipoProveedorId") Integer tipoProveedorId,
            @Param("id") Integer id
    );

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Proveedor p WHERE p.razonSocial = :razonSocial AND p.tipoProveedor.id = :tipoProveedorId")
    boolean existByProveedor(
            @Param("razonSocial") String razonSocial,
            @Param("tipoProveedorId") Integer tipoProveedorId
    );

}
