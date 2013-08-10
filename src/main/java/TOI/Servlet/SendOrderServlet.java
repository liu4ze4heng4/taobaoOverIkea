package TOI.Servlet;

import TOI.dao.DaoFactory;
import TOI.model.SendOrder;
import org.apache.commons.lang.StringUtils;

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
        String method = request.getParameter("m");
        if ("updateOrderInfo".equals(method)) {//更新发货信息
            updateSendOrderInfos(request, response);
        }

        ServletContext context = getServletContext();

        //读取senderOrder
        List<SendOrder> orders = DaoFactory.getSendOrderDao().getSendOrderByFlag(1);

        request.setAttribute("orders", orders);

        System.out.println("Redirecting to" + addItemPage);
        RequestDispatcher dispatcher = context.getRequestDispatcher(addItemPage);
        dispatcher.forward(request, response);

    }

    private void updateSendOrderInfos(HttpServletRequest request, HttpServletResponse response) {
        int orderNum = Integer.valueOf(request.getParameter("orderNum"));
        for (int i = 0; i < orderNum; i++) {
            int sendOrderId = Integer.valueOf(request.getParameter("ID" + i));
            String expressCode = request.getParameter("EN" + i);
            int status = StringUtils.isBlank(expressCode) ? 1 : 2;
            DaoFactory.getSendOrderDao().updateExpressCode(sendOrderId, expressCode, status);
        }
    }

    private void getOrderList() {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

}

