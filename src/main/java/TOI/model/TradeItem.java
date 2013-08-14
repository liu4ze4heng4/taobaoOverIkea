package TOI.model;

public class TradeItem
{
    public String id;
    public int quantity;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String price;
    public String name;
    public String type;
    public String picUrl;
    public String stockInfo;
    public int stockInfoCode;
    public int total;

    public String getFacts() {
        return facts;
    }

    public String facts;

    public String getId()
    {
        return this.id;
    }

    public String getQuantity() {
        return this.quantity + "";
    }

    public String getName() {
        return this.name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public void setStockInfo(String stockInfo) {
        this.stockInfo = stockInfo;
    }

    public void setStockInfoCode(int stockInfoCode) {
        this.stockInfoCode = stockInfoCode;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setFacts(String facts) {
        this.facts = facts;
    }

    public String getType() {
        return this.type;
    }

    public String getPicUrl() {
        return this.picUrl;
    }

    public String getStockInfo()
    {
        return this.stockInfo;
    }

    public String getTotal()
    {
        return this.total + "";
    }

    public String getStockInfoCode() {
        return this.stockInfoCode + "";
    }

    public TradeItem(String id)
    {
        this.id = id;
        this.quantity = 0;
    }
}