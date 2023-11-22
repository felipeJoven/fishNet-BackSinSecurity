package com.fish.fishNet.Controller;

import com.fish.fishNet.Controller.Impl.BaseControllerImpl;
import com.fish.fishNet.Model.TipoIdentificacion;
import com.fish.fishNet.Service.Impl.TipoIdentificacionServiceImpl;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/V1/tipo-identificacion")
@CrossOrigin(origins="*")
public class TipoIdentificacionController extends BaseControllerImpl<TipoIdentificacion, TipoIdentificacionServiceImpl> {
}
