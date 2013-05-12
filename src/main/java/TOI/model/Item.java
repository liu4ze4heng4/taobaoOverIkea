/**
 * Created with IntelliJ IDEA.
 * User: W.k
 * Date: 13-4-29
 * Time: 上午1:47
 * To change this template use File | Settings | File Templates.
 */
package TOI.model;

import TOI.util.HtmlUtil;
import TOI.util.ItemUtils;
import TOI.util.XmlCatcher;

import java.io.IOException;
import java.util.ArrayList;


public class Item {
    public String pid;

    public String getPid() {
        return pid;
    }

    public String getName() {
        return name;
    }

    public String getFacts() {
        return facts;
    }

    public String getPrice() {
        return price;
    }

    public double getPricePrevious() {
        return pricePrevious;
    }

    public String getAssembledSize() {
        return assembledSize;
    }

    public String getDesignerThoughts() {
        return designerThoughts;
    }

    public String getDesigner() {
        return designer;
    }

    public String getEnvironment() {
        return environment;
    }

    public String getGoodToKnow() {
        return goodToKnow;
    }

    public String getCareInst() {
        return careInst;
    }

    public String getCustMaterials() {
        return custMaterials;
    }

    public String getCustBenefit() {
        return custBenefit;
    }

    public float getWeight() {
        return weight;
    }

    public float getSize() {
        return size;
    }

    public ArrayList<String> getPicUrls() {
        return picUrls;
    }

    public String getType() {
        return type;
    }

    public String getColorCode() {
        return colorCode;
    }

    public String name;
    public String facts;
    public String price;
    public double pricePrevious;
    public String assembledSize;
    public String designerThoughts;
    public String designer;
    public String environment;
    public String goodToKnow;
    public String careInst;
    public String custMaterials;
    public String custBenefit;
    public float weight;
    public float size;
    public ArrayList<String> picUrls = new ArrayList<String>();
    public String type;
    public String colorCode;

    /**
     * 构造函数
     */
    public Item(String id) {
        pid = id;
    }

    public void initItemFromIKEA(String id) {
        try {
            String buf = HtmlUtil.getHtmlContent("http://www.ikea.com/cn/zh/catalog/products/" + id + "?type=xml&dataset=normal,prices,allimages,parentCategories");
            int count=0;
            while(buf.contains("<URL>")!=true&&count<10)
                {  buf = HtmlUtil.getHtmlContent("http://www.ikea.com/cn/zh/catalog/products/" + id +
                        "?type=xml&dataset=normal,prices,allimages,parentCategories");
                count++;
                }
            String info = XmlCatcher.getItem(buf);
            String[] infos = info.split("!!");

            name = infos[0];
            facts = infos[1];
            price = XmlCatcher.getPrice(buf);
            assembledSize = infos[8];
            designer = infos[4];
            environment = infos[5];
            goodToKnow = infos[6];
            careInst = infos[2];
            custBenefit = infos[3];
            custMaterials = infos[7];
            picUrls = XmlCatcher.getPicUrl(buf);
            ItemUtils.savePicToLocal(this);

            System.out.println("initItemFromIKEA "+buf+" "+name+" "+price);
        } catch (IOException ie) {
         System.err.println("initItemFromIKEA "+name+price);
        }

    }

    /**
     * 设置weight
     */
    public void setWeight() {

    }
    public void setTypeAndColorCode(String type){
        this.type=type;
        setColorCode();
    }
    /**
     * 设置colorCode
     */

    public void setColorCode(){
        if (type==null)
            colorCode = "#000000";
        else if (type.contains("黄"))
            colorCode = "#e5cd00";
        else if (type.contains("红"))
            colorCode = "#cc0000";
        else if (type.contains("绿"))
            colorCode = "#22cc00";
        else if (type.contains("蓝"))
            colorCode = "#1759a8";
        else if (type.contains("橙"))
            colorCode = "#f27405";
        else
            colorCode = "#000000";

    }


    public Item() {
    }


    public static void main(String[] args) {
        Item i = new Item("S09882144");

    }


}
