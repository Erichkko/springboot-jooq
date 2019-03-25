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
        List<Author> result =  dsl.select()
                .from(AUTHOR)
                .where(AUTHOR.ID.eq(id))
                .fetch()
                .into(Author.class);

        return result.get(0);
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
