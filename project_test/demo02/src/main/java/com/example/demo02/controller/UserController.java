package com.example.demo02.controller;

import com.example.demo02.common.Result;
import com.example.demo02.controller.request.UserPageRequest;
import com.example.demo02.entity.User;
import com.example.demo02.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@CrossOrigin //解决跨域问题
@RestController //标识
@RequestMapping("/user") //一级前缀
public class UserController {

    @Autowired
    IUserService userService;

    @PostMapping("/save")
    public Result save(@RequestBody  User user){
        userService.save(user);
        return Result.success();
    }

    @PostMapping("/account")
    public Result account(@RequestBody  User user){
        userService.handleAccount(user);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody  User user){
        userService.update(user);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        userService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/{id}") //二级前缀
    public Result getById(@PathVariable Integer id){
       User user =userService.getById(id);
        return Result.success(user);
    }

    @GetMapping("/list") //二级前缀
    public Result list(){
        List<User> users =userService.list();
        return Result.success(users);
    }

    @GetMapping("/page") //二级前缀
    public Result page(UserPageRequest userPageRequest){

        return Result.success( userService.page(userPageRequest));
    }

}
