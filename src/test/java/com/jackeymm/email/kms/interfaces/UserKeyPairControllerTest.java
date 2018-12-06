package com.jackeymm.email.kms.interfaces;

import com.jackeymm.email.kms.KeyPair;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.CREATED;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT,properties = "spring.datasource.url=jdbc:h2:mem:kms;MODE=MYSQL")
//@ActiveProfiles({"dev","dark", "h2"})
//@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:kms;MODE=MYSQL")
@ActiveProfiles({"dark", "h2"})
public class UserKeyPairControllerTest {

    private final String token = "token";
    private final String temail = "temail";
    private final KeyPair keyPair = new KeyPair(1L, "abc", "def", "syswin", "a@temail", 0L, 0L);


    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void registerUserKeyPairFailed(){
        HttpEntity<MultiValueMap<String, String>> httpEntity = httpEntityof(token,temail);
        ResponseEntity<Response<KeyPair>> responseEntity = testRestTemplate.exchange("/register", POST, httpEntity, responseType());
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(BAD_REQUEST);
    }

    @Test
    public void registerUserKeyPairSuccessfully(){
        HttpEntity<MultiValueMap<String, String>> httpEntity = httpEntityof("syswin","a@temail");
        ResponseEntity<Response<KeyPair>> responseEntity = testRestTemplate.exchange("/register", POST, httpEntity, responseType());
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(CREATED);

    }

    @Test
    public void registerUserKeyPairExist(){
        HttpEntity<MultiValueMap<String, String>> httpEntity = httpEntityof("syswin","a@temail");
        ResponseEntity<Response<KeyPair>> responseEntity = testRestTemplate.exchange("/register", POST, httpEntity, responseType());
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(CREATED);
        ResponseEntity<Response<KeyPair>> responseEntity1 = testRestTemplate.exchange("/register", POST, httpEntity, responseType());
        Assertions.assertThat(responseEntity1.getStatusCode()).isEqualTo(CONFLICT);

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
