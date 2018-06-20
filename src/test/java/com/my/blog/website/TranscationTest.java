package com.my.blog.website;

import com.my.blog.website.exception.TipException;
import com.my.blog.website.model.Vo.UserVo;
import com.my.blog.website.service.IUserService;
import com.my.blog.website.service.IOptionService;
import com.my.blog.website.utils.TaleUtils;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 测试数据库事务
 * Created by BlueT on 2017/3/8.
 */
@MapperScan("com.my.blog.website.dao")
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional(rollbackFor = TipException.class)
public class TranscationTest {

    @Resource
    private IUserService userService;

    @Resource
    private IOptionService optionService;

    @org.junit.Test
    public void test() {
        String encodePwd = TaleUtils.MD5encode("admin" + "123456");
        System.out.println(encodePwd);
    }
}
