package com.fish.fishNet.Controller;

import com.fish.fishNet.Controller.Impl.BaseControllerImpl;
import com.fish.fishNet.Dtos.ProveedorDTO;
import com.fish.fishNet.Model.Proveedor;
import com.fish.fishNet.Model.UnidadProductiva;
import com.fish.fishNet.Service.Impl.ProveedorServiceImpl;

import com.fish.fishNet.Service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/V1/proveedor")
@CrossOrigin(origins="*")
public class ProveedorController extends BaseControllerImpl<Proveedor, ProveedorServiceImpl> {

    @Autowired
    ProveedorService proveedorService;

    @GetMapping("/lote")
    public ResponseEntity<List<Proveedor>> obtenerProveedoresParaLote() {
        try {
            List<Proveedor> proveedores = proveedorService.obtenerProveedoresParaLote();
            return ResponseEntity.ok(proveedores);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/entrada-alimento")
    public ResponseEntity<List<Proveedor>> obtenerProveedoresParaEntrada() {
        try {
            List<Proveedor> proveedores = proveedorService.obtenerProveedoresParaEntrada();
            return ResponseEntity.ok(proveedores);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/registrar")
    public boolean registrarProveedor(@RequestBody ProveedorDTO proveedorDto) throws Exception {
        return proveedorService.proveedorYaExiste(proveedorDto);
    }

    @Override
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody Proveedor entity){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(proveedorService.updateOverride(id, entity));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\"}" + e.getMessage());
        }
    }

}