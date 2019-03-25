package com.example.springbootjooq.service.serviceImpl;

import com.example.springbootjooq.service.AuthorService;
import com.generator.tables.pojos.Author;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

import static com.generator.tables.Author.AUTHOR;

@Slf4j
@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    DSLContext dsl;

    @Override
    public void delete(int id) {
        dsl.delete(AUTHOR).where(AUTHOR.ID.eq(id));
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
        dsl.update(AUTHOR).set((Record) user);
        return 0;
    }

    @Override
    public Author selectById(int id) {
        Result result =  dsl.select(AUTHOR.ID,AUTHOR.FIRST_NAME,AUTHOR.LAST_NAME)
                .from(AUTHOR)
                .where(AUTHOR.ID.eq(id)).fetch();
        System.out.println(result.get(0).toString());
        String className = result.get(0).getClass().getName();
        System.out.println(className);
//        com.fantj.pojos.User user = new com.fantj.pojos.User();
        return null;
    }

    @Override
    public  List<Author> selectAll(int pageNum, int pageSize) {
        List<Author> list = dsl.select().from(AUTHOR)
                .orderBy(AUTHOR.ID.desc())   //id倒序
                .limit(pageSize)   //分页
                .offset(pageNum)   //分页
                .fetch()
                .into(Author.class);  //数据类型格式转化
        return list;
    }
}
