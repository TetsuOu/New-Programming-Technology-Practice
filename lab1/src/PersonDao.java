/*
这个类中不要出现和SQL相关的类比如Connection、Statement、PreparedStatement、ResultSet等
*/
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
public class PersonDao{
    public void createTable(DButil dbutil) throws Exception{
        String sql = "create table person"
                + "("
                + "username varchar(10) not null,"
                + "name varchar(20) not null,"
                + "age int default 18,"
                + "teleno char(11) default '18570253175',"
                + "primary key (name)"
                + ")";
        dbutil.executeUpdate(sql);
    }

    public void addPerson(Person p,DButil dbutil) throws Exception{
        String sql = "insert into person(username,name,age,teleno) values(?, ?, ?, ?)";
        Object[] obj = {p.getUsername(),p.getName(),p.getAge(),p.getTeleno()};
        dbutil.executeUpdate(sql,obj);
    }

    public static void addOrModifyPerson(Person p,DButil dbutil) throws Exception{
        String sql_1 = "select * from person where username=?";
        List<Map<String,Object>> userlist,personlist;
        personlist = dbutil.query(sql_1,p.getUsername());
        if(personlist.isEmpty()){//person表中不存在该username
            String sql_2 = "select * from users where username=?";
            userlist = dbutil.query(sql_2,p.getUsername());
            if(userlist.isEmpty()){//user表中也不存在该username,
                String sql_3 = "insert into users(username,pass) values(?,?)";
                Object[] obj = {p.getUsername(), "888888"};//默认的passwaod为888888
                dbutil.executeUpdate(sql_3,obj);
            }
            String sql_4 = "insert into person(username,name,age,teleno) values(?, ?, ?, ?)";
            Object[] obj = {p.getUsername(),p.getName(),p.getAge(),p.getTeleno()};
            dbutil.executeUpdate(sql_4,obj);
        }
        else{
            String sql_5 = "update person set name=?,age=?,teleno=? where username=?";
            Object[] obj = {p.getName(),p.getAge(),p.getTeleno(),p.getUsername()};
            dbutil.executeUpdate(sql_5,obj);
        }
    }
    public void delPersonOnUsername(String username,DButil dbutil) throws Exception{
        String sql = "delete from person where username like ?";
        dbutil.executeUpdate(sql,username);
    }

    public void dropTable(DButil dbutil) throws Exception{
        String sql = "drop table person";
        dbutil.executeUpdate(sql);
    }

    public List<Person> queAll(DButil dbutil) throws Exception{
        String sql = "select * from person";
        List<Map<String,Object>> list = dbutil.query(sql);
        List<Person> personList = new ArrayList<>();
        Person person = null;
        for(Map<String,Object>map:list){
            person = new Person((String)map.get("username"),(String)map.get("name"),(Integer)map.get("age"),(String)map.get("teleno"));
            personList.add(person);
        }
        return personList;
    }
}