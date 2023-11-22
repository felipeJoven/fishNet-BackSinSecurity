package com.fish.fishNet.Controller;

import com.fish.fishNet.Controller.Impl.BaseControllerImpl;
import com.fish.fishNet.Model.SalidaAlimentos;
import com.fish.fishNet.Service.Impl.SalidaAlimentosServiceImpl;
import com.fish.fishNet.Service.SalidaAlimentosService;
import com.fish.fishNet.Dtos.SalidaAlimentosDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/V1/salida-alimentos")
@CrossOrigin(origins="*")
@RequiredArgsConstructor
public class SalidaAlimentosController extends BaseControllerImpl<SalidaAlimentos, SalidaAlimentosServiceImpl> {

    private final SalidaAlimentosService salidaAlimentosService;

    @PostMapping("/registrar")
    public ResponseEntity<SalidaAlimentos> registrarSalida(@RequestBody SalidaAlimentosDTO salidaAlimentosDto) {
        try {
             SalidaAlimentos salidaAlimentos = salidaAlimentosService.salidaAlimento(salidaAlimentosDto);
            return new ResponseEntity<>(salidaAlimentos, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            // Manejar la excepción específica lanzada cuando hay errores en los parámetros o reglas de negocio
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Manejar otras excepciones generales
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}