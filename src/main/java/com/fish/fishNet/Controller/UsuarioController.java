package com.fish.fishNet.Controller;

import com.fish.fishNet.Controller.Impl.BaseControllerImpl;
import com.fish.fishNet.Model.Usuario;
import com.fish.fishNet.Service.Impl.UsuarioServiceImpl;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/V1/usuario")
@CrossOrigin(origins="*")
public class UsuarioController extends BaseControllerImpl<Usuario, UsuarioServiceImpl> {

}