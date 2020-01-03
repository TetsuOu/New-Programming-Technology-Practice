package com.wzbfq.service;

import com.wzbfq.bean.User;
import com.wzbfq.util.DButil;

import java.util.List;

public interface UsersService {
    public int verifyLogin(User u, DButil dButil);
    public List<User> queAll(DButil dButil);
    public boolean addUser(User u,DButil dButil);
    public boolean changePassword(User u,DButil dButil);
}
