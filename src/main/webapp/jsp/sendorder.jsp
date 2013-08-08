<%@ page contentType="text/html;charset=utf-8" %>
<%@ page import="TOI.model.SendOrder,java.util.*" %>
<html>
<head>
    <title>发货清单-趣活宜家代购联盟</title>
    <link href="http://localhost:8080/css/css.css" rel="stylesheet" type="text/css">
    <link href="http://localhost:8080/css/style.css" rel="stylesheet" type="text/css">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $(".button").click(function () {
                $(".detail").fadeToggle();
            });
        });
    </script>
    <style type="text/css">
        .main {
            border: 1px;
            cellpadding: 0;
            cellspacing: 0;
            background-color: #FFFFFF;
            font-size: 12px;
            margin: auto;
            width: 960px;
        }

        .button {
            width: 60px;
            float: right;
        }

    </style>
</head>
<body>
<%@ include file="top.jsp" %>
<table width="960" height="35" border="0" align="center" cellpadding="0" cellspacing="0" class="allge2"
       style="margin-top:20px;">
    <tbody>
    <tr>
        <td> &nbsp;&nbsp;<strong>发货清单</strong></td>
        <td class="button"><input type="button" value="简洁/明细"></td>
    </tr>
    </tbody>
</table>

<table class="main">
    <tbody>
    <tr>
        <th>内部编号</th>
        <th>成交时间</th>
        <th>姓名</th>
        <th>电话</th>
        <th>地址</th>
        <th>订单详细信息</th>
        <th>操作</th>
    </tr>
    <%
        List<SendOrder> orders = (List<SendOrder>) request.getAttribute("orders");
        if (orders != null) {
            for (int i = 0; i < orders.size(); i++) {
                SendOrder order = orders.get(i);
                if (i % 2 == 0) {
                    out.println("<tr align='center'>");
                } else {
                    out.println("<tr align='center' bgcolor='#F5F9FE'>");
                }
    %>
    <td><input type="checkbox" name="" value=""/></td>
    <td><%= order.getId() %>
    </td>
    <td><%= order.getTid() %>
    </td>
    <td><%= order.getReceiverName() %>
    </td>
    <td><%= order.getReceiverMobile() %>
    </td>
    <td><%= order.getReceiverAddress() %>
    </td>
    <td><%= order.getSellerMemo() %>
    </td>
    <td><a href="AdminServlet?action=ordermanage&id=<%= order.getId()%>">发货</a></td>
    </tr>
    </tr>
    <tr class="detail">
        <td></td>
        <td colspan="8">望京 夏都家园 3号楼 A单元1705室</td>
    </tr>
    <tr class="detail">
        <td></td>
        <td colspan="8">白折叠椅(20222444)*2|[]|</td>
    </tr>
    <%
            }
        }
    %>
    </tbody>
</table>
</body>
</html