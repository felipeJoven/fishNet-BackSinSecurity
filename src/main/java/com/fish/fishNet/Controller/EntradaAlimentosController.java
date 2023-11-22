package com.fish.fishNet.Controller;

import com.fish.fishNet.Service.EntradaAlimentosService;
import com.fish.fishNet.Dtos.EntradaAlimentosDTO;
import com.fish.fishNet.Controller.Impl.BaseControllerImpl;
import com.fish.fishNet.Model.EntradaAlimentos;
import com.fish.fishNet.Service.Impl.EntradaAlimentosServiceImpl;

import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("api/V1/entrada-alimentos")
@CrossOrigin(origins="*")
public class EntradaAlimentosController extends BaseControllerImpl<EntradaAlimentos, EntradaAlimentosServiceImpl> {

    @Autowired
    private EntradaAlimentosService entradaAlimentosService;

    @PostMapping("/registrar")
    public boolean registrarEntrada(@RequestBody EntradaAlimentosDTO entradaAlimentosDto) throws Exception {
        return entradaAlimentosService.entradaAlimentosYaExiste(entradaAlimentosDto);
    }
}
