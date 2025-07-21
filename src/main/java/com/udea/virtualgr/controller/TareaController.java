package com.udea.virtualgr.controller;

import com.udea.virtualgr.entity.Tarea;
import com.udea.virtualgr.service.TareaService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class TareaController {

    private final TareaService tareaService;

    public TareaController(TareaService tareaService) {
        this.tareaService = tareaService;
    }

    @QueryMapping
    public List<Tarea> allTareas() {
        return tareaService.getAllTareas();
    }

    @QueryMapping
    public Tarea tareaById(@Argument Long id) {
        return tareaService.getTareaById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
    }

    @QueryMapping
    public List<Tarea> tareasByProyecto(@Argument Long proyectoId) {
        return tareaService.getTareasByProyecto(proyectoId);
    }

    @QueryMapping
    public List<Tarea> tareasAsignadasA(@Argument Long usuarioId) {
        return tareaService.getTareasAsignadasA(usuarioId);
    }

    @MutationMapping
    public Tarea createTarea(@Argument String titulo, @Argument String descripcion,
                             @Argument Long proyectoId, @Argument Long creadaPorId,
                             @Argument Long asignadaAId, @Argument String prioridad,
                             @Argument String fechaEntrega) {
        Tarea.PrioridadTarea prioridadTarea = Tarea.PrioridadTarea.valueOf(prioridad);
        LocalDateTime entrega = LocalDateTime.parse(fechaEntrega);
        return tareaService.createTarea(titulo, descripcion, proyectoId, creadaPorId,
                asignadaAId, prioridadTarea, entrega);
    }

    @MutationMapping
    public Tarea updateEstadoTarea(@Argument Long id, @Argument String estado) {
        Tarea.EstadoTarea estadoTarea = Tarea.EstadoTarea.valueOf(estado);
        return tareaService.updateEstadoTarea(id, estadoTarea);
    }

    @MutationMapping
    public Tarea asignarTarea(@Argument Long tareaId, @Argument Long usuarioId) {
        return tareaService.asignarTarea(tareaId, usuarioId);
    }
}