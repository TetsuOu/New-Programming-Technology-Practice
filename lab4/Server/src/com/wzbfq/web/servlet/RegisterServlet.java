package com.wzbfq.web.servlet;

import com.wzbfq.bean.Person;
import com.wzbfq.bean.User;
import com.wzbfq.service.Impl.PersonServiceImpl;
import com.wzbfq.service.Impl.UsersServiceImpl;
import com.wzbfq.util.DButil;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name="registerServlet")
public class RegisterServlet extends HttpServlet {
    DButil dButil = new DButil();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置相应内容类型
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        try(PrintWriter out = response.getWriter()){
            String username = request.getParameter("username").trim();
            String name = request.getParameter("name").trim();
            String password = request.getParameter("password").trim();
            Integer age = Integer.valueOf(request.getParameter("age").trim());
            String telenum = request.getParameter("telenum").trim();

            PersonServiceImpl personService = new PersonServiceImpl();
            UsersServiceImpl usersService = new UsersServiceImpl();

            List<Person> personList = personService.queAll(dButil);
            List<User> userList = usersService.queAll(dButil);
            Map<String,String> params = new HashMap<>();
            JSONObject jsonObject = new JSONObject();
            boolean hasName = false;
            boolean hasUserName = false;
            //name是否被注册
            for(Person person:personList){
                if(name.equals(person.getName())){
                    hasName = true;
                    break;
                }
            }
            //username是否被注册
            for(User user:userList){
                if(username.equals(user.getUsername())){
                    hasUserName = true;
                    break;
                }
            }
            if(hasName) {
                params.put("Result","TheNameAlreadyExists");
            }
            else if(hasUserName){
                params.put("Result","TheUsernameAlreadyExists");
            }
            else{
                personService.addPerson(new Person(username,name,age,telenum),dButil);
                usersService.addUser(new User(username,password),dButil);
                params.put("Result","SignUpSucceed");
            }

            jsonObject.put("params",params);
            out.write(jsonObject.toString());

//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>gg</title>");
//            out.println("<head>");
//            out.println("<body>");
//            out.println(params.get("Result"));
//            out.println("</body>");
//            out.println("</html>");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
