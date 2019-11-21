import java.util.*;
public class Main {
    DButil dbutil = new DButil();
    UserDao userDao = new UserDao();
    PersonDao personDao = new PersonDao();
    public void step_1()throws Exception{
        userDao.createTable(dbutil);//创建数据库表users
        personDao.createTable(dbutil);//创建数据库表person
        //打印结果
        userDao.queAll(dbutil);
        personDao.queAll(dbutil);
        show();
    }
    public void step_2() throws Exception{
        //在表users中插入4行数据
        userDao.addUser(new User("ly","123456"),dbutil);
        userDao.addUser(new User("liming","345678"),dbutil);
        userDao.addUser(new User("test","11111"),dbutil);
        userDao.addUser(new User("test1","12345"),dbutil);
        //在表person中插入3行数据
        personDao.addPerson(new Person("ly","雷力"),dbutil);
        personDao.addPerson(new Person("liming","李明",25),dbutil);
        personDao.addPerson(new Person("test","测试用户",20,"13388449933"),dbutil);
        //打印结果
        userDao.queAll(dbutil);
        personDao.queAll(dbutil);
        show();
    }
    public void step_3() throws Exception{
        //在person表中插入5行数据，对于表中已有的username，根据最新的数据修改；如不存在，首先在user表中插入该username，再插入person表
        personDao.addOrModifyPerson(new Person("ly","王五"),dbutil);
        personDao.addOrModifyPerson(new Person("test2","测试用户2"),dbutil);
        personDao.addOrModifyPerson(new Person("test1","测试用户1",33),dbutil);
        personDao.addOrModifyPerson(new Person("test","张三",23,"18877009966"),dbutil);
        personDao.addOrModifyPerson(new Person("admin","admin"),dbutil);
        //打印结果
        userDao.queAll(dbutil);
        personDao.queAll(dbutil);
        show();
    }
    public void step_4() throws Exception{
        //删除users表中test打头的username
        userDao.delUserOnUsername("test",dbutil);
        //删除person表中test打头的usename
        personDao.delPersonOnUsername("test%",dbutil);
        //打印结果
        userDao.queAll(dbutil);
        personDao.queAll(dbutil);
        show();
    }
    public void show() throws Exception{//输出格式就不搞的很好了
        System.out.println("表users:");
        System.out.println("+*************************************************************************+");
        System.out.println("|字段名 username                   字段名 pass                            |");
        System.out.println("+-------------------------------------------------------------------------+");
        List<User> list = userDao.queAll(dbutil);
        for(User user:list){
            String s1 = user.getUsername();
            String s2 = user.getPassword();
            System.out.printf("%20s"+"\t\t"+"%s"+"\t\t\n",s1,s2);
            System.out.println("+-------------------------------------------------------------------------+");
        }
        List<Person> personList = personDao.queAll(dbutil);
        System.out.println("表person:");
        System.out.println("+*************************************************************************+");
        System.out.println("|字段名 username        字段名 name        字段名age         字段名 teleno|");
        System.out.println("+-------------------------------------------------------------------------+");
        for(Person person:personList){
            String s1 = person.getUsername();
            String s2 = person.getName();
            Integer s3 = person.getAge();
            String s4 = person.getTeleno();
            System.out.printf("%10s"+"\t\t"+"%10s"+"\t\t",s1,s2);
            if(s3!=null) System.out.print(s3);
            System.out.println("\t\t"+s4);
            System.out.println("+-------------------------------------------------------------------------+");
        }
    }
    public static void main(String[] argc)throws Exception{
        Main m = new Main();
        m.userDao.dropTable(m.dbutil);
        m.personDao.dropTable(m.dbutil);
        System.out.println("第一步：");
        m.step_1();
        System.out.println("第二步：");
        m.step_2();
        System.out.println("第三步：");
        m.step_3();
        System.out.println("第四步：");
        m.step_4();
        m.dbutil.close();
    }
}
