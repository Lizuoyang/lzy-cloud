package com.lzy.cloud;

import com.lzy.platform.base.constant.AuthConstant;
import org.junit.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;

/**
 * @ClassName PasswordTest
 * @Description 密码工具测试类
 * @Author LiZuoYang
 * @Date 2022/7/18 15:29
 **/
public class PasswordTest {

    @Test
    public void test() {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String md5Pswd = AuthConstant.BCRYPT + "admin" + DigestUtils.md5DigestAsHex("123456".getBytes());
        String encodePswd = passwordEncoder.encode(md5Pswd);
        System.out.println("encodePswd: " + encodePswd);
        System.out.println(passwordEncoder.matches(md5Pswd, encodePswd));
    }

    @Test
    public void test01() {
        String p1 = "{bcrypt}admin123456";
        String p2 = "{bcrypt}$2a$10$m4ElmKxahCtaAtTRXlVwZOLAnZg9NAw0VMa69fkLd5MN0g1fI61bW";
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        System.out.println(passwordEncoder.matches(p1, p2));
    }
}
