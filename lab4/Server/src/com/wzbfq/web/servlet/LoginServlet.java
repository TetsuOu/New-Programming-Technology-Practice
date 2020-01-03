package com.wzbfq.web.servlet;

import com.wzbfq.bean.User;
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

@WebServlet(name="loginServlet")
public class LoginServlet extends HttpServlet {
    DButil dButil = new DButil();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置相应内容类型
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        try(PrintWriter out = response.getWriter()){
            String username = request.getParameter("username").trim();
            String password = request.getParameter("password").trim();
            UsersServiceImpl service = new UsersServiceImpl();
            int verigyResult = service.verifyLogin(new User(username,password),dButil);

            Map<String,String> params = new HashMap<>();
            JSONObject jsonObject = new JSONObject();

            if(verigyResult == -1){
                params.put("Result","TheUserDoesNotExist");
            }else if(verigyResult == 0){
                params.put("Result","PasswordError");
            }else if(verigyResult == 1){
                params.put("Result","CorrectPassword");
            }

            jsonObject.put("params",params);
            out.write(jsonObject.toString());

//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>gg</title>");
//            out.println("<head>");
//            out.println("<body>");
//            out.println("</body>");
//            out.println("</html>");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
