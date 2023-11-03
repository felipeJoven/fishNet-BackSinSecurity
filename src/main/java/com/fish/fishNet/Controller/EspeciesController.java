package com.fish.fishNet.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fish.fishNet.Controller.Impl.BaseControllerImpl;
import com.fish.fishNet.Model.Especies;
import com.fish.fishNet.Service.Impl.EspeciesServiceImpl;

@RestController
@RequestMapping("api/V1/especies")
@CrossOrigin(origins="*")
public class EspeciesController extends BaseControllerImpl<Especies, EspeciesServiceImpl> {

}
