<%@ page contentType="text/html;charset=utf-8" %>
<%@ page import="TOI.model.SendOrder,java.util.*" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>

<html>
<head>
    <title>发货清单-趣活宜家代购联盟</title>
    <link href="/IPS/css/css.css" rel="stylesheet" type="text/css">
    <link href="/IPS/css/style.css" rel="stylesheet" type="text/css">
    <script src="/IPS/js/date.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
    <script language="javascript" src="http://mtsoftware.v053.gokao.net/samples/LodopFuncs.js"></script>
    <object id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0>
        <embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0
               pluginspage="install_lodop32.exe"></embed>
    </object>

    <style type="text/css">
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
            display:none;
            font-size: 12px;
            color: #6a6a6a;

        }

    </style>
</head>
<body>
<%@ include file="top.jsp" %>

<form id="senderOrder" name="senderOrder" action="/IPS/sendOrder" method="post">
    <input id="method" name="method" value="" type="hidden"/>
    <input id="orderNum" name="orderNum" value="" type="hidden"/>
    <input id="fahuo" name="fahuo" value="" type="hidden"/>


    <%
        List<SendOrder> orders = (List<SendOrder>) request.getAttribute("orders");
    %>

    <table width="960" height="35" border="0" align="center" cellpadding="0" cellspacing="0" class="allge2"
           style="margin-top:20px;">
        <tbody>
        <tr>
            <td> &nbsp;&nbsp;<strong>发货清单</strong></td>
            <td class="button" id="toggle"><input type="button" value="简洁/明细"></td>
            <td class="button"><input type="button" onclick="createFahuo()" value="制作取货单"/></td>
            <td class="button"><input type="button" value="打印运单" onClick="PreviewZjs()"></td>
            <td class="button"><input type="button" onclick="updateExpressCode()" value="更新发货"/></td>
        </tr>
        </tbody>
    </table>
    <table width="960" border="0" cellpadding="0" cellspacing="0" style="font-size:12px;" align="center" class="allge2">
        <tbody>
        <tr>
            <td width="80" align="center">发货状态:</td>
            <td width="80">
                <% int status = StringUtils.isBlank(request.getParameter("s_status")) ? 0 : Integer.valueOf(request.getParameter("s_status"));%>
                <select name="s_status" style="width:80">
                    <option value="0"    <% if (status == 0) { %> selected="selected"  <% }%>  >全部</option>
                    <option value="1"    <% if (status == 1) { %> selected="selected"  <% }%>  >未发货</option>
                    <option value="2"    <% if (status == 2) { %> selected="selected"  <% }%>  >已发货</option>
                </select>
            </td>
            <td width="80" align="center">物流公司:</td>
            <td width="80">
                <% int s_express = StringUtils.isBlank(request.getParameter("s_express")) ? 0 : Integer.valueOf(request.getParameter("s_express"));%>
                <select name="s_express" style="width:80">
                    <option value="0"    <% if (s_express == 0) { %> selected="selected"  <% }%>  >全部
                    </option>
                    <option value="1"    <% if (s_express == 1) { %> selected="selected"  <% }%> >宅急送
                    </option>
                    <option value="2"    <% if (s_express == 2) { %> selected="selected"  <% }%> >优速
                    </option>
                    <option value="3"    <% if (s_express == 3) { %> selected="selected"  <% }%> >德邦
                    </option>
                    <option value="4"    <% if (s_express == 4) { %> selected="selected"  <% }%> >中铁
                    </option>
                </select>
            </td>
            <td width="80" align="center">时间:</td>
            <td width="80"><input name="s_date" type="text" id="control_date" size="12"
                                  maxlength="90" onclick="new Calendar().show(this);"
                                  readonly="readonly"
                                  value="<%=StringUtils.isBlank((String)request.getAttribute("s_date"))?"":(String)request.getAttribute("s_date")%>"/>
            </td>
            <td width="80"><input name="s_endDate" type="text" id="control_endDate" size="12"
                                  maxlength="90" onclick="new Calendar().show(this);"
                                  readonly="readonly"
                                  value="<%=StringUtils.isBlank((String)request.getAttribute("s_endDate"))?"":(String)request.getAttribute("s_endDate")%>"/>
            </td>
            <td width="80" align="center">关键词:</td>
            <td width="80"><input name="s_keyword"
                                  value="<%=StringUtils.isBlank(request.getParameter("s_keyword"))?"":request.getParameter("s_keyword")%>"/>
            </td>


            <td class="button"><input type="button" onclick="seachSenderOrder()" value="搜索"/></td>
        </tr>
        </tbody>
    </table>

    <table width="960" class="main">
        <tbody>

        <tr>
            <th width="25"><input type="checkbox" onclick='chkall("table1",this)'></th>
            <th width="90">内部编号</th>
            <th width="175">成交时间</th>
            <th width="100">淘宝ID</th>
            <th width="100">姓名</th>
            <th width="100">电话</th>
            <th width="100">物流公司</th>
            <th width="180">运单号</th>
            <th width="80">状态</th>
        </tr>
        <%
            if (orders != null) {
                for (int i = 0; i < orders.size(); i++) {
                    SendOrder order = orders.get(i);
                    if (i % 2 == 0) {
                        out.println("<tr align='center' bgcolor='#ffffff'>");
                    } else {
                        out.println("<tr align='center' bgcolor='#fbfff6'>");
                    }
        %>
        <td><input type="checkbox" class="check" id="CK<%=i%>"></td>

        <td><input type="text" id="ID<%=i%>" name="ID<%=i%>" style="border: 0px;width: 90;" value="<%= order.getId() %>"
                   border="0"
                   readonly=true></td>
        <td><input type="text" id="PT<%=i%>" style="border: 0px;width: 175;" value="<%= order.getPayTime() %>"
                   readonly=true></td>
        <td><input type="text" id="BN<%=i%>" style="border: 0px;width: 100;" value="<%= order.getBuyerNIck()%>"
                   readonly=true></td>
        <td><input type="text" id="RN<%=i%>" style="border: 0px;width: 100;" value="<%= order.getReceiverName() %>"
                   readonly=true></td>
        <td><input type="text" id="RM<%=i%>" style="border: 0px;width: 100;" value="<%= order.getReceiverMobile() %>"
                   readonly=true></td>
        <td><input type="text" id="EI<%=i%>" style="border: 0px;width: 100;" value="
    <% if (("1").equals(order.getExpressId()))
        out.println("宅急送");
    else if (("2").equals(order.getExpressId()))
        out.println("优速");
    else if (("3").equals(order.getExpressId()))
        out.println("中铁");
    else if (("4").equals(order.getExpressId()))
        out.println("德邦");
    else
        out.println("无");%>" readonly=true></td>
        <td><input type="text" id="EN<%=i%>" name="EN<%=i%>" style="border: 0px;width: 180;"
                   value="<%= order.getExpressNum() %>"></td>
        <td><%= order.getStatusDep() %>
        </td>
        </tr>

        <tr class='detail' bgcolor='#f8f8f8'>
            <td></td>
            <td></td>
            <td colspan="30">
                地址:<input type="text" id="RS<%=i%>" style="border: 0px;width: 100;"
                          value="<%= order.getRecerverState()%>" readonly=true> <input type="text" id="RC<%=i%>"
                                                                                       style="border: 0px;width: 100;"
                                                                                       value="<%= order.getReceiverCity()%>"
                                                                                       readonly=true> <input type="text"
                                                                                                             id="RA<%=i%>"
                                                                                                             style="border: 0px;width: 600;"
                                                                                                             value="<%=order.getReceiverAddress()%>"
                                                                                                             readonly=true>
                <br/>内件:<input type="text" id="SM<%=i%>" style="border: 0px;width: 800;" value="<%=order.getSellerMemo()
            .replace("[", "备注:").replace("]|", "") %>" readonly=true>
            </td>
        </tr>
        <%
                }
            }
        %>


        </tbody>
    </table>
</form>

<script type="text/javascript">
    var LODOP; //声明为全局变量
    $(document).ready(function () {
        $("#toggle").click(function () {
            $(".detail").fadeToggle();
        });
    });

    function chkall(input1, input2) {
        var objForm = document.getElementsByClassName("check")

        var objLen = objForm.length;

        for (var iCount = 0; iCount < objLen; iCount++) {
            if (input2.checked == true) {
                if (objForm[iCount].type == "checkbox") {
                    objForm[iCount].checked = true;
                }
            }
            else {
                if (objForm[iCount].type == "checkbox") {
                    objForm[iCount].checked = false;
                }
            }
        }
    }
    <%--function MyPreview() {--%>
    <%--LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));--%>

    <%--//            AddTitle();--%>
    <%--var iCurLine=80;//标题行之后的数据从位置80px开始打印--%>
    <%--for (j = 0; j< <%=orders.size()%>; j++) {--%>
    <%--if (document.getElementById("CK"+j).checked) {--%>
    <%--LODOP.ADD_PRINT_TEXT(iCurLine,15,100,20,document.getElementById("ID"+j).value);--%>
    <%--LODOP.ADD_PRINT_TEXT(iCurLine,149,100,20,document.getElementById("PT"+j).value);--%>
    <%--LODOP.ADD_PRINT_TEXT(iCurLine,289,100,20,document.getElementById("BN"+j).value);--%>
    <%--LODOP.ADD_PRINT_TEXT(iCurLine,409,100,20,document.getElementById("EI"+j).value);--%>
    <%--iCurLine=iCurLine+25;//每行占25px--%>
    <%--}--%>
    <%--}--%>
    <%--LODOP.ADD_PRINT_LINE(iCurLine,14,iCurLine,510,0,1);--%>
    <%--LODOP.ADD_PRINT_TEXT(iCurLine+5,20,300,20,"打印时间："+(new Date()).toLocaleDateString()+" "+(new Date()).toLocaleTimeString());--%>
    <%--LODOP.SET_PRINT_PAGESIZE(3,1385,45,"");//这里3表示纵向打印且纸高“按内容的高度”；1385表示纸宽138.5mm；45表示页底空白4.5mm--%>
    <%--LODOP.PREVIEW();--%>
    <%--};--%>

    function PreviewZjs() {
        LODOP = getLodop(document.getElementById('LODOP_OB'), document.getElementById('LODOP_EM'));
        LODOP.PRINT_INIT("打印控件Lodop功能演示_多页预览");
        LODOP.SET_PRINT_PAGESIZE(1, 2300, 1420, "");
        for (j = 0; j < <%=orders.size()%>; j++) {
            if (document.getElementById("CK" + j).checked) {
                LODOP.NewPage();
//                alert("小赵"+"18001344361"+document.getElementById("RN"+j).value+document.getElementById("RM"+j).value+""+document.getElementById("RA"+j).value+document.getElementById("BN"+j).value+document.getElementById("RS"+j).value+document.getElementById("RC"+j).value+document.getElementById("SM"+j).value);
                LoadZjsTemple("小赵", "18001344361", document.getElementById("RN" + j).value, document.getElementById("RM" + j).value, "", document.getElementById("RA" + j).value, document.getElementById("BN" + j).value, document.getElementById("RS" + j).value, document.getElementById("RC" + j).value, document.getElementById("SM" + j).value);
            }
        }
        LODOP.PREVIEW();
    }
    ;

    function LoadZjsTemple(sender, senderMobile, receiverName, receiverMobile, receiverPhone, receiverAdress, buyerNick, recerverState, receiverCity, sellerMemo) {
        LODOP.ADD_PRINT_TEXT("38.1mm", "32.8mm", "20.6mm", "5.8mm", sender);
        LODOP.ADD_PRINT_TEXT("59.3mm", "66.7mm", "20.6mm", "5.8mm", senderMobile);
        LODOP.ADD_PRINT_TEXT("73mm", "32.8mm", "20.6mm", "6.6mm", receiverName);
        LODOP.ADD_PRINT_TEXT("95.5mm", "64.8mm", "20.6mm", "5.8mm", receiverMobile);
        LODOP.ADD_PRINT_TEXT("95.8mm", "25.7mm", "20.6mm", "5.8mm", receiverPhone);
        LODOP.ADD_PRINT_TEXT("80.2mm", "25.1mm", "79.6mm", "14.3mm", receiverAdress);
        LODOP.ADD_PRINT_TEXT("3.4mm", "9.5mm", "20.6mm", "5.8mm", buyerNick);
        LODOP.ADD_PRINT_TEXT("72.5mm", "62.4mm", "19mm", "6.6mm", recerverState);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
        LODOP.ADD_PRINT_TEXT("72.2mm", "82mm", "19mm", "6.6mm", receiverCity);
        LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
        LODOP.ADD_PRINT_TEXT("70.4mm", "126.7mm", "5.3mm", "5.3mm", "√");
        LODOP.SET_PRINT_STYLE(0, "FontSize", 8);
        LODOP.ADD_PRINT_TEXT("114.6mm", "17.7mm", "82.8mm", "7.1mm", sellerMemo);
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
        LODOP.ADD_PRINT_TEXT("37.8mm", "128.6mm", "19.8mm", "5.8mm", "6566697");
        LODOP.ADD_PRINT_TEXT("130.4mm", "148.7mm", "45.8mm", "5.6mm", "亲，请仔细验货，本人签收！");
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 8);
        LODOP.ADD_PRINT_TEXT("102.4mm", "129.9mm", "17.7mm", "5.8mm", "小赵");
        LODOP.SET_PRINT_STYLEA(0, "FontName", "楷体");
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 11);
        LODOP.ADD_PRINT_TEXT("51.6mm", "25.7mm", "79.1mm", "5.8mm", "Quhoo Procurement Service of IKEA");
        LODOP.ADD_PRINT_TEXT("38.4mm", "84.9mm", "14.8mm", "5.8mm", "北京 望京");
        LODOP.ADD_PRINT_TEXT("109.3mm", "129.4mm", "17.7mm", "5.8mm", (new Date()).toLocaleDateString());
        LODOP.SET_PRINT_STYLEA(0, "FontSize", 7);
    }
    function updateExpressCode() {
        var form1 = window.document.getElementById("senderOrder");//获取form1对象
        window.document.getElementById("method").value = "updateOrderInfo";
        window.document.getElementById("orderNum").value = "<%=orders.size()%>";
        form1.submit();
    }
    ;

    function seachSenderOrder() {
        var form1 = window.document.getElementById("senderOrder");//获取form1对象
        window.document.getElementById("method").value = "searchOrder";
        form1.submit();
    }
    ;

    //    function createFahuo(){
    //        var form1 = window.document.getElementById("senderOrder");//获取form1对象
    //        window.document.getElementById("method").value="createFahuo";
    //        form1.submit();
    //    };

    function createFahuo() {
        var memoList = new Array()
        var form1 = window.document.getElementById("senderOrder");//获取form1对象

        for (var k = 0; k < <%=orders.size()%>; k++) {
            if (document.getElementById("CK" + k).checked) {
//                LoadZjsTemple("小赵","18001344361",document.getElementById("RN"+j).value,document.getElementById("RM"+j).value,"",document.getElementById("RA"+j).value,document.getElementById("BN"+j).value,document.getElementById("RS"+j).value,document.getElementById("RC"+j).value,);
                memoList.push(document.getElementById("ID" + k).value);
            }
        }
        window.document.getElementById("fahuo").value = memoList;
        window.document.getElementById("method").value = "createFahuo";

        form1.submit();


    }
</script>
</body>
</html>