package com.fish.fishNet.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fish.fishNet.Controller.Impl.BaseControllerImpl;
import com.fish.fishNet.Model.SalidaAlimentos;
import com.fish.fishNet.Service.Impl.SalidaAlimentosServiceImpl;

@RestController
@RequestMapping("api/V1/salida-alimentos")
@CrossOrigin(origins="*")
public class SalidaAlimentosController extends BaseControllerImpl<SalidaAlimentos, SalidaAlimentosServiceImpl> {

}