package com.fish.fishNet.Controller;

import com.fish.fishNet.Controller.Impl.BaseControllerImpl;
import com.fish.fishNet.Dtos.ProveedorDTO;
import com.fish.fishNet.Model.TipoAlimento;
import com.fish.fishNet.Service.Impl.TipoAlimentoServiceImpl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/V1/tipo-alimento")
@CrossOrigin(origins="*")
public class TipoAlimentoController extends BaseControllerImpl<TipoAlimento, TipoAlimentoServiceImpl> {

}