package com.udea.virtualgr.service;

import com.udea.virtualgr.entity.Equipo;
import com.udea.virtualgr.entity.Usuario;
import com.udea.virtualgr.entity.MiembroEquipo;
import com.udea.virtualgr.repository.EquipoRepository;
import com.udea.virtualgr.repository.UsuarioRepository;
import com.udea.virtualgr.repository.MiembroEquipoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class EquipoService {

    private final EquipoRepository equipoRepository;
    private final UsuarioRepository usuarioRepository;
    private final MiembroEquipoRepository miembroEquipoRepository;

    public EquipoService(EquipoRepository equipoRepository,
                         UsuarioRepository usuarioRepository,
                         MiembroEquipoRepository miembroEquipoRepository) {
        this.equipoRepository = equipoRepository;
        this.usuarioRepository = usuarioRepository;
        this.miembroEquipoRepository = miembroEquipoRepository;
    }

    public List<Equipo> getAllEquipos() {
        return equipoRepository.findAll();
    }

    public Optional<Equipo> getEquipoById(Long id) {
        return equipoRepository.findById(id);
    }

    @Transactional
    public Equipo createEquipo(String nombre, Long creadorId) {
        if (equipoRepository.existsByNombre(nombre)) {
            throw new RuntimeException("Ya existe un equipo con ese nombre");
        }

        Usuario creador = usuarioRepository.findById(creadorId)
                .orElseThrow(() -> new RuntimeException("Usuario creador no encontrado"));

        Equipo equipo = new Equipo();
        equipo.setNombre(nombre);
        equipo.setCreador(creador);

        Equipo equipoGuardado = equipoRepository.save(equipo);

        // Agregar al creador como líder del equipo
        MiembroEquipo miembro = new MiembroEquipo();
        miembro.setEquipo(equipoGuardado);
        miembro.setUsuario(creador);
        miembro.setRol(MiembroEquipo.RolEquipo.LIDER);
        miembroEquipoRepository.save(miembro);

        return equipoGuardado;
    }

    public MiembroEquipo addMiembroEquipo(Long equipoId, Long usuarioId, MiembroEquipo.RolEquipo rol) {
        Equipo equipo = equipoRepository.findById(equipoId)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (miembroEquipoRepository.existsByEquipoIdAndUsuarioId(equipoId, usuarioId)) {
            throw new RuntimeException("El usuario ya es miembro del equipo");
        }

        MiembroEquipo miembro = new MiembroEquipo();
        miembro.setEquipo(equipo);
        miembro.setUsuario(usuario);
        miembro.setRol(rol);

        return miembroEquipoRepository.save(miembro);
    }

    public List<MiembroEquipo> getMiembrosEquipo(Long equipoId) {
        return miembroEquipoRepository.findByEquipoId(equipoId);
    }
}