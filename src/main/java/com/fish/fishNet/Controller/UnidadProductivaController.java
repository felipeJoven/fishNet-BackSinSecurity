package com.fish.fishNet.Controller;

import org.springframework.web.bind.annotation.*;
import com.fish.fishNet.Controller.Impl.BaseControllerImpl;
import com.fish.fishNet.Model.UnidadProductiva;
import com.fish.fishNet.Service.Impl.UnidadProductivaServiceImpl;

@RestController
@RequestMapping("api/V1/unidad-productiva")
@CrossOrigin(origins="*")
public class UnidadProductivaController extends BaseControllerImpl<UnidadProductiva, UnidadProductivaServiceImpl> {

}