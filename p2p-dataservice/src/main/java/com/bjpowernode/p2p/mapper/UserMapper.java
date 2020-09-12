package com.bjpowernode.p2p.mapper;

import com.bjpowernode.p2p.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.jute.compiler.JString;
import org.springframework.stereotype.Component;

@Mapper
@Component(value ="userMapper")
public interface UserMapper {

    @Select(value ="select * from  u_user where phone=#{phone} and loginPassword=#{loginPassword}")
    User selectUserByPhoneAndPassowrd(String phone, String loginPassword);

    void updateLastLoginTime(User user);


    @Select(value="select count(id) from u_user where phone=#{phone}")
    int checkUserByPhone(String phone);

 //   @Insert("insert into t_user (name,age,pwd) values(#{name},#{age},#{pwd})")
    @Insert("insert into u_user (phone,loginPassword,add_time) values (#{phone},#{loginPassword},#{addTime})")
    void insertUser(User user);
    @Update(value = "")
    void updateUserRealName(User user);

}