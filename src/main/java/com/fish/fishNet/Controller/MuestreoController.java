package com.fish.fishNet.Controller;

import com.fish.fishNet.Controller.Impl.BaseControllerImpl;
import com.fish.fishNet.Dtos.MuestreoDTO;
import com.fish.fishNet.Model.*;
import com.fish.fishNet.Service.Impl.MuestreoServiceImpl;
import com.fish.fishNet.Service.LoteService;
import com.fish.fishNet.Service.MuestreoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/V1/muestreo")
@CrossOrigin(origins="*")
public class MuestreoController extends BaseControllerImpl<Muestreo, MuestreoServiceImpl> {

    @Autowired
    private MuestreoService muestreoService;
    @Autowired
    private LoteService loteService;

    @PostMapping("/registrar")
    public ResponseEntity<?> crearMuestreo(@RequestBody MuestreoDTO muestreoDTO) throws Exception {
        Muestreo nuevoMuestreo = new Muestreo();
        nuevoMuestreo.setPesoActual(muestreoDTO.getPesoActual());
        // Corrige las llamadas a los servicios para obtener las entidades relacionadas
        Lote lote = loteService.findById(muestreoDTO.getLoteId());
        // Verifica si las entidades se encontraron en la base de datos
        if (lote == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Lote no encontrado.\"}");
        }
        // Establece las relaciones en el nuevo muestreo
        nuevoMuestreo.setLote(lote);
        // Luego, guarda el nuevo lote en la base de datos
        Muestreo muestreoGuardado = muestreoService.save(nuevoMuestreo);
        return ResponseEntity.status(HttpStatus.OK).body(muestreoGuardado);
    }
}

