package com.jackeymm.email.kms.interfaces;

import com.jackeymm.email.kms.KeyPair;
import com.jackeymm.email.kms.KmsService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.boot.test.web.client.TestRestTemplate;

import javax.xml.ws.Response;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT,properties = "spring.datasource.url=jdbc:h2:mem:kms;MODE=MYSQL")
@ActiveProfiles({"dev","dark", "h2"})
//@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:kms;MODE=MYSQL")
//@ActiveProfiles({"dark", "h2"})
public class UserKeyPairControllerTest {

    private final String token = "token";
    private final String temail = "temail";
    private final KeyPair keyPair = new KeyPair(1L, "abc", "def", "syswin", "a@temail", 0L, 0L);

    @MockBean
    private KmsService kmsService;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void registerUserKeyPairFailed(){
//        Mockito.when(kmsService.register(token,temail)).thenReturn(keyPair);
        HttpEntity<MultiValueMap<String, String>> httpEntity = httpEntityof(token,temail);
        ResponseEntity<Response<KeyPair>> response = testRestTemplate.exchange("/register", POST, httpEntity, responseType());
        Assertions.assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
    }

    private ParameterizedTypeReference<Response<KeyPair>> responseType() {
        return new ParameterizedTypeReference<Response<KeyPair>>(){};
    }

    private HttpEntity httpEntityof(String domain, String temail) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> multiValuedMap = new LinkedMultiValueMap<String, String>();
        multiValuedMap.add("temail","a@temail");
        multiValuedMap.add("domain","domain");

        return new HttpEntity(multiValuedMap, httpHeaders);
    }

}
