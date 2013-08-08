package TOI.model;

import TOI.util.HtmlUtil;
import TOI.util.ItemUtils;
import TOI.util.XmlCatcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Item
{
    public String pid;
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
    public ArrayList<String> picUrls = new ArrayList();
    public ArrayList<String> picUrlsAtTaobao = new ArrayList();
    public String type;
    public String colorCode;

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFacts(String facts) {
        this.facts = facts;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setPricePrevious(double pricePrevious) {
        this.pricePrevious = pricePrevious;
    }

    public void setAssembledSize(String assembledSize) {
        this.assembledSize = assembledSize;
    }

    public void setDesignerThoughts(String designerThoughts) {
        this.designerThoughts = designerThoughts;
    }

    public void setDesigner(String designer) {
        this.designer = designer;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public void setGoodToKnow(String goodToKnow) {
        this.goodToKnow = goodToKnow;
    }

    public void setCareInst(String careInst) {
        this.careInst = careInst;
    }

    public void setCustMaterials(String custMaterials) {
        this.custMaterials = custMaterials;
    }

    public void setCustBenefit(String custBenefit) {
        this.custBenefit = custBenefit;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public void setPicUrls(String picUrls) {
        Collections.addAll(this.picUrls, picUrls.split(","));
    }

    public ArrayList<String> getPicUrlsAtTaobao() {
        return picUrlsAtTaobao;
    }

    public void setPicUrlsAtTaobao(ArrayList<String> picUrlsAtTaobao) {
        this.picUrlsAtTaobao = picUrlsAtTaobao;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    public String quantity;
    public String stockType;

    public String getPid()
    {
        return this.pid;
    }

    public String getName() {
        return this.name;
    }

    public String getFacts() {
        return this.facts;
    }

    public String getPrice() {
        return this.price;
    }

    public double getPricePrevious() {
        return this.pricePrevious;
    }

    public String getAssembledSize() {
        return this.assembledSize;
    }

    public String getDesignerThoughts() {
        return this.designerThoughts;
    }

    public String getDesigner() {
        return this.designer;
    }

    public String getEnvironment() {
        return this.environment;
    }

    public String getGoodToKnow() {
        return this.goodToKnow;
    }

    public String getCareInst() {
        return this.careInst;
    }

    public String getCustMaterials() {
        return this.custMaterials;
    }

    public String getCustBenefit() {
        return this.custBenefit;
    }

    public float getWeight() {
        return this.weight;
    }

    public float getSize() {
        return this.size;
    }

    public ArrayList<String> getPicUrls() {
        return this.picUrls;
    }

    public String getType() {
        return this.type;
    }

    public String getColorCode() {
        return this.colorCode;
    }

    public String getTbPIC(int i)
    {
        return (String)this.picUrlsAtTaobao.get(i);
    }
    public ArrayList<String> getTbPICs() {
        return this.picUrlsAtTaobao;
    }

    public Item(String id)
    {
        this.pid = id;
    }

    public void initItemFromIKEA(String id) {
        try {
            String buf = HtmlUtil.getHtmlContent("http://www.ikea.com/cn/zh/catalog/products/" + id + "?type=xml&dataset=normal,prices,allimages,parentCategories");
            int count = 0;
            while ((buf.contains("<URL>") != true) && (count < 10)) {
                buf = HtmlUtil.getHtmlContent("http://www.ikea.com/cn/zh/catalog/products/" + id + "?type=xml&dataset=normal,prices,allimages,parentCategories");

                count++;
            }
            String info = XmlCatcher.getItem(buf);
            String[] infos = info.split("!!");

            this.name = infos[0];
            this.facts = infos[1];
            this.price = XmlCatcher.getPrice(buf);
            this.assembledSize = infos[8];
            this.designer = infos[4];
            this.environment = infos[5];
            this.goodToKnow = infos[6];
            this.careInst = infos[2];
            this.custBenefit = infos[3];
            this.custMaterials = infos[7];
            this.picUrls = XmlCatcher.getPicUrl(buf);
            ItemUtils.savePicToLocal(this);

            System.out.println("initItemFromIKEA " + buf + " " + this.name + " " + this.price);
        } catch (IOException ie) {
            System.err.println("initItemFromIKEA " + this.name + this.price);
        }
    }

    public void setWeight()
    {
    }

    public void setTypeAndColorCode(String type)
    {
        this.type = type;
        setColorCode();
    }

    public void setColorCode()
    {
        if (this.type == null)
            this.colorCode = "#000000";
        else if (this.type.contains("黄"))
            this.colorCode = "#e5cd00";
        else if (this.type.contains("红"))
            this.colorCode = "#cc0000";
        else if (this.type.contains("绿"))
            this.colorCode = "#22cc00";
        else if (this.type.contains("蓝"))
            this.colorCode = "#1759a8";
        else if (this.type.contains("木"))
            this.colorCode = "#e1cf9d";
        else if (this.type.contains("白")) {
            this.colorCode = "#cacaca";
        }
        else
            this.colorCode = "#000000";
    }

    public Item()
    {
    }

    public static void main(String[] args)
    {
        Item i = new Item("S09882144");
    }
}