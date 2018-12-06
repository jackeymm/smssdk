package com.jackeymm.email.kms.interfaces;


import com.jackeymm.email.kms.KeyPair;
import com.jackeymm.email.kms.exceptions.KmsSystemException;
import com.jackeymm.email.kms.service.KmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class UserKeyPairController {

    @Autowired
    private KmsService kmsService;
    private String TE_MAIL = "temail";
    private String TOKEN = "token";

    @PostMapping(value = "/register", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Mono<Response<KeyPair>> register(ServerWebExchange exchange){
        Response.failed(HttpStatus.BAD_REQUEST,"register 1", null);

        return exchange.getFormData().map(params -> {
            System.out.println("in");
            String token = params.getFirst(TOKEN);
            String temail = params.getFirst(TE_MAIL);

            KeyPair keyPair = kmsService.register(token,temail);

            return Response.ok(keyPair);
        });

    }

}
