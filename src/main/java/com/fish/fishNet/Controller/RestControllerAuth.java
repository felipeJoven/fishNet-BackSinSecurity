package com.fish.fishNet.Controller;

import com.fish.fishNet.Dtos.DefaultResponseDto;
import com.fish.fishNet.Dtos.DtoLogin;
import com.fish.fishNet.Dtos.DtoRegistro;
import com.fish.fishNet.Dtos.ServiceResponseDTO;
import com.fish.fishNet.Model.Roles;
import com.fish.fishNet.Model.Usuario;
import com.fish.fishNet.Repository.RolesRepository;
import com.fish.fishNet.Repository.UsuarioRepository;
import com.fish.fishNet.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/V1/auth/")
@CrossOrigin("*")
public class RestControllerAuth {

    @Autowired
    private AuthService authService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RolesRepository rolesRepository;

    @PostMapping("register-user")
    public ResponseEntity<DefaultResponseDto> userRegister(@RequestBody DtoRegistro dtoRegistro) {
        ServiceResponseDTO<DefaultResponseDto> response = this.authService.registerByRol(dtoRegistro, "USER");
        return new ResponseEntity<>(response.getMessage(), response.getStatus());
    }

    @PostMapping("register-admin")
    public ResponseEntity<DefaultResponseDto> registrarAdmin(@RequestBody DtoRegistro dtoRegistro) {
        ServiceResponseDTO<DefaultResponseDto> response = this.authService.registerByRol(dtoRegistro, "ADMIN");
        return new ResponseEntity<>(response.getMessage(), response.getStatus());
    }

    @PostMapping("login")
    public ResponseEntity<DefaultResponseDto> login(@RequestBody DtoLogin dtoLogin) {
        ServiceResponseDTO<DefaultResponseDto> response = authService.login(dtoLogin);
        return new ResponseEntity<>(response.getMessage(), response.getStatus());
    }

    @GetMapping("/usuario")
    List<Usuario> getAllUsers(){
        return usuarioRepository.findAll();
    }

    @GetMapping("/roles")
    List<Roles> getAllRoles(){
        return rolesRepository.findAll();
    }
}
