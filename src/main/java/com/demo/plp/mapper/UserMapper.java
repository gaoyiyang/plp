package com.demo.plp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.demo.plp.po.User;

/**
 * User映射类
 * @author gaoyiyang
 *
 */
@Mapper
public interface UserMapper {
	@Insert("INSERT INTO info_user(ID,username,password) VALUES(#{id,jdbcType=VARCHAR},#{username},#{password})")
	public void insert(User user);
	@Select("SELECT * FROM info_user WHERE username=#{username} and password=#{password}")
	public User select(@Param("username") String username,@Param("password") String password);
	@Select("SELECT * FROM info_user WHERE username=#{username}")
	public User exists(@Param("username") String username);
	@Update("UPDATE info_user SET username=#{username},passwor#{password} WHERE id=#{id}")
	public void update(User user);
	@Select("SELECT * FROM info_user limit #{page},#{pageSize}")
	public List<User> selectAll(@Param("page")int page, @Param("pageSize")int pageSize);
}
