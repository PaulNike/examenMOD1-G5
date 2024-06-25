package com.codigo.ms_rodriguez_mijahuanga.feign;

import com.codigo.ms_rodriguez_mijahuanga.response.ResponseSunat;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "client-sunat",url = "https://api.apis.net.pe/v2/sunat/")
public interface ClientEmpresa {
    @GetMapping("/ruc")
    ResponseSunat getInfoSunat(@RequestParam("numero") String numero,
                               @RequestHeader("Authorization") String authorization);
}
