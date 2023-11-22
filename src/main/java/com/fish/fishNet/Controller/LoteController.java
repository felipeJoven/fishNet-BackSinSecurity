package com.fish.fishNet.Controller;

import com.fish.fishNet.Model.Especies;
import com.fish.fishNet.Model.Proveedor;
import com.fish.fishNet.Model.UnidadProductiva;
import com.fish.fishNet.Service.EspeciesService;
import com.fish.fishNet.Service.LoteService;
import com.fish.fishNet.Service.ProveedorService;
import com.fish.fishNet.Service.UnidadProductivaService;
import com.fish.fishNet.Dtos.LoteDto;
import com.fish.fishNet.Controller.Impl.BaseControllerImpl;
import com.fish.fishNet.Model.Lote;
import com.fish.fishNet.Service.Impl.LoteServiceImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("api/V1/lote")
@CrossOrigin(origins="*")
public class LoteController extends BaseControllerImpl<Lote, LoteServiceImpl> {

    @Autowired
    private LoteService loteService;
    @Autowired
    private ProveedorService proveedorService;
    @Autowired
    private UnidadProductivaService unidadProductivaService;
    @Autowired
    private EspeciesService especiesService;

    @PostMapping("/registrar")
    public ResponseEntity<?> crearLote(@RequestBody LoteDto loteDTO) throws Exception {
        Lote nuevoLote = new Lote();
        nuevoLote.setNombreLote(loteDTO.getNombreLote());
        nuevoLote.setNumeroAnimales(loteDTO.getNumeroAnimales());
        nuevoLote.setFechaSiembra(loteDTO.getFechaSiembra());
        // Corrige las llamadas a los servicios para obtener las entidades relacionadas
        Proveedor proveedor = proveedorService.findById(loteDTO.getProveedorId());
        UnidadProductiva unidadProductiva = unidadProductivaService.findById(loteDTO.getUnidadProductivaId());
        Especies especies = especiesService.findById(loteDTO.getEspeciesId());
        // Verifica si las entidades se encontraron en la base de datos
        if (proveedor == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Proveedor no encontrado.\"}");
        }
        if (unidadProductiva == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Unidad Productiva no encontrado.\"}");
        }
        if (especies == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Especies no encontrado.\"}");
        }
        // Establece las relaciones en el nuevo lote
        nuevoLote.setProveedor(proveedor);
        nuevoLote.setUnidadProductiva(unidadProductiva);
        nuevoLote.setEspecies(especies);
        // Luego, guarda el nuevo lote en la base de datos
        Lote loteGuardado = loteService.save(nuevoLote);
        return ResponseEntity.status(HttpStatus.OK).body(loteGuardado);
    }
}
