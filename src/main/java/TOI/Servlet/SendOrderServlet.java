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

public class SendOrderServlet extends HttpServlet {
	private static String addItemPage = "/jsp/sendorder.jsp";

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = getServletContext();

		//读取senderOrder
        List<SendOrder> orders=DaoFactory.getSendOrderDao().getSendOrderByFlag(1);

		request.setAttribute("orders", orders);

        System.out.println("Redirecting to" + addItemPage);
        RequestDispatcher dispatcher = context.getRequestDispatcher(addItemPage);
		dispatcher.forward(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
    }

}

