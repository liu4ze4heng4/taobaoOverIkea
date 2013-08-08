package TOI.Servlet;

import TOI.model.Product;
import TOI.model.User;
import TOI.util.ProductUtils;
import TOI.util.TaobaoUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddItem2TBServlet extends HttpServlet {
	private static String resultPage = "/jsp/result.jsp";

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String itemCode = request.getParameter("productCode");

		// /////////////////////更新product表内一个产品/////////////////////////
		Product p = ProductUtils.grabProductFromIKEA(itemCode);
//		ProductUtils.addToSQL(p, 1);
		// ////////////////////从SQL获取一个产品/////////////////////////
		// Product p = ProductUtils.setFromSQL("S69932184");
		// //////////////////////上传至淘宝/////////////////////////
		User user=(User) request.getSession().getAttribute("user");
		String result="用户为空";
		if(user!=null){
            TaobaoUtils t=TaobaoUtils.getInstance(user.getSessionKey()) ;
			t.addTaobaoItem(p);
			t.uploadExtraPic(p);
		}

		ServletContext context = getServletContext();
		request.setAttribute("msg", "http://item.taobao.com/item.htm?id="+result);

		RequestDispatcher dispatcher = context.getRequestDispatcher(resultPage);
		dispatcher.forward(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
