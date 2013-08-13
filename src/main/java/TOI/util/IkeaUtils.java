package TOI.util;


import TOI.dao.DaoFactory;
import TOI.model.Item;
import TOI.model.Product;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: W.k
 * Date: 13-4-30
 * Time: 上午1:00
 * To change this template use File | Settings | File Templates.
 */
public class IkeaUtils {
    /**
     * 设置MOBILE_BUF
     */
    public static String setBuf(String pid) {
        String bufS = new String();
        try {
            if (pid.contains("(?i)S"))
                bufS = HtmlUtil.getHtmlContent("http://m.ikea.com/cn/zh/catalog/products/spr/" + pid.replace("(?i)S", "") + "/");
            else
                bufS = HtmlUtil.getHtmlContent("http://m.ikea.com/cn/zh/catalog/products/art/" + pid + "/");

        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.err.println("设置ITEM失败");
//            e.printStackTrace();
        }
        return bufS;
    }

    /**
     * 设置分类(mobile)
     */
    public static List<String> getCategory(String bufS) {
        if (bufS.contains("breadcrumbs")) {
            int beginIx = bufS.indexOf("<div class=\"ikea-breadcrumbs\"");
            int endIx = bufS.indexOf("</div>", beginIx);
            String result = bufS.substring(beginIx, endIx);
            String regexStr = "<a href=[\\s\\S]*?</a>";
            Pattern productCell = Pattern.compile(regexStr);
            Matcher m = productCell.matcher(result);
            List<String> cates = new ArrayList<String>();
            while (m.find()) {
                String date = m.group();
                int beginIx1 = date.indexOf(">");
                int endIx1 = date.indexOf("</a>", beginIx1);
                String cate = date.substring(beginIx1 + ">".length(), endIx1);
                cates.add(cate);
            }

            return cates;
        } else
            return null;
    }

    /**
     * 设置ITEMTYPES
     */
    public static Map<String, String> getItemTypes(String bufS) {
        Map<String, String> items = new HashMap<String, String>();
        if (bufS.contains("itemLookup")) {
            int beginIx = bufS.indexOf("var itemLookup");
            int endIx = bufS.indexOf("var firstAttrLookup", beginIx);
            String result = bufS.substring(beginIx + "var itemLookup".length(), endIx);
            String regexStr = "A1:[\\s\\S]*?}";
            Pattern productCell = Pattern.compile(regexStr);
            Matcher m = productCell.matcher(result);
            while (m.find()) {
                String date = m.group();
                int beginIx1 = date.indexOf("A1:");
                int endIx1 = date.indexOf("|", beginIx1);
                String type = date.substring(beginIx1 + "A1:".length(), endIx1);
                beginIx1 = date.indexOf("'t':'");
                endIx1 = date.indexOf("',", beginIx1);
                String as = date.substring(beginIx1 + "'t':'".length(), endIx1);
                beginIx1 = date.indexOf("i':'");
                endIx1 = date.indexOf("'}", beginIx1);
                String id = date.substring(beginIx1 + "i':'".length(), endIx1);
                if (as.equals("spr"))
                    id = "S" + id;
                items.put(id, type);
            }

            return items;
        } else
            return items;
    }

    /**
     * 从IKEA获得一个完整的产品
     */
    public static void grabProductFromIKEA(String id) {
        System.out.println("=============================\nNOW:" + id);
        Product p = new Product();
        List<Item> items = new ArrayList<Item>();
        p.setItemObjs(items);
        String buf = IkeaUtils.setBuf(id);
        List<String> cates = IkeaUtils.getCategory(buf);
        Map<String, String> map = IkeaUtils.getItemTypes(buf);
        Set<String> idSet = map.keySet();
        if (!idSet.isEmpty()){
        Iterator<String> iterator = idSet.iterator();

        while (iterator.hasNext()) {
            Item item = new Item();
            item.setPid(iterator.next());
            item.setType(map.get(item.pid));
            item.setCategory(cates.get(0));
            item.setSubCategory(cates.get(1));
            initItemFromIKEA(item);
            item.setWeight(IkeaStockUtil.WeightCatcher(item.getPid()));
            items.add(item);
        }      }
        else  {
            Item item = new Item();
            item.setPid(id);
//            item.setType();
            item.setCategory(cates.get(0));
            item.setSubCategory(cates.get(1));
            initItemFromIKEA(item);
            item.setWeight(IkeaStockUtil.WeightCatcher(item.getPid()));
            items.add(item);
        }



        if( DaoFactory.getItemDao().check(items.get(0)) != 0)
        {  p.setPid(DaoFactory.getItemDao().getItemByIid(items.get(0).getPid()).getProductId());
            DaoFactory.getProductDao().updateProduct(p);   }
        else
                p.setPid(DaoFactory.getProductDao().insert(p));

            for (int i = 0; i < items.size(); i++) {
                Item item = items.get(i);
               if( DaoFactory.getItemDao().check(items.get(i)) == 0)
               { item.setProductId(p.getPid());
                DaoFactory.getItemDao().insertItem(item); }
               else {
                   DaoFactory.getItemDao().updateItem(item);
                   System.out.println("update item");}
            }



    }

    public static void initItemFromIKEA(Item item) {
        String id = item.getPid();
        try {
            String buf = HtmlUtil.getHtmlContent("http://www.ikea.com/cn/zh/catalog/products/" + id + "?type=xml&dataset=normal,prices,allimages,parentCategories");
            int count = 0;
            while ((buf.contains("<URL>") != true) && (count < 10)) {
                buf = HtmlUtil.getHtmlContent("http://www.ikea.com/cn/zh/catalog/products/" + id + "?type=xml&dataset=normal,prices,allimages,parentCategories");

                count++;
            }
            String info = XmlCatcher.getItem(buf);
            String[] infos = info.split("!!");

            item.name = infos[0];
            item.facts = infos[1];
            item.setPrice( XmlCatcher.getPrice(buf));
            item.assembledSize = infos[8];
            item.designer = infos[4];
            item.environment = infos[5];
            item.goodToKnow = infos[6];
            item.careInst = infos[2];
            item.custBenefit = infos[3];
            item.custMaterials = infos[7];
            item.picUrls = XmlCatcher.getPicUrl(buf);
            ItemUtils.savePicToLocal(item);

            System.out.println("initItemFromIKEA " + buf + " " + item.name + " " + item.price);
        } catch (IOException ie) {
            System.err.println("initItemFromIKEA " + item.name + item.price);
        }
    }

    public static void sortItemsByPrice(List<Item> items){

        for (int i = 0; i < items.size(); i++) {
            for (int j = i + 1; j < items.size(); j++) {
                if (compare(items.get(i), items.get(j)) == 1) {
                    Item temp = items.get(i);
                    items.set(i, items.get(j));
                    items.set(j, temp);
                }
            }
        }
    }
    public static int compare(Item arg0, Item arg1) {
        Item item1 = arg0;
        Item item2 = arg1;

        double flag1 = Double.parseDouble(item1.price) - Double.parseDouble(item2.price);
        if (flag1 < 0.0D) {
            return -1;
        }
        return 1;
    }

    public static void main(String[] args) {
        IkeaUtils.grabProductFromIKEA("30160964");
    }
}
