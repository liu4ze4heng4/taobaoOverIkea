package TOI.model;
import java.text.DecimalFormat;
import java.util.*;

public class Product
{
    public int pid;
    public String tid;
    public String price;
    public String title;
    public Map<String, String> itemTypes = null;
    public String weight;
    public String virtualWeight;
    public List<String> itemIds;
    public List<Item> itemObjs;


    public List<Item> getItemObjs() {
        return itemObjs;
    }



    public void setItemObjs(List<Item> itemObjs) {
        this.itemObjs = itemObjs;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }
    public List<String> getItemIds() {
        return itemIds;
    }



    public String getItemss(){
        StringBuilder sb=new StringBuilder();
        for (int i = 0; i < itemObjs.size(); i++) {
                     sb.append(itemObjs.get(i).getPid()+",");
        }
        return sb.toString();

    }

    public void setItemIds(String itemIds) {
                       if(this.itemIds==null){
                                            this.itemIds=new ArrayList<String>();
                       }

    Collections.addAll(this.itemIds, itemIds.split(","));
}


    public void setVirtualWeight()
    {
        if ((this.weight != null) && (this.weight != "-1") && (this.price != null))
        {
            double vw = 1.3012D + 1.3752D * Double.parseDouble(this.weight) + 0.0206D * Double.parseDouble(this.price);
            DecimalFormat df = new DecimalFormat(".##");
            this.virtualWeight = df.format(vw);
        }
        else {
            this.virtualWeight = "-1";
        }
    }

    public void setVirtualWeight2()
    {
        if ((this.weight != null) && (this.weight != "-1"))
        {
            double vw = Math.ceil(Double.parseDouble(this.weight) * 1.2D);
            this.virtualWeight = String.valueOf(vw);
        }
        else {
            this.virtualWeight = "-1";
        }
    }

//    public void setTitleAndPrice()
//    {
//        Item topItem = (Item)this.itemsList.get(0);
//        this.title = new StringBuilder().append(topItem.name).append(topItem.facts).toString();
//        System.out.println(this.title);
//        this.price = topItem.price;
//    }

//    public void setItemsId()
//    {
//        if (this.itemTypes.size() != 0)
//            this.itemIds = this.itemTypes.keySet().toString();
//        else
//            this.itemIds = new StringBuilder().append("[").append(this.pid).append("]").toString();
//    }
//
//    public void setItemsId2()
//    {
//        StringBuilder ii = new StringBuilder();
//        ii.append("[");
//        for (Item item : this.itemsList)
//            ii.append(new StringBuilder().append(item.pid).append(",").toString());
//        ii.append("]");
//        this.itemIds = ii.toString().replace(",]", "]");
//    }
//
//    public void initItemListFromIKEA()
//    {
//        if (this.itemTypes.isEmpty()) {
//            Item item = new Item(this.pid);
//            item.initItemFromIKEA(this.pid);
//            this.itemsList.add(item);
//            item.setTypeAndColorCode(item.name);
//        } else {
//            for (String id : this.itemTypes.keySet()) {
//                Item item = new Item(id);
//                item.initItemFromIKEA(id);
//                item.setTypeAndColorCode((String)this.itemTypes.get(id));
//                this.itemsList.add(item);
//            }
//        }
//
//        for (int i = 0; i < this.itemsList.size(); i++) {
//            for (int j = i + 1; j < this.itemsList.size(); j++) {
//                if (compare((Item)this.itemsList.get(i), (Item)this.itemsList.get(j)) == 1)
//                {
//                    Item temp = (Item)this.itemsList.get(i);
//                    this.itemsList.set(i, this.itemsList.get(j));
//                    this.itemsList.set(j, temp);
//                }
//            }
//        }
//        for (int i = 0; i < this.itemsList.size(); i++)
//            System.out.println(((Item)this.itemsList.get(i)).price);
//    }

//    public void initItemListFromSQL()
//    {
//        List<String> items = ProductUtils.setItemsFromSQL(this.pid);
//        for (String id : items)
//            this.itemsList.add(ItemUtils.setItemFromSQL(id));
//    }
}