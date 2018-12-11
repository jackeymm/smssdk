package com.jackeymm.email.kms.service;


import com.jackeymm.email.kms.domains.KeyPair;
import com.jackeymm.email.kms.exceptions.KmsGenerateKeyPiarParamCheckException;
import com.jackeymm.email.kms.exceptions.KmsRSAGenerateException;
import com.jackeymm.email.kms.util.RSAKeyPairUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class CipherAlgorithmService {
    public KeyPair generateKey(String token, String temail,String algorithm){
        this.checkParam(token, temail);
        try {
            String publicKey="";
            String privateKey="";
            switch (algorithm) {
                case "RSA" :
                    Map keyPairMap = RSAKeyPairUtil.initKey();
                    publicKey = RSAKeyPairUtil.getPublicKeyStr(keyPairMap);
                    privateKey = RSAKeyPairUtil.getPrivateKeyStr(keyPairMap);
                    break;
            }
            long now = System.currentTimeMillis();
            return new KeyPair(publicKey, privateKey, token, temail, now, now);
        } catch (Exception e) {
            throw new KmsRSAGenerateException("initKey error");
        }
    }

    private void checkParam(String token, String temail){
        if(StringUtils.isEmpty(token) ){
            throw new KmsGenerateKeyPiarParamCheckException("generateKey param {token} is empty ");
        }
        if(StringUtils.isEmpty(temail)){
            throw new KmsGenerateKeyPiarParamCheckException("generateKey param {temail} is empty ");
        }
    }

}
