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
    private final String email = "email";


    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void registerUserKeyPairFailed(){
        HttpEntity<MultiValueMap<String, String>> httpEntity = httpEntityof(token,email);
        ResponseEntity<Response<KeyPair>> responseEntity = testRestTemplate.exchange("/register", POST, httpEntity, responseType());
        assertThat(responseEntity.getStatusCode()).isEqualTo(BAD_REQUEST);
    }

    @Test
    public void registerUserKeyPairSuccessfully(){
        HttpEntity<MultiValueMap<String, String>> httpEntity = httpEntityof("smsToken","abc@email");
        ResponseEntity<Response<KeyPair>> responseEntity = testRestTemplate.exchange("/register", POST, httpEntity, responseType());
        assertThat(responseEntity.getStatusCode()).isEqualTo(CREATED);

    }

    @Test
    public void registerUserKeyPairExist(){
        HttpEntity<MultiValueMap<String, String>> httpEntity = httpEntityof("smsToken","ac@email");
        ResponseEntity<Response<KeyPair>> responseEntity = testRestTemplate.exchange("/register", POST, httpEntity, responseType());
        assertThat(responseEntity.getStatusCode()).isEqualTo(CREATED);
        ResponseEntity<Response<KeyPair>> responseEntity1 = testRestTemplate.exchange("/register", POST, httpEntity, responseType());
        assertThat(responseEntity1.getStatusCode()).isEqualTo(CONFLICT);

    }

    @Test
    public void queryUserKeyPairByEmailFailed(){
        ResponseEntity<Response<KeyPair>> responseEntity = testRestTemplate.exchange("/queryKeyPair/token/{token}/emails/{email}", GET, null, responseType(),"asdf", email);
        assertThat(responseEntity.getStatusCode()).isEqualTo(BAD_REQUEST);
    }

    @Test
    public void queryUserKeyPairByEmailNotFound(){
        ResponseEntity<Response<KeyPair>> responseEntity = testRestTemplate.exchange("/queryKeyPair/token/{token}/emails/{email}", GET, null, responseType(),"smsToken", email);
        assertThat(responseEntity.getStatusCode()).isEqualTo(FORBIDDEN);
    }

    @Test
    public void queryUserKeyPairByEmailSuccessfully(){
        HttpEntity<MultiValueMap<String, String>> httpEntity = httpEntityof("smsToken","ab@email");
        ResponseEntity<Response<KeyPair>> registerResponseEntity = testRestTemplate.exchange("/register", POST, httpEntity, responseType());
        assertThat(registerResponseEntity.getStatusCode()).isEqualTo(CREATED);
        ResponseEntity<Response<KeyPair>> responseEntity = testRestTemplate.exchange("/queryKeyPair/token/{token}/emails/{email}", GET, null, responseType(),"smsToken", "ab@email");

        assertThat(responseEntity.getStatusCode()).isEqualTo(OK);
        KeyPair keyPair = responseEntity.getBody().getData();

        assertThat(keyPair.getEmail()).isEqualTo("ab@email");
        assertThat(keyPair.getToken()).isEqualTo("smsToken");
        assertThat(keyPair.getPublicKey()).isNotNull();
        assertThat(keyPair.getPrivateKey()).isNotNull();
    }


    private ParameterizedTypeReference<Response<KeyPair>> responseType() {
        return new ParameterizedTypeReference<Response<KeyPair>>(){};
    }

    private HttpEntity httpEntityof(String token, String email) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> multiValuedMap = new LinkedMultiValueMap<String, String>();
        multiValuedMap.add("email",email);
        multiValuedMap.add("token",token);

        return new HttpEntity(multiValuedMap, httpHeaders);
    }

}
