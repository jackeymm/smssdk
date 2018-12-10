package com.jackeymm.email.kms.infrastructure;

import com.jackeymm.email.kms.KeyPair;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserKeypairRepository {

    @Insert("insert into user_keypair (temail, token, public_key, private_key, create_time, update_time) values(#{keyPair.temail}, #{keyPair.token}, #{keyPair.publicKey}, #{keyPair.privateKey}, #{keyPair.createTime}, #{keyPair.updateTime})")
    @Options(useGeneratedKeys=true, keyProperty="keyPair.id", keyColumn="id")
    int register(@Param("keyPair") KeyPair keyPair);

    @Select("select id, temail, token, public_key as publicKey, private_key as privateKey, create_time as createTime, update_time as updateTime from user_keypair where temail = #{keyPair.temail} and token = #{keyPair.token}")
    KeyPair getByKeyPair(@Param("keyPair") KeyPair keyPair);
}
