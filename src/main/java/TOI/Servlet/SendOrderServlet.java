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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SendOrderServlet extends HttpServlet {
    private static String addItemPage = "/jsp/sendorder.jsp";
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        request.setCharacterEncoding("UTF-8");
        String method = request.getParameter("m");
        if ("updateOrderInfo".equals(method)) {//更新发货信息
            updateSendOrderInfos(request, response);
        }

        int s_express=StringUtils.isBlank(request.getParameter("s_express"))?0:Integer.valueOf(request.getParameter("s_express"));
        int status=StringUtils.isBlank(request.getParameter("s_status"))?0:Integer.valueOf(request.getParameter("s_status"));
        String  keyWord=request.getParameter("s_keyword");
        String  dateStr=request.getParameter("s_date");
        if(StringUtils.isBlank(dateStr)) dateStr = sdf.format(new Date());
        List<SendOrder> orders = DaoFactory.getSendOrderDao().searchSendOrder(status, s_express, keyWord, dateStr );
        request.setAttribute("s_date",dateStr);
        request.setAttribute("orders", orders);
        System.out.println("Redirecting to" + addItemPage);
        RequestDispatcher dispatcher = context.getRequestDispatcher(addItemPage);
        dispatcher.forward(request, response);
        return;

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

