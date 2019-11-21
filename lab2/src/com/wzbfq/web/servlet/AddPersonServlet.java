package com.wzbfq.web.servlet;

import com.wzbfq.bean.Person;
import com.wzbfq.util.DButil;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.HTMLDocument;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import com.wzbfq.service.PersonService;
import com.wzbfq.service.impl.PersonServiceImpl;
import org.apache.commons.beanutils.BeanUtils;
@WebServlet(name = "addPersonServlet")
public class AddPersonServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1、设置编码格式
        response.setCharacterEncoding("utf-8");
        //2.获取参数
        Map<String, String[]> map = request.getParameterMap();
//        for(Iterator iter = map.entrySet().iterator();iter.hasNext();){
//            Map.Entry element = (Map.Entry)iter.next();
//            Object strKey = element.getKey();
//            Object strObj = element.getValue();
//            System.out.println(strKey+" "+strObj);
//        }
        //3.封装对象
        Person person = new Person();
        try{
            BeanUtils.populate(person,map);
        }catch (Exception e){
            e.printStackTrace();
        }
        //4.调用Service保存
        PersonServiceImpl service = new PersonServiceImpl();
        ServletContext sc = getServletConfig().getServletContext();
        int operatorResult = service.addOrModifyPerson(person,(DButil)sc.getAttribute("DB"));
        request.setAttribute("returnType",0);
        request.setAttribute("operatorResult",operatorResult);
        request.setAttribute("username",person.getUsername());
        //5.跳转到
        request.getRequestDispatcher("/result.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
