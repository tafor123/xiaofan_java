package com.example.demo02.controller;

import com.example.demo02.common.Result;
import com.example.demo02.controller.request.BookPageRequest;
import com.example.demo02.entity.Book;
import com.example.demo02.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin //解决跨域问题
@RestController //标识
@RequestMapping("/book") //一级前缀
public class BookController {

    @Autowired
    IBookService bookService;

    

    @PostMapping("/save")
    public Result save(@RequestBody  Book obj){
        bookService.save(obj);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody  Book obj){
        bookService.update(obj);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        bookService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/{id}") //二级前缀
    public Result getById(@PathVariable Integer id){
       Book obj =bookService.getById(id);
        return Result.success(obj);
    }

    @GetMapping("/list") //二级前缀
    public Result list(){
        List<Book> list =bookService.list();
        return Result.success(list);
    }

    @GetMapping("/page") //二级前缀
    public Result page(BookPageRequest pageRequest){

        return Result.success( bookService.page(pageRequest));
    }

}
