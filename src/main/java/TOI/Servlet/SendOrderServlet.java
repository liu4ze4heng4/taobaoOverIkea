package TOI.Servlet;

import TOI.dao.DaoFactory;
import TOI.model.SendOrder;
import TOI.model.TradeItem;
import TOI.util.Fahuo;
import org.apache.commons.lang.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SendOrderServlet extends HttpServlet {
    private static String addItemPage = "/jsp/sendorder.jsp";
    private static String fahuoPage="/jsp/fahuo.jsp";
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        request.setCharacterEncoding("UTF-8");
        String method = request.getParameter("method");
        if("createFahuo".equals(method))
        {  List<TradeItem> tradeItems= faHuo(request, response);
            request.setAttribute("tradeItems", tradeItems);

            System.out.println("Redirecting to" + fahuoPage);
            RequestDispatcher dispatcher = context.getRequestDispatcher(fahuoPage);
            dispatcher.forward(request, response);
        }
        else{
        if ("updateOrderInfo".equals(method)) {//更新发货信息
            updateSendOrderInfos(request, response);
        }

        int s_express=StringUtils.isBlank(request.getParameter("s_express"))?0:Integer.valueOf(request.getParameter("s_express"));
        int status=StringUtils.isBlank(request.getParameter("s_status"))?1:Integer.valueOf(request.getParameter("s_status"));
        String  keyWord=request.getParameter("s_keyword");
        String  dateStr=request.getParameter("s_date");
        String  endDateStr=request.getParameter("s_endDate");
        if(StringUtils.isBlank(dateStr))
        {   Calendar calendar=Calendar.getInstance();
            calendar.add(Calendar.HOUR_OF_DAY,-72);
            dateStr = sdf.format(calendar.getTime());
        }
            if(StringUtils.isBlank(endDateStr)){
                Calendar calendar=Calendar.getInstance();
                calendar.add(Calendar.HOUR_OF_DAY,24);
                endDateStr=sdf.format(calendar.getTime());
            }
        List<SendOrder> orders = DaoFactory.getSendOrderDao().searchSendOrder(status, s_express, keyWord, dateStr ,endDateStr);
        request.setAttribute("s_date",dateStr);
        request.setAttribute("s_endDate",endDateStr);
        request.setAttribute("orders", orders);
        System.out.println("!Redirecting to" + addItemPage);
        RequestDispatcher dispatcher = context.getRequestDispatcher(addItemPage);
        dispatcher.forward(request, response);
        return;
        }
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

    private List<TradeItem> faHuo(HttpServletRequest request, HttpServletResponse response) {
        String tmp=request.getParameter("fahuo");
        String[] sendOrderList=tmp.split(",");
        System.out.println(sendOrderList[0]);
        List<SendOrder> sendOrderList1=new ArrayList<SendOrder>();
        for(int i=0;i<sendOrderList.length;i++)
        {
            SendOrder sendOrder1=new SendOrder();
            System.out.println(sendOrderList[i]);
            sendOrder1=DaoFactory.getSendOrderDao().getSendOrderById(Integer.valueOf(sendOrderList[i]));
            sendOrderList1.add(sendOrder1);
        }
        List<TradeItem> tradeItems= Fahuo.createFahuo(sendOrderList1);

        return tradeItems;


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

}

