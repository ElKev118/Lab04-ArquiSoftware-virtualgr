package com.udea.virtualgr.controller;

import com.udea.virtualgr.entity.Proyecto;
import com.udea.virtualgr.service.ProyectoService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ProyectoController {

    private final ProyectoService proyectoService;

    public ProyectoController(ProyectoService proyectoService) {
        this.proyectoService = proyectoService;
    }

    @QueryMapping
    public List<Proyecto> allProyectos() {
        return proyectoService.getAllProyectos();
    }

    @QueryMapping
    public Proyecto proyectoById(@Argument Long id) {
        return proyectoService.getProyectoById(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
    }

    @QueryMapping
    public List<Proyecto> proyectosByEquipo(@Argument Long equipoId) {
        return proyectoService.getProyectosByEquipo(equipoId);
    }

    @MutationMapping
    public Proyecto createProyecto(@Argument String nombre, @Argument String descripcion,
                                   @Argument Long equipoId, @Argument String fechaInicio,
                                   @Argument String fechaFinPrevista) {
        LocalDateTime inicio = LocalDateTime.parse(fechaInicio);
        LocalDateTime fin = LocalDateTime.parse(fechaFinPrevista);
        return proyectoService.createProyecto(nombre, descripcion, equipoId, inicio, fin);
    }

    @MutationMapping
    public Proyecto updateEstadoProyecto(@Argument Long id, @Argument String estado) {
        Proyecto.EstadoProyecto estadoProyecto = Proyecto.EstadoProyecto.valueOf(estado);
        return proyectoService.updateEstadoProyecto(id, estadoProyecto);
    }
}