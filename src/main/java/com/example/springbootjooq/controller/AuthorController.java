package com.example.springbootjooq.controller;


import com.example.springbootjooq.service.AuthorService;
import com.generator.tables.pojos.Author;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

@Slf4j
@RestController()
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @RequestMapping(method = RequestMethod.GET,value = "/delete/{id}")
    public void delete(@PathVariable("id")int id){
        authorService.delete(id);
    }

    @RequestMapping(method = RequestMethod.GET,value = "/insert")
    public void insert(){
        Author author = new Author();
        author.setFirstName("w"+ "5");
        author.setLastName(" ==s");
        authorService.insert(author);
    }
    @RequestMapping(method = RequestMethod.GET,value = "/update/{id}")
    public void update(@PathVariable("id")int id){
        Author author = authorService.selectById(id);
        log.error("author == "+author);
        author.setFirstName("李");
        author.setLastName("-科峰");
       int flag =  authorService.update(author);
       log.error("更新状态"+flag);
       if (flag == 1){
           log.error("更新成功！");
       }else{
           log.error("更新失败！");
       }

    }

    @RequestMapping(method = RequestMethod.GET,value = "/c/{id}")
    public Author select(@PathVariable("id")int id){
        Author author = authorService.selectById(id);
        log.error("author == "+author);
        return author;
    }

    @RequestMapping(method = RequestMethod.GET,value = "/selectAll/{pageNum}/{pageSize}")
    public List<Author> selectAll(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize){
        List<Author> list = authorService.selectAll(pageNum, pageSize);

        return list;
    }

        @RequestMapping(method = RequestMethod.GET,value = "/selectAll")
    public List<Author> selectAll(){
        List<Author> list = authorService.selectAll(0, 10);

        return list;
    }
}
