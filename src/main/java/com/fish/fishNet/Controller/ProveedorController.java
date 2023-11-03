package com.fish.fishNet.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fish.fishNet.Controller.Impl.BaseControllerImpl;
import com.fish.fishNet.Model.Proveedor;
import com.fish.fishNet.Service.Impl.ProveedorServiceImpl;

@RestController
@RequestMapping("api/V1/proveedor")
@CrossOrigin(origins="*")
public class ProveedorController extends BaseControllerImpl<Proveedor, ProveedorServiceImpl> {

}