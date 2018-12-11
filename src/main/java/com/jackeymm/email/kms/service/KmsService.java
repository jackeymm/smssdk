package com.jackeymm.email.kms.service;

import com.jackeymm.email.kms.domains.KeyPair;
import com.jackeymm.email.kms.exceptions.KmsSystemException;
import com.jackeymm.email.kms.exceptions.KmsTenantNoFoundException;
import com.jackeymm.email.kms.infrastructure.KmsCache;
import com.jackeymm.email.kms.infrastructure.UserKeypairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

@Service
public class KmsService {

    @Autowired
    private CipherAlgorithmService algorithm;

    @Autowired
    private KmsCache kmsCache;

    @Autowired
    private UserKeypairRepository userKeypairRepository;

    public KmsService(CipherAlgorithmService algorithm, UserKeypairRepository userKeypairRepository) {
        this.algorithm = algorithm;
        this.userKeypairRepository = userKeypairRepository;
    }

    public KeyPair register(String token, String temail) {
        this.checkToken(token);
        KeyPair keyPair = this.algorithm.generateKey(token, temail,"RSA");

        int result = userKeypairRepository.register(keyPair);

        if(1 == result){
            kmsCache.setCache(keyPair);
            return keyPair;
        }else{
            throw new KmsSystemException("register failed ~~");
        }
    }

    public Optional<KeyPair> queryKeyPair(String token,String temail) {
        this.checkToken(token);
        KeyPair keyPair = new KeyPair(token, temail);
        Optional olCache = kmsCache.getCache(temail);
        if(!olCache.isPresent()){
            KeyPair result = userKeypairRepository.getByKeyPair(keyPair);
            if(!ObjectUtils.isEmpty(result)){
                kmsCache.setCache(keyPair);
            }
            return Optional.ofNullable(result);
        }else{
            return olCache;
        }

    }

    private void checkToken(String token){
        if (!"kmsToken".equals(token)) {
            throw new KmsTenantNoFoundException("token is not exist~");
        }
    }
}
