package com.fish.fishNet.Controller;

import com.fish.fishNet.Controller.Impl.BaseControllerImpl;
import com.fish.fishNet.Model.SalidaAlimentos;
import com.fish.fishNet.Service.Impl.SalidaAlimentosServiceImpl;
import com.fish.fishNet.Service.SalidaAlimentosService;
import com.fish.fishNet.Dtos.SalidaAlimentosDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/V1/salida-alimentos")
@CrossOrigin(origins="*")
@RequiredArgsConstructor
public class SalidaAlimentosController extends BaseControllerImpl<SalidaAlimentos, SalidaAlimentosServiceImpl> {

    private final SalidaAlimentosService salidaAlimentosService;

    @PostMapping("/registrar")
    public SalidaAlimentos registrarSalida(@RequestBody SalidaAlimentosDto salidaAlimentosDto) {
        return salidaAlimentosService.salidaAlimento(salidaAlimentosDto);
    }
}