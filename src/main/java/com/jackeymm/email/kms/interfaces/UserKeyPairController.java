package com.jackeymm.email.kms.interfaces;


import com.jackeymm.email.kms.domains.KeyPair;
import com.jackeymm.email.kms.exceptions.KmsEmailNoFoundException;
import com.jackeymm.email.kms.service.KmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class UserKeyPairController {

    @Autowired
    private KmsService kmsService;
    private String EMAIL = "email";
    private String TOKEN = "token";

    @PostMapping(value = "/register", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(CREATED)
    Mono<Response<KeyPair>> register(ServerWebExchange exchange){
        return exchange.getFormData().map(params -> {
            String token = params.getFirst(TOKEN);
            String email = params.getFirst(EMAIL);

            KeyPair keyPair = kmsService.register(token,email);

            return Response.ok(keyPair);
        });

    }

    @GetMapping(value = "/queryKeyPair/token/{token}/emails/{email}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    Mono<Response<KeyPair>> queryKeyPair(@PathVariable String token, @PathVariable String email){
        return Mono.fromSupplier(() -> kmsService.queryKeyPair(token,email)
                .map(Response::ok)
                .orElseThrow(() -> new KmsEmailNoFoundException("No such email keyPair found: " + email)));
    }

}
