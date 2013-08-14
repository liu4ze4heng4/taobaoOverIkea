<%@ page contentType="text/html;charset=utf-8" %>
<%@ page import="java.util.*,TOI.model.User" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>【趣活】</title>
    <%--<link href="/tb/css/reset.css" rel="stylesheet" type="text/css">--%>
    <link href="/IPS/css/css.css" rel="stylesheet" type="text/css">
    <link href="/IPS/css/style.css" rel="stylesheet" type="text/css">
    <%--<script type="text/javascript" src="/tb/js/jquery-1.8.2.min.js"></script>--%>
    <%--<script type="text/javascript" src="/tb/js/common.js"></script>--%>
    <style type="text/css">
        #banner {
            position: relative;
            width: 670px;
            height: 205px;
            overflow: hidden;
        }

        #banner_list img {
            border: 0px;
        }

        #banner_bg {
            position: absolute;
            bottom: 0;
            height: 30px;
            filter: Alpha(Opacity=30);
            opacity: 0.3;
            z-index: 1000;
            cursor: pointer;
            width: 670px;
        }

        #banner_info {
            position: absolute;
            bottom: 0;
            left: 5px;
            height: 22px;
            color: #fff;
            cursor: pointer
        }

        #banner_text {
            position: absolute;
            width: 120px;
            right: 3px;
            bottom: 3px;
        }

        #banner ul {
            position: absolute;
            list-style-type: none;
            filter: Alpha(Opacity=80);
            opacity: 0.8;
            border: 1px solid #fff;
            z-index: 1002;
            margin: 0;
            padding: 0;
            bottom: 3px;
            right: 5px;
        }

        #banner ul li {
            padding: 0px 8px;
            float: left;
            display: block;
            color: #FFF;
            border: #e5eaff 1px solid;
            background: #6f4f67;
            cursor: pointer
        }

        #banner ul li.on {
            background: #900
        }

        #banner_list a {
            position: absolute;
            width: 670px;
            height: 205px;
            display: block;
            left: 0px;
        }

        <!--
        让四张图片都可以重叠在一起

        -->
    </style>
</head>
<body>
    <%@ include file="top.jsp" %>
    <table border="0" width="100%" height="100%" cellpadding="0" cellspacing="0">

        <tr height="100">
          <td width="200" height="100" >
          <% 
             if(session.getAttribute("user")==null)
         {
          %>
              		
          <% 
             }
             else
             {
             	out.println("<table border='0' bgcolor='#FFF0E1' width='80%' height='150'>");
             	out.println("<tr align='center' height='80'><td>");
             	out.println(session.getAttribute("userName")+"你好<br/>!!!");
             	out.println("<a href='CartServlet?action=logout'>[注销]</a>");
             	out.println("</td></tr></table>");
             }
            %>
          </td>
        </tr>
      </table>
  </body>
</html>