package com.jaly.testmybatis.domain.entity;

import com.jaly.testmybatis.infra.mapperimpl.MemberMapperImpl;
import com.jaly.testmybatis.infra.transactions.TransactionUtils.Lookup;

import java.io.Serializable;

import static com.jaly.testmybatis.infra.transactions.MyBatisUtils.Equals;

public class Member implements Serializable {

    private String id;
    private String memberName;
    private String password;

    /**
     * 通过ID查找Member对象
     * @param id
     * @return
     */
    public static final Lookup<Member, String> BY_ID =
            new Equals<>(MemberMapperImpl.class, Member.class, "id");

    @Override
    public String toString() {
        return "{ id -> " + id + ", memberName -> " + memberName + "}";
    }
    
}
