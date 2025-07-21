package com.udea.virtualgr.service;

import com.udea.virtualgr.entity.Tarea;
import com.udea.virtualgr.entity.Proyecto;
import com.udea.virtualgr.entity.Usuario;
import com.udea.virtualgr.entity.MiembroEquipo;
import com.udea.virtualgr.repository.TareaRepository;
import com.udea.virtualgr.repository.ProyectoRepository;
import com.udea.virtualgr.repository.UsuarioRepository;
import com.udea.virtualgr.repository.MiembroEquipoRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TareaService {

    private final TareaRepository tareaRepository;
    private final ProyectoRepository proyectoRepository;
    private final UsuarioRepository usuarioRepository;
    private final MiembroEquipoRepository miembroEquipoRepository;

    public TareaService(TareaRepository tareaRepository,
                        ProyectoRepository proyectoRepository,
                        UsuarioRepository usuarioRepository,
                        MiembroEquipoRepository miembroEquipoRepository) {
        this.tareaRepository = tareaRepository;
        this.proyectoRepository = proyectoRepository;
        this.usuarioRepository = usuarioRepository;
        this.miembroEquipoRepository = miembroEquipoRepository;
    }

    public List<Tarea> getAllTareas() {
        return tareaRepository.findAll();
    }

    public Optional<Tarea> getTareaById(Long id) {
        return tareaRepository.findById(id);
    }

    public List<Tarea> getTareasByProyecto(Long proyectoId) {
        return tareaRepository.findByProyectoId(proyectoId);
    }

    public List<Tarea> getTareasAsignadasA(Long usuarioId) {
        return tareaRepository.findByAsignadaAId(usuarioId);
    }

    public Tarea createTarea(String titulo, String descripcion, Long proyectoId,
                             Long creadaPorId, Long asignadaAId, Tarea.PrioridadTarea prioridad,
                             LocalDateTime fechaEntrega) {
        Proyecto proyecto = proyectoRepository.findById(proyectoId)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

        Usuario creador = usuarioRepository.findById(creadaPorId)
                .orElseThrow(() -> new RuntimeException("Usuario creador no encontrado"));

        Usuario asignado = null;
        if (asignadaAId != null) {
            asignado = usuarioRepository.findById(asignadaAId)
                    .orElseThrow(() -> new RuntimeException("Usuario asignado no encontrado"));

            // Verificar que el usuario asignado sea miembro del equipo
            boolean esMiembro = miembroEquipoRepository.existsByEquipoIdAndUsuarioId(
                    proyecto.getEquipo().getId(), asignadaAId);
            if (!esMiembro) {
                throw new RuntimeException("El usuario asignado debe ser miembro del equipo");
            }
        }

        if (fechaEntrega.isAfter(proyecto.getFechaFinPrevista())) {
            throw new RuntimeException("La fecha de entrega no puede ser posterior a la fecha de fin del proyecto");
        }

        Tarea tarea = new Tarea();
        tarea.setTitulo(titulo);
        tarea.setDescripcion(descripcion);
        tarea.setProyecto(proyecto);
        tarea.setCreadaPor(creador);
        tarea.setAsignadaA(asignado);
        tarea.setEstado(Tarea.EstadoTarea.PENDIENTE);
        tarea.setPrioridad(prioridad);
        tarea.setFechaEntrega(fechaEntrega);

        return tareaRepository.save(tarea);
    }

    public Tarea updateEstadoTarea(Long id, Tarea.EstadoTarea estado) {
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        tarea.setEstado(estado);
        if (estado == Tarea.EstadoTarea.COMPLETADA) {
            tarea.setFechaCompletado(LocalDateTime.now());
        }

        return tareaRepository.save(tarea);
    }

    public Tarea asignarTarea(Long tareaId, Long usuarioId) {
        Tarea tarea = tareaRepository.findById(tareaId)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Verificar que el usuario sea miembro del equipo
        boolean esMiembro = miembroEquipoRepository.existsByEquipoIdAndUsuarioId(
                tarea.getProyecto().getEquipo().getId(), usuarioId);
        if (!esMiembro) {
            throw new RuntimeException("El usuario debe ser miembro del equipo");
        }

        tarea.setAsignadaA(usuario);
        return tareaRepository.save(tarea);
    }
}