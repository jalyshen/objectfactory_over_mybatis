package com.jaly.testmybatis.infra.mapper;

import com.jaly.testmybatis.domain.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

/**
 * Member的repository类
 * MyBatis里叫做mapper
 */
@Mapper
public interface MemberMapper {

    @Select("select * from member where id=#{id}")
    @Results({
            @Result(property = "id", column = "id", jdbcType =  JdbcType.VARCHAR),
            @Result(property = "memberName", column = "membername", jdbcType = JdbcType.VARCHAR),
            @Result(property = "password", column = "password", jdbcType = JdbcType.VARCHAR)
    })
    Member id(String id);
}
