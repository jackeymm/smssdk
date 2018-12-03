package com.jackeymm.email.kms.infrastructure;

import com.jackeymm.email.kms.KeyPair;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserKeypairRepository {

    @Insert("insert into user_keypair (temail, token, public_key, private_key) values(#{temail}, #{token}, #{keyPair.publicKey}, #{keyPair.privateKey})")
    int register(@Param("temail") String temail, @Param("token") String token, @Param("keyPair") KeyPair keyPair);

    @Select("select * from user_keypair where temail = #{temail} and token = #{token}")
    KeyPair getByTemail(String temail, String token);
}
