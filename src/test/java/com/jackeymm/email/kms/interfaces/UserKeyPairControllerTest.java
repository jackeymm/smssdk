package com.jackeymm.email.kms.interfaces;

import com.jackeymm.email.kms.domains.KeyPair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT,properties = "spring.datasource.url=jdbc:h2:mem:kms;MODE=MYSQL")
//@SpringBootTest(webEnvironment = RANDOM_PORT,properties = "spring.datasource.url=jdbc:h2:me6379m:kms;MODE=MYSQL")
@ActiveProfiles({"dark", "h2"})
public class UserKeyPairControllerTest {

    private final String token = "token";
    private final String temail = "temail";


    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void registerUserKeyPairFailed(){
        HttpEntity<MultiValueMap<String, String>> httpEntity = httpEntityof(token,temail);
        ResponseEntity<Response<KeyPair>> responseEntity = testRestTemplate.exchange("/register", POST, httpEntity, responseType());
        assertThat(responseEntity.getStatusCode()).isEqualTo(BAD_REQUEST);
    }

    @Test
    public void registerUserKeyPairSuccessfully(){
        HttpEntity<MultiValueMap<String, String>> httpEntity = httpEntityof("kmsToken","abc@temail");
        ResponseEntity<Response<KeyPair>> responseEntity = testRestTemplate.exchange("/register", POST, httpEntity, responseType());
        assertThat(responseEntity.getStatusCode()).isEqualTo(CREATED);

    }

    @Test
    public void registerUserKeyPairExist(){
        HttpEntity<MultiValueMap<String, String>> httpEntity = httpEntityof("kmsToken","ac@temail");
        ResponseEntity<Response<KeyPair>> responseEntity = testRestTemplate.exchange("/register", POST, httpEntity, responseType());
        assertThat(responseEntity.getStatusCode()).isEqualTo(CREATED);
        ResponseEntity<Response<KeyPair>> responseEntity1 = testRestTemplate.exchange("/register", POST, httpEntity, responseType());
        assertThat(responseEntity1.getStatusCode()).isEqualTo(CONFLICT);

    }

    @Test
    public void queryUserKeyPairByTemailFailed(){
        ResponseEntity<Response<KeyPair>> responseEntity = testRestTemplate.exchange("/queryKeyPair/token/{token}/temails/{temail}", GET, null, responseType(),"asdf", temail);
        assertThat(responseEntity.getStatusCode()).isEqualTo(BAD_REQUEST);
    }

    @Test
    public void queryUserKeyPairByTemailNotFound(){
        ResponseEntity<Response<KeyPair>> responseEntity = testRestTemplate.exchange("/queryKeyPair/token/{token}/temails/{temail}", GET, null, responseType(),"kmsToken", temail);
        assertThat(responseEntity.getStatusCode()).isEqualTo(FORBIDDEN);
    }

    @Test
    public void queryUserKeyPairByTemailSuccessfully(){
        HttpEntity<MultiValueMap<String, String>> httpEntity = httpEntityof("kmsToken","ab@temail");
        ResponseEntity<Response<KeyPair>> registerResponseEntity = testRestTemplate.exchange("/register", POST, httpEntity, responseType());
        assertThat(registerResponseEntity.getStatusCode()).isEqualTo(CREATED);
        ResponseEntity<Response<KeyPair>> responseEntity = testRestTemplate.exchange("/queryKeyPair/token/{token}/temails/{temail}", GET, null, responseType(),"kmsToken", "ab@temail");

        assertThat(responseEntity.getStatusCode()).isEqualTo(OK);
        KeyPair keyPair = responseEntity.getBody().getData();

        assertThat(keyPair.getTemail()).isEqualTo("ab@temail");
        assertThat(keyPair.getToken()).isEqualTo("kmsToken");
        assertThat(keyPair.getPublicKey()).isNotNull();
        assertThat(keyPair.getPrivateKey()).isNotNull();
    }


    private ParameterizedTypeReference<Response<KeyPair>> responseType() {
        return new ParameterizedTypeReference<Response<KeyPair>>(){};
    }

    private HttpEntity httpEntityof(String token, String temail) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> multiValuedMap = new LinkedMultiValueMap<String, String>();
        multiValuedMap.add("temail",temail);
        multiValuedMap.add("token",token);

        return new HttpEntity(multiValuedMap, httpHeaders);
    }

}
