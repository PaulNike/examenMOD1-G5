package com.codigo.ms_rodriguez_mijahuanga.service;

import com.codigo.ms_rodriguez_mijahuanga.request.EmpresaRequest;
import com.codigo.ms_rodriguez_mijahuanga.response.ResponseBase;

public interface EmpresaService {
    ResponseBase crear(EmpresaRequest empresaRequest);
    ResponseBase obtenerUno(String numero);
    ResponseBase obtenerTodos();
    ResponseBase actualizar(String ruc, EmpresaRequest empresaRequest);
    ResponseBase borrar(String numero);

    ResponseBase getMapper(String numero);

}
