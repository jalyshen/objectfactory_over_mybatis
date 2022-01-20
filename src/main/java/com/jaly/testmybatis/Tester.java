package com.jaly.testmybatis;

import com.jaly.testmybatis.domain.entity.Member;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value=1)
public class Tester implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        Member member = Member.BY_ID.lookup("123456789");
        if (member != null) {
            System.out.println(member.toString());
        } else {
            System.out.println("Not found the Member by id 123456789");
        }
    }

    /**
     * 测试live template
     */
    public void test() {
        
    }
}
