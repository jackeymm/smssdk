package com.jackeymm.email.kms.interfaces;


import com.jackeymm.email.kms.KeyPair;
import com.jackeymm.email.kms.KmsService;
import com.jackeymm.email.kms.exceptions.KmsSystemException;
import com.jackeymm.email.kms.exceptions.KmsTenantNoFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.util.MultiValueMap;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
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
        exchange.getFormData().map(params -> {
            ensureExists(params, TE_MAIL, TOKEN);

            String token = params.getFirst(TOKEN);
            String temail = params.getFirst(TE_MAIL);
            KeyPair keyPair = null;

            keyPair = kmsService.register(token,temail);
            return Response.ok(keyPair);
        });
        throw new KmsSystemException();
    }

    private void ensureExists(MultiValueMap<String, String> params, String... keys) {
        for (String key : keys) {
            if (!params.containsKey(key)) {
                throw new IllegalArgumentException("TeMail address or public key is invalid");
            }
        }
    }

}
