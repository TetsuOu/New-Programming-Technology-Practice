package com.wzbfq.dao;
import java.util.*;
import com.wzbfq.util.DButil;
import com.wzbfq.bean.User;
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

    public void addUser(User u,DButil dbutil) throws Exception {
        String sql = "insert into users(username,pass) values(?, ?)";
        Object[] obj = {u.getUsername(),u.getPassword()};
        dbutil.executeUpdate(sql,obj);
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
}
