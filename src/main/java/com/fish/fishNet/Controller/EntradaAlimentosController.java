package com.fish.fishNet.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fish.fishNet.Controller.Impl.BaseControllerImpl;
import com.fish.fishNet.Model.EntradaAlimentos;
import com.fish.fishNet.Service.Impl.EntradaAlimentosServiceImpl;

@RestController
@RequestMapping("api/V1/entrada-alimentos")
@CrossOrigin(origins="*")
public class EntradaAlimentosController extends BaseControllerImpl<EntradaAlimentos, EntradaAlimentosServiceImpl> {

}
