package com.fish.fishNet.Service.Impl;

import com.fish.fishNet.Dtos.DefaultResponseDto;
import com.fish.fishNet.Dtos.DtoLogin;
import com.fish.fishNet.Dtos.DtoRegistro;
import com.fish.fishNet.Dtos.ServiceResponseDTO;
import com.fish.fishNet.Model.Roles;
import com.fish.fishNet.Model.Usuario;
import com.fish.fishNet.Repository.RolesRepository;
import com.fish.fishNet.Repository.UsuarioRepository;
import com.fish.fishNet.Service.AuthService;
import com.fish.fishNet.security.JwtGenerador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtGenerador jwtGenerador;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Override
    public ServiceResponseDTO<DefaultResponseDto> login(DtoLogin dtoLogin) {
        try {
            Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(dtoLogin.getEmail());
            if (optionalUsuario.isPresent() && passwordEncoder.matches(dtoLogin.getPassword(), optionalUsuario.get().getPassword())) {
                Authentication authentication = new UsernamePasswordAuthenticationToken(dtoLogin.getEmail(), dtoLogin.getPassword());
                return new ServiceResponseDTO<>(new DefaultResponseDto(jwtGenerador.generarToken(authentication)), HttpStatus.OK);
            } else return new ServiceResponseDTO<>(new DefaultResponseDto("Credenciales invalidas"), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ServiceResponseDTO<>(new DefaultResponseDto(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ServiceResponseDTO<DefaultResponseDto> registerByRol(DtoRegistro dtoRegistro, String rol) {
        try {
            if (usuarioRepository.existsByEmail(dtoRegistro.getEmail())) {
                return new ServiceResponseDTO<>(new DefaultResponseDto("El correo electrónico ya existe, intenta con otro"), HttpStatus.BAD_REQUEST);
            }
            if (!usuarioService.passwordsEqual(dtoRegistro.getPassword(), dtoRegistro.getConfirmarPassword())) {
                return new ServiceResponseDTO<>(new DefaultResponseDto("Las contraseñas no coinciden"), HttpStatus.BAD_REQUEST);
            }
            Usuario usuario = new Usuario();
            usuario.setUsername(dtoRegistro.getUsername());
            usuario.setPassword(passwordEncoder.encode(dtoRegistro.getPassword()));
            usuario.setNombre(dtoRegistro.getNombre());
            usuario.setApellido(dtoRegistro.getApellido());
            usuario.setEmail(dtoRegistro.getEmail());
            usuario.setTelefono(dtoRegistro.getTelefono());
            Optional<Roles> optionalRoles = rolesRepository.findByName(rol);
            if (optionalRoles.isPresent()) {
                usuario.setRoles(Collections.singletonList(optionalRoles.get()));
                usuarioRepository.save(usuario);
                return new ServiceResponseDTO<>(new DefaultResponseDto("Registro de usuario exitoso"), HttpStatus.OK);
            } else return  new ServiceResponseDTO<>(new DefaultResponseDto("El rol no existe"), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ServiceResponseDTO<>(new DefaultResponseDto(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}