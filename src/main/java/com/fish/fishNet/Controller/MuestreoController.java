package com.fish.fishNet.Controller;

import com.fish.fishNet.Controller.Impl.BaseControllerImpl;
import com.fish.fishNet.Model.Muestreo;
import com.fish.fishNet.Service.Impl.MuestreoServiceImpl;
import com.fish.fishNet.Service.MuestreoService;
import com.fish.fishNet.Service.PescaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/V1/muestreo")
@CrossOrigin(origins="*")
public class MuestreoController extends BaseControllerImpl<Muestreo, MuestreoServiceImpl> {

    @Autowired
    private MuestreoService muestreoService;
}

