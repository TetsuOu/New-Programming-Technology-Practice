package com.wzbfq.dao;

import com.wzbfq.bean.User;
import com.wzbfq.util.DButil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserDao {
    public void createTable(DButil dbutil) throws Exception {
        String sql = "create table users"
                + "( "
                + "username varchar(10) not null,"
                + "pass varchar(8) not null,"
                + "primary key (username)"
                + ")";
        dbutil.executeUpdate(sql);
    }

    public boolean addUser(User u,DButil dbutil) throws Exception {
        String sql = "insert into users(username,pass) values(?, ?)";
        Object[] obj = {u.getUsername(),u.getPassword()};
        int isok = dbutil.executeUpdate(sql,obj);
        if(isok==0) return false;
        else return true;
    }

    public int delUserOnUsername(String username,DButil dbutil) throws Exception {
        String sql = "delete from users where username like ?";
        return dbutil.executeUpdate(sql,username);
    }

    public void dropTable(DButil dbutil) throws Exception{
        String sql = "drop table users";
        dbutil.executeUpdate(sql);
    }

    public List<User> queAll(DButil dbutil) throws Exception {
        String sql = "select * from users";
        List<Map<String,Object>> list = dbutil.query(sql);
        List<User> userList = new ArrayList<>();
        User user = null;
        for(Map<String,Object> map:list){
            user = new User((String)map.get("username"),(String)map.get("pass"));
            //System.out.println(map.get("username")+" "+map.get("pass"));
            userList.add(user);
        }
        return userList;
    }

    public boolean changePassword(User user, DButil dButil){
        String sql = "update users set pass=? where username=?";
        Object[] objects = {user.getPassword(),user.getUsername()};
        if(dButil.executeUpdate(sql,objects)==1) return true;
        else return false;
    }
}
