package com.codigo.ms_rodriguez_mijahuanga.service.impl;

import com.codigo.ms_rodriguez_mijahuanga.constants.Constants;
import com.codigo.ms_rodriguez_mijahuanga.dao.EmpresaRepository;
import com.codigo.ms_rodriguez_mijahuanga.entity.EmpresaEntity;
import com.codigo.ms_rodriguez_mijahuanga.feign.ClientEmpresa;
import com.codigo.ms_rodriguez_mijahuanga.mapper.EmpresaMapper;
import com.codigo.ms_rodriguez_mijahuanga.redis.RediService;
import com.codigo.ms_rodriguez_mijahuanga.request.EmpresaRequest;
import com.codigo.ms_rodriguez_mijahuanga.response.ResponseBase;
import com.codigo.ms_rodriguez_mijahuanga.response.ResponseSunat;
import com.codigo.ms_rodriguez_mijahuanga.service.EmpresaService;
import com.codigo.ms_rodriguez_mijahuanga.util.Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service

public class EmpresaServiceImpl implements EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final ClientEmpresa clienteEmpresa;
    private final RediService rediService;
    private final EmpresaMapper empresaMapper;

    @Value("${token}")
    private String token;

    public EmpresaServiceImpl(EmpresaRepository empresaRepository, ClientEmpresa clienteEmpresa, RediService rediService, EmpresaMapper empresaMapper) {
        this.empresaRepository = empresaRepository;
        this.clienteEmpresa = clienteEmpresa;
        this.rediService = rediService;
        this.empresaMapper = empresaMapper;
    }

    @Override
    public ResponseBase crear(EmpresaRequest empresaRequest) {
        EmpresaEntity empresa = getEntity(empresaRequest,false,null);
        if(empresa != null){
            return new ResponseBase(Constants.CODIGO_EXITO,Constants.MENSAJE_EXITO, Optional.of(empresaRepository.save(empresa)));
        }else{
            return new ResponseBase(Constants.CODIGO_ERROR, Constants.MENSAJE_ERROR,Optional.empty());
        }
    }

    @Override
    public ResponseBase obtenerUno(String numero) {
        String redis = rediService.getFromRedis(Constants.REDIS_KEY_GUARDAR+numero);
        if(redis != null){
            EmpresaEntity empresaEntity = Util.convertirDesdeString(redis,EmpresaEntity.class);
            return new ResponseBase(Constants.CODIGO_EXITO,Constants.MENSAJE_EXITO, Optional.of(empresaEntity));
        }else{
            EmpresaEntity empresaEntity = empresaRepository.findByNumeroDocumento(numero);
            String data = Util.convertirAString(empresaEntity);
            rediService.saveInRedis(Constants.REDIS_KEY_GUARDAR+numero,data,10);
            return new ResponseBase(Constants.CODIGO_EXITO,Constants.MENSAJE_EXITO, Optional.of(empresaEntity));
        }
    }

    @Override
    public ResponseBase obtenerTodos() {
        Optional<List<EmpresaEntity>> todos = empresaRepository.findByEstado("ACTIVO");
        return new ResponseBase(Constants.CODIGO_EXITO,Constants.MENSAJE_EXITO, todos);
    }

    @Override
    public ResponseBase actualizar(String ruc,EmpresaRequest empresaRequest) {
        boolean existe = empresaRepository.existsByNumeroDocumento(ruc);
        if(existe){
            EmpresaEntity empresaObtenidaBD = empresaRepository.findByNumeroDocumento(ruc);
             EmpresaEntity empresa = getEntity(empresaRequest,true,empresaObtenidaBD);
            if(empresa != null){
                return new ResponseBase(Constants.CODIGO_EXITO,Constants.MENSAJE_EXITO_UPDATE, Optional.of(empresaRepository.save(empresa)));
            }else{
                return new ResponseBase(Constants.CODIGO_ERROR, Constants.MENSAJE_ERROR,Optional.empty());
            }
        }else {
            return null;
        }
    }

    @Override
    public ResponseBase borrar(String numero) {
            EmpresaEntity empresaObtenidaBD = empresaRepository.findByNumeroDocumento(numero);
            if(empresaObtenidaBD != null){
                empresaObtenidaBD.setEstado("INACTIVO");
                empresaObtenidaBD.setUsuarioEliminacion("PRODRIGUEZ");
                empresaObtenidaBD.setFechaEliminacion(new Timestamp(System.currentTimeMillis()));
                return new ResponseBase(Constants.CODIGO_EXITO,Constants.MENSAJE_EXITO_UPDATE, Optional.of(empresaRepository.save(empresaObtenidaBD)));
            }else{
                return new ResponseBase(Constants.CODIGO_ERROR, Constants.MENSAJE_ERROR,Optional.empty());
            }
    }

    @Override
    public ResponseBase getMapper(String numero) {
        EmpresaEntity empresaObtenidaBD = empresaRepository.findByNumeroDocumento(numero);
        return new ResponseBase(Constants.CODIGO_EXITO,Constants.MENSAJE_EXITO, Optional.of(empresaMapper.mapToDto(empresaObtenidaBD)));
    }

    private ResponseSunat getExec(String numero){
        String auth = "Bearer "+token;
        return clienteEmpresa.getInfoSunat(numero,auth);
    }

    private EmpresaEntity getEntity(EmpresaRequest empresaRequest, boolean operacion, EmpresaEntity actualizar){
        EmpresaEntity empresa = new EmpresaEntity();
        ResponseSunat responseSunat = getExec(empresaRequest.getDocumento());
        if(responseSunat != null){
            empresa.setNumeroDocumento(responseSunat.getNumeroDocumento());
            empresa.setRazonSocial(responseSunat.getRazonSocial());
            empresa.setTipoDocumento(responseSunat.getTipoDocumento());
            empresa.setEstado(responseSunat.getEstado());
            empresa.setCondicion(responseSunat.getCondicion());
            empresa.setDireccion(responseSunat.getDireccion());
            empresa.setUbigeo(responseSunat.getUbigeo());
            empresa.setViaTipo(responseSunat.getViaTipo());
            empresa.setViaNombre(responseSunat.getViaNombre());
            empresa.setZonaCodigo(responseSunat.getZonaCodigo());
            empresa.setZonaTipo(responseSunat.getZonaTipo());
            empresa.setNumero(responseSunat.getNumero());
            empresa.setInterior(responseSunat.getInterior());
            empresa.setLote(responseSunat.getLote());
            empresa.setDpto(responseSunat.getDpto());
            empresa.setManzana(responseSunat.getManzana());
            empresa.setKilometro(responseSunat.getKilometro());
            empresa.setDistrito(responseSunat.getDistrito());
            empresa.setProvincia(responseSunat.getProvincia());
            empresa.setDepartamento(responseSunat.getDepartamento());
            empresa.setEsAgenteRetencion(responseSunat.isEsAgenteRetencion());
            if(operacion){
                empresa.setId(actualizar.getId());
                empresa.setUsuarioActualizacion("PRODRIGUEZ2");
                empresa.setFechaCreacion(new Timestamp(System.currentTimeMillis()));
            }else{
                empresa.setUsuarioCreacion("PRODRIGUEZ");
                empresa.setFechaCreacion(new Timestamp(System.currentTimeMillis()));
            }

            return empresa;
        }else {
            return null;
        }
    }
}
