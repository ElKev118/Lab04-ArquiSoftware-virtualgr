package com.udea.virtualgr.controller;

import com.udea.virtualgr.entity.Equipo;
import com.udea.virtualgr.entity.MiembroEquipo;
import com.udea.virtualgr.service.EquipoService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
public class EquipoController {

    private final EquipoService equipoService;

    public EquipoController(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    @QueryMapping
    public List<Equipo> allEquipos() {
        return equipoService.getAllEquipos();
    }

    @QueryMapping
    public Equipo equipoById(@Argument Long id) {
        return equipoService.getEquipoById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));
    }

    @QueryMapping
    public List<MiembroEquipo> miembrosEquipo(@Argument Long equipoId) {
        return equipoService.getMiembrosEquipo(equipoId);
    }

    @MutationMapping
    public Equipo createEquipo(@Argument String nombre, @Argument Long creadorId) {
        return equipoService.createEquipo(nombre, creadorId);
    }

    @MutationMapping
    public MiembroEquipo addMiembroEquipo(@Argument Long equipoId, @Argument Long usuarioId,
                                          @Argument String rol) {
        MiembroEquipo.RolEquipo rolEquipo = MiembroEquipo.RolEquipo.valueOf(rol);
        return equipoService.addMiembroEquipo(equipoId, usuarioId, rolEquipo);
    }
}