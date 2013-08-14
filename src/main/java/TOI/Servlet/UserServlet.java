package TOI.Servlet;

import TOI.dao.DaoFactory;
import TOI.model.SendOrder;
import TOI.model.User;
import org.apache.commons.lang.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserServlet extends BaseServlet {
    private static String loginPage = "/jsp/login.jsp";
    private static String homePage = "/jsp/index.jsp";

    protected void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.setCharacterEncoding("UTF-8");
        String method=request.getParameter("method");
        ServletContext context = getServletContext();
        if("login".equals(method)){
            User user= (User) request.getSession().getAttribute("user");
            if(user!=null){
                RequestDispatcher dispatcher = context.getRequestDispatcher(homePage);
                dispatcher.forward(request, response);
            }  else {
                String userName=request.getParameter("username");
                String password=request.getParameter("password");
                user=DaoFactory.getUserDao().getUser(userName,password) ;
                if(user==null){
                    request.setAttribute("msg","用户名或密码错误！请联系管理员");
                    RequestDispatcher dispatcher = context.getRequestDispatcher(loginPage);
                    dispatcher.forward(request, response);
                }else{
                    request.getSession().setAttribute("user",user);
                    RequestDispatcher dispatcher = context.getRequestDispatcher(homePage);
                    dispatcher.forward(request, response);
                }
            }
        } else if("loginout".equals(method)){

        }

    }


}

