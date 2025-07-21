package com.udea.virtualgr.repository;

import com.udea.virtualgr.entity.MiembroEquipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MiembroEquipoRepository extends JpaRepository<MiembroEquipo, Long> {
    List<MiembroEquipo> findByEquipoId(Long equipoId);
    List<MiembroEquipo> findByUsuarioId(Long usuarioId);
    boolean existsByEquipoIdAndUsuarioId(Long equipoId, Long usuarioId);
}