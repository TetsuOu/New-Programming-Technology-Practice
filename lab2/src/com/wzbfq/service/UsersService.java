package com.wzbfq.service;

import com.wzbfq.bean.User;
import com.wzbfq.util.DButil;

import java.util.List;

public interface UsersService {
    //void addUser(String user);
    int delUser(String username, DButil dButil);
    List<User> queAll(DButil dButil);
}
