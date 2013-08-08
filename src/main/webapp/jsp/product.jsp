<%@ page contentType="text/html;charset=gb2312"%>
<%@ page import="wyf.zrk.CartBean,wyf.zrk.DBcart,java.util.*"%>
<html>
  <head>
    <title>上传产品</title>
    <script language="javascript" src="script/trim.js"></script>
    <script language="javascript">
      function check()
      {
      	document.mfmodify.submit();
      }
    </script>
  </head>
  <body>
     <table width="100%">
		  <tr>
		    <td><%@ include file="top.jsp" %></td>
		  </tr>
		  <tr align="center">
		    <td>
		      <table>
		        <form action="/IPStest/AddItem2TBServlet" method="post" name="mfmodify">
		          <tr align="center">
		            <td><br/><br/>产品编号:</td>
		            <td align="left"><input name="productCode" value=""/></td>
		          </tr>
		          <tr>
		            <td align="right">
		              <br/><br/><input type="button" value="提交" onclick="check()"/>
		              <input type="hidden" name="action" value="uinfomodify"/>
		            </td>
		            <td align="right"><br/><br/>
		              <a href="javascript:history.back()">单击这里返回</a>
		            </td>
		          </tr>
		        </form>
		      </table>
		    </td>
		  </tr>
	</table>
  </body>
</html>