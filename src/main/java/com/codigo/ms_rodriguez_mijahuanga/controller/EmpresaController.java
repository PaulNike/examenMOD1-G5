package com.codigo.ms_rodriguez_mijahuanga.controller;

import com.codigo.ms_rodriguez_mijahuanga.constants.Constants;
import com.codigo.ms_rodriguez_mijahuanga.request.EmpresaRequest;
import com.codigo.ms_rodriguez_mijahuanga.response.ResponseBase;
import com.codigo.ms_rodriguez_mijahuanga.service.EmpresaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/examen")
public class EmpresaController {
    private final EmpresaService empresaService;


    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @PostMapping("/crear")
    public ResponseEntity<ResponseBase> crearEmpresa(@RequestBody EmpresaRequest empresaRequest){
        ResponseBase responseBase = empresaService.crear(empresaRequest);
        if(responseBase.getCode() == Constants.CODIGO_EXITO){
            return ResponseEntity.status(HttpStatus.CREATED).body(responseBase);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBase);
        }
    }

    @GetMapping("/{ruc}")
    public ResponseEntity<ResponseBase> obtenerUno(@PathVariable String ruc){
        ResponseBase responseBase = empresaService.obtenerUno(ruc);
        if(responseBase.getCode() == Constants.CODIGO_EXITO){
            return ResponseEntity.ok(responseBase);
        }else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseBase);
        }
    }

    @GetMapping("/todos")
    public ResponseEntity<ResponseBase> obtenerTodos(){
        ResponseBase responseBase = empresaService.obtenerTodos();
        if(responseBase.getCode() == Constants.CODIGO_EXITO){
            return ResponseEntity.ok(responseBase);
        }else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseBase);
        }
    }

    @PutMapping("/actualizar/{ruc}")
    public ResponseEntity<ResponseBase> actualizar(@PathVariable String ruc,@RequestBody EmpresaRequest empresaRequest){
        ResponseBase responseBase = empresaService.actualizar(ruc,empresaRequest);
        if(responseBase.getCode() == Constants.CODIGO_EXITO){
            return ResponseEntity.ok(responseBase);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBase);
        }
    }

    @DeleteMapping("/{ruc}")
    public ResponseEntity<ResponseBase> borrar(@PathVariable String ruc){
        ResponseBase responseBase = empresaService.borrar(ruc);
        if(responseBase.getCode() == Constants.CODIGO_EXITO){
            return ResponseEntity.ok(responseBase);
        }else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseBase);
        }
    }

    @GetMapping("/mapper/{numero}")
    public ResponseEntity<ResponseBase> getMapper(@PathVariable String numero){
        ResponseBase responseBase = empresaService.getMapper(numero);
        if(responseBase.getCode() == Constants.CODIGO_EXITO){
            return ResponseEntity.ok(responseBase);
        }else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseBase);
        }
    }
}

