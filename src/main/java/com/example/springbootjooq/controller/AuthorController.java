package com.example.springbootjooq.controller;


import com.example.springbootjooq.service.AuthorService;
import com.generator.tables.pojos.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

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
    @RequestMapping(method = RequestMethod.POST,value = "/update/{id}")
    public void update(@RequestParam Author user){
        authorService.update(user);
    }

    @RequestMapping(method = RequestMethod.GET,value = "/{id}/select")
    public Author select(@PathVariable("id")int id){
        return authorService.selectById(id);
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
