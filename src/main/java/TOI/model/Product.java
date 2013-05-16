package TOI.model;

import TOI.Constant.Constant;
import TOI.util.ItemUtils;
import TOI.util.ProductUtils;
import TOI.util.VelocityUtil;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.tools.generic.MathTool;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: W.k
 * Date: 13-4-30
 * Time: 上午12:55
 * To change this template use File | Settings | File Templates.
 */
public class Product {
    public String pid;
    public String tid;
    public String price;
    public String title;
    public List<Item> itemsList = new ArrayList<Item>();
    public Map<String, String> itemTypes= null;
    public String itemIds;
    public String categoryLocal;
    public String subCategoryLocal;
    public String saleInfo;
    public String description;
    public List<String> mainPicsLocal = new ArrayList<String>();

    /**
     * 构造函数
     */
    public Product(String id) {
        pid = id;
    }

    /**
     * 设置mainPics
     */
    public List<String> setMainPics() {
        ArrayList<String> mainPics = new ArrayList<String>();
        for (Item item : itemsList)
            mainPics.add(item.picUrls.get(0));
        return mainPics;
    }

    /**
     * 设置mainPicLocal
     */
    public List<String> setMainPicLocal() {
        List<String> mainPics = setMainPics();
        try {
            for (int i = 0; i < mainPics.size(); i++) {
                URL url = new URL("mainPics.get(i)");
                URLConnection urlCon = url.openConnection();
                InputStream is = urlCon.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                FileOutputStream fos = new FileOutputStream(Constant.picPath + "\\\\" + pid + "_" + i + ".jpg");
                BufferedOutputStream bos = new BufferedOutputStream(fos);

                int read;
                while ((read = bis.read()) != -1) {
                    bos.write(read);
                }
                bos.close();// 不关闭，输出流不刷新，有可能得到无效图片
                mainPicsLocal.add(Constant.picPath + "\\\\" + pid + "_" + i + ".jpg");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mainPicsLocal;
    }

    /**
     * 设置title，price
     */
    public void setTitleAndPrice() {
        Item topItem = itemsList.get(0);
        title = topItem.name + topItem.facts;
        System.out.println(title);
        price = topItem.price;
    }
    /**
     * 设置itemsId
     */
    public void setItemsId() {
        if(itemTypes.size()!=0)
        itemIds = itemTypes.keySet().toString();
        else
            itemIds="["+pid+"]";
    }

    /**
     * 生成描述
     */
    public String setDescription() {
        VelocityContext context = new VelocityContext();

        context.put("itemList", itemsList);

        // context.put("product", p);
        context.put("math", new MathTool());
        description = VelocityUtil.filterVM("productDetail.vm", context);

        return description;
    }

    /**
     * 从IKEA初始化itemList
     */
    public void initItemListFromIKEA() {
        if (itemTypes.isEmpty()) {
            Item item = new Item(pid);
            item.initItemFromIKEA(pid);
            itemsList.add(item);
        } else {
            for (String id : itemTypes.keySet()) {
                Item item = new Item(id);
                item.initItemFromIKEA(id);
                item.setTypeAndColorCode(itemTypes.get(id));
                itemsList.add(item);
            }
        }
        System.out.println(itemsList.get(0).facts);
        Item temp;
        for(int i=0;i <itemsList.size();i++) {
            for (int j = i+1; j < itemsList.size(); j++) {
                if (compare(itemsList.get(i), itemsList.get(j)) == -1)
                {
                    temp = itemsList.get(i);
                    itemsList.set(i,itemsList.get(j));
                    itemsList.set(j,temp);
                }
            }
        }

    }

    /**
     * 从数据库初始化itemList
     */
    public void initItemListFromSQL() {
        List<String> items= ProductUtils.setItemsFromSQL(pid);
        for(String id:items)
        itemsList.add(ItemUtils.setItemFromSQL(id));
        Item temp;
        for(int i=0;i <itemsList.size();i++) {
            for (int j = i+1; j < itemsList.size(); j++) {
                if (compare(itemsList.get(i), itemsList.get(j)) == -1)
                {
                    temp = itemsList.get(i);
                    itemsList.set(i,itemsList.get(j));
                    itemsList.set(j,temp);
                }
            }
        }
    }

    /**
     * 设置分类
     */
    public void setCategory(List<String> cates) {
        if(cates!=null)
        { categoryLocal = cates.get(0);
        subCategoryLocal = cates.get(1);}
    }


    /**
     * 生成宝贝描述
     *
     * @return
     */
    public void creatDescribtion() {

    }


    public int compare(Item arg0, Item arg1) {
        Item item1 = arg0;
        Item item2 = arg1;

        double flag1 = Double.parseDouble(item1.price) - Double.parseDouble(item2.price);
        if (flag1 < 0.0)
            return -1;
        else
            return 1;
    }



}
