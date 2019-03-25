package com.example.springbootjooq.service;

import com.generator.tables.pojos.Author;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;


public interface AuthorService {
    /** 删除 */
    public void delete(int id);
    /** 增加*/
    public void insert(Author user);
    /** 更新*/
    public int update(Author user);
    /** 查询单个*/
    public Author selectById(int id);
    /** 查询全部列表*/
    public List<Author> selectAll(int pageNum, int pageSize);
}
