package com.fish.fishNet.Controller;

import com.fish.fishNet.Model.*;
import com.fish.fishNet.Service.LoteService;
import com.fish.fishNet.Service.MortalidadService;
import com.fish.fishNet.Dtos.MortalidadDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fish.fishNet.Controller.Impl.BaseControllerImpl;
import com.fish.fishNet.Service.Impl.MortalidadServiceImpl;

@RestController
@RequestMapping("api/V1/mortalidad")
@CrossOrigin(origins="*")
public class MortalidadController extends BaseControllerImpl<Mortalidad, MortalidadServiceImpl> {

    @Autowired
    private MortalidadService mortalidadService;

    @Autowired
    private LoteService loteService;

    @PostMapping("/registrar")
    public ResponseEntity<?> crearMortalidad(@RequestBody MortalidadDTO mortalidadDTO) throws Exception {
        Mortalidad nuevaMortalidad = new Mortalidad();
        nuevaMortalidad.setAnimalesMuertos(mortalidadDTO.getAnimalesMuertos());
        nuevaMortalidad.setObservacion(mortalidadDTO.getObservacion());

        // Corrige las llamadas a los servicios para obtener las entidades relacionadas
        Lote lote = loteService.findById(mortalidadDTO.getLoteId());

        // Verifica si las entidades se encontraron en la base de datos
        if (lote == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Lote no encontrado.\"}");
        }

        // Establece las relaciones en el nuevo lote
        nuevaMortalidad.setLote(lote);

        // Luego, guarda el nuevo lote en la base de datos
        Mortalidad mortalidadGuardada = mortalidadService.save(nuevaMortalidad);

        return ResponseEntity.status(HttpStatus.OK).body(mortalidadGuardada);
    }

}
