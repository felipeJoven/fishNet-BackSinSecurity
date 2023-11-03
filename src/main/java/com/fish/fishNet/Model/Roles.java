package com.fish.fishNet.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
public class Roles extends Base {

    @Column(name = "nombre_rol", nullable = false)
    private String name;
}
