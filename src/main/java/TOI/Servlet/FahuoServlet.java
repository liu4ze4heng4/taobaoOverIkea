package TOI.Servlet;

import TOI.dao.DaoFactory;
import TOI.model.SendOrder;
import TOI.model.TradeItem;
import TOI.util.Fahuo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Wk
 * Date: 13-8-9
 * Time: 下午5:09
 * To change this template use File | Settings | File Templates.
 */

public class FahuoServlet extends HttpServlet {
    private static String fahuoPage="/jsp/fahuo.jsp";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        String sendOrder=request.getParameter("sendorder");
        String[] sendOrderList=sendOrder.split(",");
        List<SendOrder> sendOrderList1=new ArrayList<SendOrder>();
        for(int i=0;i<sendOrderList.length;i++)
        {
            SendOrder sendOrder1=new SendOrder();
            sendOrder1=DaoFactory.getSendOrderDao().getSendOrderById(Integer.valueOf(sendOrderList[i]));
            sendOrderList1.add(sendOrder1);
        }
        List<TradeItem> tradeItems= Fahuo.createFahuo(sendOrderList1);


        request.setAttribute("tradeItems", tradeItems);

        System.out.println("Redirecting to" + fahuoPage);
        RequestDispatcher dispatcher = context.getRequestDispatcher(fahuoPage);
        dispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

}
