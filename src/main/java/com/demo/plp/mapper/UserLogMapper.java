package com.demo.plp.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.demo.plp.po.User;


/**
 * 登陆日志
 * @author gaoyiyang
 *
 */
@Mapper
public interface UserLogMapper {
	@Insert("insert into info_user_log(user_id,user_name,ip_addr) values(#{user.id},#{user.username},#{ipAddr})")
	void insert(@Param("user")User user, @Param("ipAddr")String ipAddr); 
}
