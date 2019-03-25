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

@Slf4j
@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    DSLContext dsl;
    com.generator.tables.Author u =   com.generator.tables.Author.AUTHOR.as("a");


    @Override
    public void delete(int id) {
        dsl.delete(u).where(u.ID.eq(id));
    }

    @Override
    public void insert(Author user) {
        int flag = dsl.insertInto(u).
                columns(u.FIRST_NAME,u.LAST_NAME).
                values(user.getFirstName(),user.getLastName())
                .execute();

        log.error("添加状态 == "+flag);

    }

    @Override
    public int update(Author user) {
        dsl.update(u).set((Record) user);
        return 0;
    }

    @Override
    public Author selectById(int id) {
        Result result =  dsl.select(u.ID,u.FIRST_NAME,u.LAST_NAME)
                .from(u)
                .where(u.ID.eq(id)).fetch();
        System.out.println(result.get(0).toString());
        String className = result.get(0).getClass().getName();
        System.out.println(className);
//        com.fantj.pojos.User user = new com.fantj.pojos.User();
        return null;
    }

    @Override
    public Iterator<Author> selectAll(int pageNum, int pageSize) {
        Result result = dsl.select().from(u).fetch();
        return null;
    }
}
