package com.codigo.ms_rodriguez_mijahuanga.dao;

import com.codigo.ms_rodriguez_mijahuanga.entity.EmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<EmpresaEntity,Long> {


    EmpresaEntity findByNumeroDocumento(String nummero);
    boolean existsByNumeroDocumento(String numero);
    Optional<List<EmpresaEntity>> findByEstado(String estado);
}
