package com.example.demo02.service;


import com.example.demo02.controller.request.BaseRequest;
import com.example.demo02.entity.Borrow;
import com.example.demo02.entity.Retur;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IBorrowService {
    List<Borrow> list();

    PageInfo<Borrow> page(BaseRequest baseRequest);

    void save(Borrow obj);

    PageInfo<Retur> pageRetur(BaseRequest baseRequest);

    void saveRetur(Retur obj);

    Borrow getById(Integer id);

    void update(Borrow obj);

    void deleteById(Integer id);

    void deleteReturById(Integer id);
}
