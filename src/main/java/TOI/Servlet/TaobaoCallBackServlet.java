package TOI.Servlet;

import TOI.model.User;
import TOI.util.TaobaoUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TaobaoCallBackServlet extends HttpServlet
{
	public  void doGet(HttpServletRequest request,HttpServletResponse response)
              							throws ServletException,IOException
    {
    	this.doPost(request,response);
    }
	public  void doPost(HttpServletRequest request,HttpServletResponse response)
              							throws ServletException,IOException
    {
    	//设置编码格式
    	request.setCharacterEncoding("utf-8");
    	//拿到session对象
    	//拿到输出流对象
    	ServletContext context = getServletContext();
    	String top_session=request.getParameter("top_session");
    	
    	User user= TaobaoUtils.getInstance(top_session).getTBUser();
    	user.setTbToken(top_session);
    	request.getSession().setAttribute("user", user);
    	request.getSession().setAttribute("userName", user.getName());
    	request.getSession().setAttribute("key", user.getTbToken());
    	//
    	
    	RequestDispatcher dispatcher = context.getRequestDispatcher("/jsp/index.jsp");
    	dispatcher.forward(request, response);
    }
    public void pageForward(String msg,String url,HttpServletRequest request,
    					HttpServletResponse response)throws ServletException,IOException
    {
		request.setAttribute("msg",msg);
		ServletContext sc = getServletContext(); 
		RequestDispatcher rd = sc.getRequestDispatcher(url); 
		rd.forward(request,response);   	
    }
}