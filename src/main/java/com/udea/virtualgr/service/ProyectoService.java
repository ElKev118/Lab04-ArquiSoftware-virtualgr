package com.udea.virtualgr.service;

import com.udea.virtualgr.entity.Proyecto;
import com.udea.virtualgr.entity.Equipo;
import com.udea.virtualgr.repository.ProyectoRepository;
import com.udea.virtualgr.repository.EquipoRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProyectoService {

    private final ProyectoRepository proyectoRepository;
    private final EquipoRepository equipoRepository;

    public ProyectoService(ProyectoRepository proyectoRepository, EquipoRepository equipoRepository) {
        this.proyectoRepository = proyectoRepository;
        this.equipoRepository = equipoRepository;
    }

    public List<Proyecto> getAllProyectos() {
        return proyectoRepository.findAll();
    }

    public Optional<Proyecto> getProyectoById(Long id) {
        return proyectoRepository.findById(id);
    }

    public List<Proyecto> getProyectosByEquipo(Long equipoId) {
        return proyectoRepository.findByEquipoId(equipoId);
    }

    public Proyecto createProyecto(String nombre, String descripcion, Long equipoId,
                                   LocalDateTime fechaInicio, LocalDateTime fechaFinPrevista) {
        Equipo equipo = equipoRepository.findById(equipoId)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));

        if (fechaInicio.isAfter(fechaFinPrevista)) {
            throw new RuntimeException("La fecha de inicio debe ser anterior a la fecha de fin prevista");
        }

        Proyecto proyecto = new Proyecto();
        proyecto.setNombre(nombre);
        proyecto.setDescripcion(descripcion);
        proyecto.setEquipo(equipo);
        proyecto.setEstado(Proyecto.EstadoProyecto.PENDIENTE);
        proyecto.setFechaInicio(fechaInicio);
        proyecto.setFechaFinPrevista(fechaFinPrevista);

        return proyectoRepository.save(proyecto);
    }

    public Proyecto updateEstadoProyecto(Long id, Proyecto.EstadoProyecto estado) {
        Proyecto proyecto = proyectoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

        proyecto.setEstado(estado);
        if (estado == Proyecto.EstadoProyecto.COMPLETADO) {
            proyecto.setFechaCompletado(LocalDateTime.now());
        }

        return proyectoRepository.save(proyecto);
    }
}