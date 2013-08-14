<%@ page contentType="text/html;charset=utf-8" %>
<%@ page import="TOI.model.TradeItem" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>发货清单-趣活宜家代购联盟</title>
    <link href="/IPS/css/css.css" rel="stylesheet" type="text/css">
    <link href="/IPS/css/style.css" rel="stylesheet" type="text/css">
    <script src="/IPS/js/date.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
    <script language="javascript" src="http://mtsoftware.v053.gokao.net/samples/LodopFuncs.js"></script>
    <object id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0>
        <embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0 pluginspage="install_lodop32.exe"></embed>
    </object>

    <style id="style" type="text/css">
        .main {
            border-width: 0px;
            cellpadding: 0;
            cellspacing: 0;
            background-color: #58a900;
            font-size: 12px;
            margin: auto;
            border-spacing: 1px;
        }

        .button {
            width: 60px;
            float: right;
        }

        .detail {
            font-size: 12px;
            color: #6a6a6a;

        }
        .table,.table td{
            margin: auto;
            font-size: 12px;
            text-align: center;
            line-height: 24px;
            border:1px solid #cccccc;
            border-collapse:collapse;
        }

    </style>
</head>
<body>
<%@ include file="top.jsp" %>
<table width="750" height="35" border="0" align="center" cellpadding="0" cellspacing="0" class="allge2"
       style="margin-top:20px;">
    <tbody>
    <tr>
        <td> &nbsp;&nbsp;<strong>取货清单</strong></td>
        <td class="button"><input type="button" value="打印运单" onClick="prn1_preview()"> </td>
    </tr>
    </tbody>
</table>
<%
    List<TradeItem> tradeItemList = (List<TradeItem>) request.getAttribute("tradeItems");
%>
<div id="table">
    <table width="750" border="0" class="table">
        <thead>
        <tr>
            <th>No</th>
            <th>ID</th>
            <th>NAME</th>
            <th>PRICE&TYPE </th>
            <th>QUANTITY</th>
            <th>STOCKINFO</th>
        </tr>
        </thead>
        <%int count=1;%>
<% for(TradeItem tradeItem:tradeItemList){%>
      <tr>
            <td><%=count++%></td>
            <td><%=tradeItem.getId()%></td>
            <td><%=tradeItem.getName()+tradeItem.getFacts()+"["+tradeItem.getType()+"]"%></td>
            <td><%=tradeItem.getPrice()%></td>
            <td><%=tradeItem.getQuantity()%></td>
            <td><%=tradeItem.getStockInfo()+tradeItem.getTotal()%></td>
        </tr>

<% } %>
    </table>
</div>
<script language="javascript" type="text/javascript">
    var LODOP; //声明为全局变量
    function prn1_preview() {
        PrintInFullPage();
        LODOP.PREVIEW();
    };

    function PrintInFullPage(){
        LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
        LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_自定义纸张");
        LODOP.SET_PRINT_PAGESIZE(1,0,0,"A4");
        var strBodyStyle="<style>"+document.getElementById("style").innerHTML+"</style>";
        var strFormHtml=strBodyStyle+"<body>"+document.getElementById("table").innerHTML+"</body>";
        LODOP.ADD_PRINT_TABLE("2%","1%","96%","90%",strFormHtml);
        LODOP.SET_PREVIEW_WINDOW(0,0,0,800,600,"");}

</script>
</body>
</html>