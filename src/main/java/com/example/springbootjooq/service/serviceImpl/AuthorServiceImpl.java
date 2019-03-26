package com.example.springbootjooq.service.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.example.springbootjooq.constant.RedisConstant;
import com.example.springbootjooq.service.AuthorService;
import com.example.springbootjooq.util.RedisUtil;
import com.generator.tables.pojos.Author;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.generator.tables.Author.AUTHOR;

@Slf4j
@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    DSLContext dsl;
    @Autowired
    RedisUtil redisUtil;
    @Override
    public void delete(int id) {
        int flag = dsl.delete(AUTHOR).where(AUTHOR.ID.eq(id)).execute();
        log.error("flag =="+flag);
        if (flag == 1){
            log.error("删除成功");
        }else {
            log.error("删除失败");
        }
    }

    @Override
    public void insert(Author user) {
        int flag = dsl.insertInto(AUTHOR).
                columns(AUTHOR.FIRST_NAME,AUTHOR.LAST_NAME).
                values(user.getFirstName(),user.getLastName())
                .execute();

        log.error("添加状态 == "+flag);

    }

    @Override
    public int update(Author user) {

        return   dsl.update(AUTHOR)
                .set(AUTHOR.FIRST_NAME, user.getFirstName())
                .set(AUTHOR.LAST_NAME, user.getLastName())
                .where(AUTHOR.ID.eq(user.getId())).execute();

    }

    @Override
    public Author selectById(int id) {
        Author author = null;
        String redisStr = (String) redisUtil.get(RedisConstant.C_2);
        if (!StringUtils.isEmpty(redisStr)){
            author = JSON.parseObject(redisStr,Author.class);
        }else {
            List<Author> result =  dsl.select()
                    .from(AUTHOR)
                    .where(AUTHOR.ID.eq(id))
                    .fetch()
                    .into(Author.class);
            if (result != null&&result.size() >0){
                  author = result.get(0);
                  redisUtil.set(RedisConstant.C_2,JSON.toJSONString(author));
            }
        }


        return author;
    }

    @Override
    public  List<Author> selectAll(int pageNum, int pageSize) {

        List<Author> configInfos = null;
        String sysInfoMapJson = (String) redisUtil.get(RedisConstant.C_1);
        if (!StringUtils.isEmpty(sysInfoMapJson)) {
            try {
                configInfos = JSON.parseObject(sysInfoMapJson,new TypeReference<List<Author>>(){});
            } catch (Exception e) {
                e.printStackTrace();
                configInfos = null;
            }
        }

        if (configInfos == null) {
            configInfos =  dsl.select().from(AUTHOR)
                    .orderBy(AUTHOR.ID.desc())   //id倒序
                    .limit(pageSize)   //分页
                    .offset(pageNum)   //分页
                    .fetch()
                    .into(Author.class);
            redisUtil.set(RedisConstant.C_1 , JSON.toJSONString(configInfos));
        }


        return configInfos;
    }
}
