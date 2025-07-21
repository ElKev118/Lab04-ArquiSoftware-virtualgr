package com.udea.virtualgr.entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RolGlobal rol;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "ultimo_acceso")
    private LocalDateTime ultimoAcceso;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<MiembroEquipo> miembrosEquipo;

    @OneToMany(mappedBy = "creador", cascade = CascadeType.ALL)
    private List<Equipo> equiposCreados;

    @OneToMany(mappedBy = "creadaPor", cascade = CascadeType.ALL)
    private List<Tarea> tareasCreadas;

    @OneToMany(mappedBy = "asignadaA", cascade = CascadeType.ALL)
    private List<Tarea> tareasAsignadas;

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
        this.ultimoAcceso = LocalDateTime.now();
    }

    public enum RolGlobal {
        ESTUDIANTE, PROFESOR
    }
}