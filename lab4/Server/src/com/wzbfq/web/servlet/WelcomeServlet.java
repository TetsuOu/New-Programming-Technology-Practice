package com.wzbfq.web.servlet;

import com.wzbfq.bean.Person;
import com.wzbfq.service.Impl.PersonServiceImpl;
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

@WebServlet(name="welcomeServlet")
public class WelcomeServlet extends HttpServlet {
    DButil dButil = new DButil();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        try(PrintWriter out = response.getWriter()){
            String username = request.getParameter("username").trim();
            String name = "";
            Integer age = 0;
            String telenum = "18570253175";
            PersonServiceImpl service = new PersonServiceImpl();
            List<Person> personList = service.queAll(dButil);
            for(Person person:personList){
                if(username.equals(person.getUsername())){
                    name = person.getName();
                    age = person.getAge();
                    telenum = person.getTeleno();
                    break;
                }
            }
            Map<String,String> params = new HashMap<>();
            JSONObject jsonObject = new JSONObject();
            params.put("username",username);
            params.put("name",name);
            params.put("age", String.valueOf(age));
            params.put("telenum",telenum);
            jsonObject.put("params",params);
            out.write(jsonObject.toString());

//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>gg</title>");
//            out.println("<head>");
//            out.println("<body>");
//            out.println(params.get("username"));
//            out.println(params.get("name"));
//            out.println(params.get("age"));
//            out.println(params.get("telenum"));
//            out.println("</body>");
//            out.println("</html>");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
