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

//    TODO:当算法需要变幻时，在此处调整算法入参。可添加至配置文件中进行管理，或者添加选算法的策略
    private String algorithm = "RSA";

    public KeyPair generateKey(String token, String temail){
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
//              TODO:当添加算法时在此处添加逻辑
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
