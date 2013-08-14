<%@ page import="TOI.model.User" %>
<%@ page contentType="text/html;charset=utf-8" %>

<table width="960" border="0" align="center" cellpadding="0" cellspacing="0">
    <tbody>
    <tr>
        <td height="80"><img src="/IPS/images/logo.png" width="400" height="67"></td>
        <%
            User user= (User) request.getSession().getAttribute("user");
            String userName= (user==null)?"":user.getName() ;
        %>
        <td height="80"><%=userName%>  <a href='/IPS/user?method=loginout'>[注销]</a></td>
    </tr>
    </tbody>
</table>

<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tbody>
    <tr>
        <td height="35" align="center" bgcolor="#5BA707">
            <table width="960" height="35" border="0" cellpadding="0" cellspacing="0"
                   style="color:#FFFFFF; font-weight:bold">
                <tbody>
                <tr>
                    <td width="2" align="center" onmouseover="this.style.background='#69B310'"
                        onmouseout="this.style.background='#5BA707'"><img src="/IPS/images/other/split.jpg"></td>
                    <td width="135" align="center" onmouseover="this.style.background='#69B310'"
                        onmouseout="this.style.background='#5BA707'"
                        style="background-color: rgb(91, 167, 7); background-position: initial initial; background-repeat: initial initial;">
                        <a href="/IPS/jsp/index.jsp" class="a1">首页</a></td>
                    <td width="2" align="center" onmouseover="this.style.background='#69B310'"
                        onmouseout="this.style.background='#5BA707'"><img src="/IPS/images/other/split.jpg"></td>
                    <td width="135" align="center" onmouseover="this.style.background='#69B310'"
                        onmouseout="this.style.background='#5BA707'"><a href="/IPS/jsp/userInfo.jsp" class="a1">用户信息</a>
                    </td>
                    <td width="2" align="center" onmouseover="this.style.background='#69B310'"
                        onmouseout="this.style.background='#5BA707'"><img src="/IPS/images/other/split.jpg"></td>
                    <td width="135" align="center" onmouseover="this.style.background='#69B310'"
                        onmouseout="this.style.background='#5BA707'"><a href="/IPS/jsp/product.jsp" class="a1">上传宝贝</a></td>
                    <td width="2" align="center" onmouseover="this.style.background='#69B310'"
                        onmouseout="this.style.background='#5BA707'"><img src="/IPS/images/other/split.jpg"></td>
                    <td width="135" align="center" onmouseover="this.style.background='#69B310'"
                        onmouseout="this.style.background='#5BA707'"><a href="/IPS/sendOrder" class="a1">发货清单</a>
                    </td>
                    <td width="2" align="center" onmouseover="this.style.background='#69B310'"
                        onmouseout="this.style.background='#5BA707'"><img src="/IPS/images/other/split.jpg"></td>
                    <td width="135" align="center">&nbsp;</td>
                    <td width="135" align="center">&nbsp;</td>
                </tr>
                </tbody>
            </table>
        </td>
    </tr>
    </tbody>
</table>

