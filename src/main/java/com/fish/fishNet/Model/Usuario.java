package com.fish.fishNet.Model;   

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario extends Base {
    
    @Column(name="usuario", nullable = false)
    private String username;
    
    @Column(name="contraseña", nullable = false)
    private String password;
    
    @Column(name="nombre", nullable = false)
    private String nombre;
    
    @Column(name="apellido", nullable = false)
    private String apellido;    
    
    @Column(name="telefono", nullable = false)
    private Integer telefono;
    
    @Column(name="correo", nullable = false)
    private String email;

    // Relaciones
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Roles> roles = new ArrayList<>();
    
}
