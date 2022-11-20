package com.ry.mapper;

import com.ry.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

//@CacheNamespace(implementation = RedisCache.class)//开启二级缓存
public interface UserMapper1 {

    public List<User> findAll();

    //添加用户
    @Insert("insert into user values(#{id},#{username},#{password},#{birthday})")
    public void addUser(User user);

    //更新用户
    @Update("update user set username = #{username} where id = #{id}")
    public void updateUser(User user);

    //查询用户
    @Select("select * from user")
    public List<User> selectUser();

    //删除用户
    @Delete("delete from user where id = #{id}")
    public void deleteUser(Integer id);

    //根据id查询用户
//    @Options(useCache = true)
    @Select({"select * from user where id = #{id}"})
    public User findUserById(Integer id);

}
