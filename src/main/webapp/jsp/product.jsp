<%@ page contentType="text/html;charset=gb2312"%>
<%@ page import="wyf.zrk.CartBean,wyf.zrk.DBcart,java.util.*"%>
<html>
  <head>
    <title>�ϴ���Ʒ</title>
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
		            <td><br/><br/>��Ʒ���:</td>
		            <td align="left"><input name="productCode" value=""/></td>
		          </tr>
		          <tr>
		            <td align="right">
		              <br/><br/><input type="button" value="�ύ" onclick="check()"/>
		              <input type="hidden" name="action" value="uinfomodify"/>
		            </td>
		            <td align="right"><br/><br/>
		              <a href="javascript:history.back()">�������ﷵ��</a>
		            </td>
		          </tr>
		        </form>
		      </table>
		    </td>
		  </tr>
	</table>
  </body>
</html>