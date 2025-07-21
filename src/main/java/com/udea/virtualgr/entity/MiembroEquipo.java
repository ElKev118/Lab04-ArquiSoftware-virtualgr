package com.udea.virtualgr.entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "miembro_equipo")
public class MiembroEquipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "equipo_id", nullable = false)
    private Equipo equipo;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RolEquipo rol;

    @Column(name = "fecha_incorporacion")
    private LocalDateTime fechaIncorporacion;

    @PrePersist
    public void prePersist() {
        this.fechaIncorporacion = LocalDateTime.now();
    }

    public enum RolEquipo {
        MIEMBRO, LIDER
    }
}