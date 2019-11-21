package com.wzbfq.web.servlet;

import com.wzbfq.service.UsersService;
import com.wzbfq.service.impl.UsersServiceImpl;
import com.wzbfq.util.DButil;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "delUserServlet")
public class DelUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        String username = request.getParameter("username2");
        UsersService service = new UsersServiceImpl();
        ServletContext sc = getServletConfig().getServletContext();
        int operatorResult = service.delUser(username,(DButil)sc.getAttribute("DB"));
        request.setAttribute("returnType",1);
        request.setAttribute("operatorResult",operatorResult);
        request.setAttribute("username",username);
        //5.跳转到
        //response.sendRedirect(request.getContextPath()+"/operateResultServlet");
        request.getRequestDispatcher("/result.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
