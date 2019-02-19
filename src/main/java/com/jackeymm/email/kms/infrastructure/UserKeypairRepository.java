package com.jackeymm.email.kms.infrastructure;

import com.jackeymm.email.kms.domains.KeyPair;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserKeypairRepository {

    @Insert("insert into user_keypair (email, token, public_key, private_key, create_time, update_time) values(#{keyPair.email}, #{keyPair.token}, #{keyPair.publicKey}, #{keyPair.privateKey}, #{keyPair.createTime}, #{keyPair.updateTime})")
    @Options(useGeneratedKeys=true, keyProperty="keyPair.id", keyColumn="id")
    int register(@Param("keyPair") KeyPair keyPair);

    @Select("select id, email, token, public_key as publicKey, private_key as privateKey, create_time as createTime, update_time as updateTime from user_keypair where email = #{keyPair.email} and token = #{keyPair.token}")
    KeyPair getByKeyPair(@Param("keyPair") KeyPair keyPair);
}
