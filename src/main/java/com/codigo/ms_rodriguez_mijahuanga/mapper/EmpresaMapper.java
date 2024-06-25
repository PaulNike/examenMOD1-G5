package com.codigo.ms_rodriguez_mijahuanga.mapper;

import com.codigo.ms_rodriguez_mijahuanga.dto.EmpresaDTO;
import com.codigo.ms_rodriguez_mijahuanga.entity.EmpresaEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class EmpresaMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public EmpresaDTO mapToDto(EmpresaEntity empresaEntity){
        return modelMapper.map(empresaEntity, EmpresaDTO.class);
    }
    public EmpresaEntity mapToEntity(EmpresaDTO empresaDTO){
        return modelMapper.map(empresaDTO, EmpresaEntity.class);
    }
}
