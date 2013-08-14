package TOI.Servlet;

import TOI.dao.DaoFactory;
import TOI.model.SendOrder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Wk
 * Date: 13-8-9
 * Time: 下午5:09
 * To change this template use File | Settings | File Templates.
 */

public class FahuoServlet extends BaseServlet {
    private static String addItemPage = "/jsp/fahuo.jsp";

    protected void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();

        //读取senderOrder
        List<SendOrder> orders= DaoFactory.getSendOrderDao().getSendOrderByFlag(1);

        request.setAttribute("orders", orders);

        System.out.println("Redirecting to" + addItemPage);
        RequestDispatcher dispatcher = context.getRequestDispatcher(addItemPage);
        dispatcher.forward(request, response);
    }

}
