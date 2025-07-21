package com.udea.virtualgr.repository;

import com.udea.virtualgr.entity.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {
    List<Tarea> findByProyectoId(Long proyectoId);
    List<Tarea> findByAsignadaAId(Long usuarioId);
    List<Tarea> findByCreadaPorId(Long usuarioId);
}