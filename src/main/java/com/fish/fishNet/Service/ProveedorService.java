package com.fish.fishNet.Service;

import com.fish.fishNet.Dtos.ProveedorDTO;
import com.fish.fishNet.Model.Proveedor;

import java.util.List;

public interface ProveedorService extends BaseService<Proveedor, Integer> {

    List<Proveedor> obtenerProveedoresParaLote();

    List<Proveedor> obtenerProveedoresParaEntrada();

    boolean proveedorYaExiste(ProveedorDTO proveedorDTO) throws Exception;

    Proveedor updateOverride(Integer id, Proveedor proveedor) throws Exception;
}