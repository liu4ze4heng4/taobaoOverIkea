package TOI.util;

import TOI.Constant.Constant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: W.k
 * Date: 13-5-2
 * Time: 下午10:36
 * To change this template use File | Settings | File Templates.
 */
public class IkeaStockUtil {

    public static String setBufA(String id) {
        id = setId(id);
        String bufA = new String();
        try {

            bufA = HtmlUtil.getHtmlContent("http://m.ikea" +
                    ".com/cn/zh/store/availability/?storeCode=802&itemType=art&itemNo=" + id + "&change=true&_=1");
            if (bufA == null)
                bufA = HtmlUtil.getHtmlContent("http://m.ikea" +
                        ".com/cn/zh/store/availability/?storeCode=802&itemType=spr&itemNo=" + id + "&change=true&_=1");

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return bufA;
    }

    public static String setBufB(String id) {
        id = setId(id);
        String bufB = new String();

        try {
            bufB = HtmlUtil.getHtmlContent("http://www.ikea.com/cn/zh/catalog/packagepopup/" + id);
            if (bufB == null)
                bufB = HtmlUtil.getHtmlContent("http://www.ikea.com/cn/zh/catalog/packagepopup/S" + id);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return bufB;
    }

    private static String setId(String id) {
        return id.replace(".", "").replace("S", "");
    }

    /**
     * 获取IKEA库存信息
     */
    public static String StockInfoCatcher(String id) {

        ArrayList<Double> weight = new ArrayList<Double>();

        ArrayList<Double> sizer = new ArrayList<Double>();
//
        id = id.replace(".", "").replace("S", "");
        String buf = setBufA(id);
        String quantity = new String();
        int status = 0;  //无库存
        if (buf.contains("北京商场当前有库存")) {
            int beginIx = buf.indexOf("北京商场当前有库存");
            int endIx = buf.indexOf("</b>", beginIx);
            quantity = buf.substring(beginIx + "北京商场当前有库存：".length(), endIx);
            quantity = quantity.replace("<b>", "").replace(" ", "");

            if (buf.contains("联系工作人员")) {
                status = 2;  //外仓
            } else {
                status = 1; //可以发货
            }

        } else if (buf.contains("北京商场当前无库存")) {
            quantity = "0";
            status = 0;     //无库存
        } else if (buf.contains("北京商场不出售该产品")) {
            quantity = "0";
            status = -1;     //不再售
        } else

            quantity = "0";
        status = -2;     //出错
        return quantity + Constant.split + status;
    }

    public static double WeightCatcher(String id) {
        double wholeweight = 0;

        id = setId(id);
        String buf2 = setBufB(id);
        List<Double> weight = new ArrayList<Double>();
        String regexStr = "<div class=\"rowContainerPackage\">[\\s\\S]*?<div class=\"clear\"></div>";
        Pattern productCell = Pattern.compile(regexStr);
        Matcher m = productCell.matcher(buf2);
        while (m.find()) {

            String content = m.group();
            int beginIx = content.indexOf("<div class=\"colPack\">");
            int endIx = content.indexOf("</div>", beginIx);
            int count = new Integer(content.substring(beginIx + "<div class=\"colPack\">".length(), endIx).replace("	", "").replace("千克", "").replace("&nbsp;", ""));


            beginIx = content.indexOf("<div class=\"colWeight\">");
            endIx = content.indexOf("</div>", beginIx);
            weight.add(count * new Double(content.substring(beginIx + "<div class=\"colWeight\">".length(),
                    endIx).replace("	", "").replace("千克", "").replace("&nbsp;", "9999")));


        }
        if (!weight.contains(9999)) {
            for (int i = 0; i < weight.size(); i++)
                wholeweight = wholeweight + weight.get(i);
            return wholeweight;
        } else return -1;
    }



    public static double SizeCatcher(String id) {
        id = setId(id);
        String buf2 = setBufB(id);
        double wholesize = 0;
        List<Double> sizer = new ArrayList<Double>();

        String regexStr = "<div class=\"rowContainerPackage\">[\\s\\S]*?<div class=\"clear\"></div>";
        Pattern productCell = Pattern.compile(regexStr);
        Matcher m = productCell.matcher(buf2);
        while (m.find()) {

            String content = m.group();
        int beginIx = content.indexOf("<div class=\"colWidth\">");
        int endIx = content.indexOf("</div>", beginIx);
            int count = new Integer(content.substring(beginIx + "<div class=\"colPack\">".length(), endIx).replace("	", "").replace("千克", "").replace("&nbsp;", ""));
            ArrayList<Double> size = new ArrayList<Double>();
        size.add(new Double(content.substring(beginIx + "<div class=\"colWidth\">".length(), endIx).replace("	", "").replace("厘米", "").replace("&nbsp;", "0")));

        beginIx = content.indexOf("<div class=\"colHeight\">");
        endIx = content.indexOf("</div>", beginIx);
        size.add(new Double(content.substring(beginIx + "<div class=\"colHeight\">".length(), endIx).replace("	", "").replace("厘米", "").replace("&nbsp;", "0")));
        beginIx = content.indexOf("<div class=\"colLength\">");
        endIx = content.indexOf("</div>", beginIx);
        size.add(new Double(content.substring(beginIx + "<div class=\"colLength\">".length(), endIx).replace("	", "").replace("厘米", "").replace("&nbsp;", "0")));
        if (!size.contains((double) 0))
            sizer.add(count * size.get(0) * size.get(1) * size.get(2) / 1000000);
        else
            sizer.add((double) 9999);
    }

        if (!sizer.contains(9999)) {
            for (int i = 0; i < sizer.size(); i++)
                wholesize = wholesize + sizer.get(i);
            return wholesize;
        } else return -1;
}

}
