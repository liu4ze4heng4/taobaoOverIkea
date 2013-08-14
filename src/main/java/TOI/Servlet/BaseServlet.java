package TOI.Servlet;

import TOI.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: zehengliu
 * Date: 13-8-14
 * Time: 下午10:55
 * To change this template use File | Settings | File Templates.
 */
public  abstract class BaseServlet extends HttpServlet {
    private static String loginReq = "/user?method=login";
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        User user= (User) request.getSession().getAttribute("user");
        if(user==null){
            ServletContext context = getServletContext();
            RequestDispatcher dispatcher = context.getRequestDispatcher(loginReq);
            dispatcher.forward(request, response);
        }
        try {
            excute(request,response);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    abstract void  excute(HttpServletRequest request, HttpServletResponse response) throws Exception;

}
