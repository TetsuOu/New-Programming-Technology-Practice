package com.wzbfq.service.Impl;

import com.wzbfq.bean.User;
import com.wzbfq.dao.PersonDao;
import com.wzbfq.service.UsersService;
import com.wzbfq.util.DButil;
import com.wzbfq.dao.UserDao;

import java.util.List;

public class UsersServiceImpl implements UsersService {
    private UserDao userDao = new UserDao();
    @Override
    public int verifyLogin(User u, DButil dButil) {
        List<User> userList = null;
        String username = u.getUsername();
        String password = u.getPassword();
        try{
            userList = userDao.queAll(dButil);
        }catch (Exception e){
            e.printStackTrace();
        }
        boolean hasUser = false;
        boolean rightPass = false;
        for(User user:userList){
            if(user.getUsername().equals(username)){
                hasUser = true;
                if(user.getPassword().equals(password)){
                    rightPass = true;
                }
                break;
            }
        }
        if(!hasUser) return -1;//无该用户
        else if(!rightPass) return 0;//有该用户，但是密码输入错误
        return 1;//有该用户，且密码输入正确
    }

    @Override
    public List<User> queAll(DButil dButil) {
        List<User> userList = null;
        try{
            userList = userDao.queAll(dButil);
        }catch (Exception e){
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public boolean addUser(User u, DButil dButil) {
        boolean isok = false;
        try{
            isok = userDao.addUser(u,dButil);
        }catch (Exception e){
            e.printStackTrace();
        }
        return isok;
    }

    @Override
    public boolean changePassword(User u, DButil dButil) {
        boolean isok = false;
        try{
            isok = userDao.changePassword(u,dButil);
        }catch (Exception e){
            e.printStackTrace();
        }
        return isok;
    }
}
