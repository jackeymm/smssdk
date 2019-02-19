package com.jackeymm.email.kms.interfaces;

import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import com.jackeymm.email.kms.domains.KeyPair;
import com.jackeymm.email.kms.service.KmsService;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

@RunWith(PactRunner.class)
@Provider("sms-server")
@PactFolder("../target/pacts")
//@PactBroker(host = "172.28.50.206", port = "88")
public class UserKeyPairControllerProviderPactTest{

    private static ConfigurableApplicationContext context;
    private static KmsService kmsService;
    private KeyPair keyPair = new KeyPair("smsToken","a@10email.com");

//    @TestTarget
//    public final Target target = new HttpTarget(8081);

    @BeforeClass
    public static void startKmsService(){
        context = SpringApplication.run(KmsPactApplication.class, "--server.prot=8081","--spring.profiles.active=dev");
        kmsService = context.getBean(KmsService.class);
    }

    @AfterClass
    public static void tearDown() {
        context.close();
    }

    @State("Register - a10 register success")
    public void registerSuccessFully(){
        Mockito.when(kmsService.register(any(String.class), any(String.class))).thenReturn(keyPair);
    }

    @SpringBootApplication
    static class KmsPactApplication{
        public static void main(String[] args){
            SpringApplication.run(KmsPactApplication.class, args);
        }

        @Bean
        KmsService kmsService(){
            return mock(KmsService.class);
        }
    }

}
