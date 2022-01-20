package com.jaly.testmybatis.infra.mapperimpl;

import com.jaly.testmybatis.domain.entity.Member;
import com.jaly.testmybatis.infra.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberMapperImpl {

    @Autowired
    private MemberMapper memberMapper;

    public MemberMapperImpl() {
    }

    public Member id(String id) {
        return memberMapper.id(id);
    }
}
