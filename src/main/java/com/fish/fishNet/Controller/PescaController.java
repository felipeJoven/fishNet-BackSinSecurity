package com.fish.fishNet.Controller;

import com.fish.fishNet.Model.*;
import com.fish.fishNet.Service.LoteService;
import com.fish.fishNet.Service.PescaService;
import com.fish.fishNet.dtos.PescaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fish.fishNet.Controller.Impl.BaseControllerImpl;
import com.fish.fishNet.Service.Impl.PescaServiceImpl;

@RestController
@RequestMapping("api/V1/pesca")
@CrossOrigin(origins="*")
public class PescaController extends BaseControllerImpl<Pesca, PescaServiceImpl> {

    @Autowired
    private PescaService pescaService;

    @Autowired
    private LoteService loteService;


    @PostMapping("/crear-pesca")
    public ResponseEntity<?> crearPesca(@RequestBody PescaDTO pescaDTO) throws Exception {
        Pesca nuevaPesca = new Pesca();
        nuevaPesca.setAnimalesPescados(pescaDTO.getAnimalesPescados());
        nuevaPesca.setPesoPromedio(pescaDTO.getPesoPromedio());

        // Corrige las llamadas a los servicios para obtener las entidades relacionadas
        Lote lote = loteService.findById(pescaDTO.getLoteId());

        // Verifica si las entidades se encontraron en la base de datos
        if (lote == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Lote no encontrado.\"}");
        }

        // Establece las relaciones en el nuevo lote
        nuevaPesca.setLote(lote);

        // Luego, guarda el nuevo lote en la base de datos
        Pesca pescaGuardada = pescaService.save(nuevaPesca);

        return ResponseEntity.status(HttpStatus.OK).body(pescaGuardada);
    }

}