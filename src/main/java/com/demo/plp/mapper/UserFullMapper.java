package com.demo.plp.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.demo.plp.po.User;
import com.demo.plp.po.UserFull;

@Mapper
public interface UserFullMapper {
	@Insert("INSERT INTO info_full_user(id,user_id,nickname,email,phone,qq,gender)"
			+ " VALUES(#{id},#{userId},#{nickname},#{email},#{phone},#{qq},#{gender})")
	public void insert(UserFull userFull);
	@Select("SELECT * FROM info_full_user WHERE user_id=#{userId}")
	public UserFull select(String userId);
	@Update("UPDATE info_full_user SET nickname=#{nickname},email=#{email},phone=#{phone},qq=#{qq},gender=#{gender} WHERE id=#{id}")
	public void update(UserFull userFull);
}
