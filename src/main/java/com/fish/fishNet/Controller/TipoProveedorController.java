package com.fish.fishNet.Controller;

import com.fish.fishNet.Controller.Impl.BaseControllerImpl;
import com.fish.fishNet.Model.TipoProveedor;
import com.fish.fishNet.Service.Impl.TipoProveedorServiceImpl;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/V1/tipo-proveedor")
@CrossOrigin(origins="*")
public class TipoProveedorController extends BaseControllerImpl<TipoProveedor, TipoProveedorServiceImpl> {
}
