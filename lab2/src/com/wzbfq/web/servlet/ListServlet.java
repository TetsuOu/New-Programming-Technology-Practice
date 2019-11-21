package com.wzbfq.web.servlet;

import com.wzbfq.bean.Person;
import com.wzbfq.bean.User;
import com.wzbfq.service.PersonService;
import com.wzbfq.service.impl.PersonServiceImpl;
import com.wzbfq.service.impl.UsersServiceImpl;
import com.wzbfq.util.DButil;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "listServlet")
public class ListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.调用UserService完成查询
        PersonServiceImpl service = new PersonServiceImpl();
        ServletContext sc = getServletConfig().getServletContext();
        List<Person> personList = service.queAll((DButil)sc.getAttribute("DB"));
        //2.将list存入request域
        request.setAttribute("personList",personList);
        UsersServiceImpl service2 = new UsersServiceImpl();
        List<User> usersList = service2.queAll((DButil)sc.getAttribute("DB"));
        request.setAttribute("usersList",usersList);
        //3.转发到list.jsp
        request.getRequestDispatcher("/list.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
