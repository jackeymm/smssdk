package com.jackeymm.email.kms.infrastructure;

import com.jackeymm.email.kms.KeyPair;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserKeypairRepository {

    @Insert("insert into user_keypair (temail, token, public_key, private_key) values(#{keyPair.temail}, #{keyPair.token}, #{keyPair.publicKey}, #{keyPair.privateKey})")
    int register(@Param("keyPair") KeyPair keyPair);

    @Select("select id, temail, token, public_key as publicKey, private_key as privateKey, create_time as createTime, update_time as updateTime from user_keypair where temail = #{keyPair.temail} and token = #{keyPair.token}")
    KeyPair getByKeyPair(@Param("keyPair") KeyPair keyPair);
}
