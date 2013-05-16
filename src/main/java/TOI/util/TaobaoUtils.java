package TOI.util;//package com.fec.TOI.util;

import TOI.Constant.Constant;
import TOI.model.Product;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.FileItem;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Picture;
import com.taobao.api.request.*;
import com.taobao.api.response.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaobaoUtils {
    public static Map<String, String> getCCMapFromFile() {
        Map<String, String> result = new HashMap<String, String>();
        String temp;
        String[] tempA;
        try {
            InputStreamReader insReader = new InputStreamReader(new FileInputStream(new File(Constant.tb_category_file)), "utf-8");
            BufferedReader bufReader = new BufferedReader(insReader);
            while ((temp = bufReader.readLine()) != null) {
                tempA = temp.split(Constant.split);
                result.put(tempA[0], tempA[1]);
            }
            bufReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void saveTBcategory2File() {
        try {
            ArrayList<SellerCid> tbclist = getSellerCid();
            FileWriter fw = new FileWriter(new File(Constant.tb_category_file));
            for (SellerCid tbCategory : tbclist) {
                fw.write(tbCategory + "\n");
            }
            fw.flush();
            fw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("save taobao category to file finish!");
    }

    /**
     *
     */
    public static void addTaobaoItem(Product p) {
        TaobaoClient client = new DefaultTaobaoClient(Constant.url, Constant.appkey, Constant.appSecret);
        ItemAddRequest req = new ItemAddRequest();
        req.setNum(30L);
        req.setPrice(p.price);
        req.setType("fixed");
        req.setStuffStatus("new");
        req.setTitle(p.title);
        req.setDesc(p.setDescription());
        req.setLocationState("北京");
        req.setLocationCity("北京");
        req.setApproveStatus("instock");
        req.setCid(50006298L);
        // req.setProps("20000:33564;21514:38489");
        req.setFreightPayer("buyer");
        // req.setValidThru(7L);
        req.setHasInvoice(true);
        req.setHasWarranty(true);
        // req.setHasShowcase(true);
        System.out.println(getCCMapFromFile().get(p.subCategoryLocal));
        req.setSellerCids(getCCMapFromFile().get(p.subCategoryLocal));
        req.setItemWeight("1");
        // req.setHasDiscount(true);
        // req.setPostFee("5.07");
        // req.setExpressFee("15.07");
        // req.setEmsFee("25.07");
        // Date dateTime =
        // SimpleDateFormat.getDateTimeInstance().parse("2000-01-01 00:00:00");
        // req.setListTime(dateTime);
        // req.setIncrement("2.50");
        FileItem fItem = new FileItem(new File(Constant.picPath + "\\ITEMPICS\\" + p.itemsList.get(0).pid + "-0.jpg"));
        System.out.println(Constant.picPath + "\\ITEMSPIC\\" + p.itemsList.get(0).pid + "-0.jpg");
        req.setImage(fItem);
        req.setPostageId(755800881L);
        // req.setAuctionPoint(5L);
        // req.setPropertyAlias("pid:vid:别名;pid1:vid1:别名1");
        // req.setInputPids("pid1,pid2,pid3");
        // req.setSkuProperties("pid:vid;pid:vid");
        // req.setSkuQuantities("2,3,4");
        // req.setSkuPrices("200.07");
        // req.setSkuOuterIds("1234,1342");
        // req.setLang("zh_CN");
        req.setOuterId(p.pid);
        // req.setProductId(123456789L);
        // req.setPicPath("i7/T1rfxpXcVhXXXH9QcZ_033150.jpg");
        // req.setAutoFill("time_card");
        // req.setInputStr("耐克;耐克系列;科比系列;科比系列;2K5,Nike乔丹鞋;乔丹系列;乔丹鞋系列;乔丹鞋系列;");
        // req.setIsTaobao(true);
        // req.setIsEx(true);
        // req.setIs3D(true);
        // req.setSellPromise(true);
        // req.setAfterSaleId(47758L);
        // req.setCodPostageId(53899L);
        // req.setIsLightningConsignment(true);
        // req.setWeight(100L);
        // req.setIsXinpin(false);
        // req.setSubStock(1L);
        ItemAddResponse response;
        try {
            response = client.execute(req, "6100d30b25a4315f8bfc8e711a72fb491c33e31e73953c842635718");
            try {
                JSONObject root = new JSONObject(response.getBody());
                JSONObject item_add_response = (JSONObject) root.get("item_add_response");
                JSONObject item = (JSONObject) item_add_response.get("item");

                String num_iid = item.getString("num_iid");

                ProductUtils.updateProductTid(p, num_iid);
                System.out.println(num_iid.toString());


            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (ApiException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    /**
     * 获取线上宝贝
     */
    public static void getOnlineProducts(){

        TaobaoClient client = new DefaultTaobaoClient(Constant.url, Constant.appkey, Constant.appSecret);
        ItemsOnsaleGetRequest req=new ItemsOnsaleGetRequest();
        req.setFields("num_iid,title,price");
//        req.setQ("趣活");
//        req.setCid(1512L);
//        req.setSellerCids("11");
        req.setPageNo(1L);
//        req.setHasDiscount(true);
//        req.setHasShowcase(true);
        req.setOrderBy("list_time:desc");
        req.setIsTaobao(true);
//        req.setIsEx(true);
        req.setPageSize(200L);
//        Date dateTime = SimpleDateFormat.getDateTimeInstance().parse("2000-01-01 00:00:00");
//        req.setStartModified(dateTime);
//        Date dateTime = SimpleDateFormat.getDateTimeInstance().parse("2000-01-01 00:00:00");
//        req.setEndModified(dateTime);
        try{
        ItemsOnsaleGetResponse response = client.execute(req , Constant.sessionKey);
            System.out.println(response.getTotalResults());
            System.out.println(response.getItems().get(0));
            int count=0;
           for(com.taobao.api.domain.Item onlineProduct:response.getItems())
           {
               String numId=onlineProduct.getNumIid().toString();
               String title=onlineProduct.getTitle();
               if(title.contains("."))
               { int firstDot=title.indexOf(".");
                   String pid=title.substring(firstDot-3,firstDot+7).replace(".","");
                   System.out.println(pid);
                   Product p=new Product(pid);
                   p.tid=numId;
                   p.title=title;
                   ProductUtils.addProductToSQL(p);
                   count++;
               }
           }     System.out.println(count);
        } catch (ApiException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    }
    /**
     * 更新线上产品
     */
   public static void updateOnlineItem(String id){
       TaobaoClient client = new DefaultTaobaoClient(Constant.url, Constant.appkey, Constant.appSecret);
       Product product=ProductUtils.setFromSQL(id);
       if(product.tid!=null) {
       ItemUpdateRequest req = new ItemUpdateRequest();
       req.setNumIid(Long.parseLong(product.tid));
//		req.setCid(1512L);
//		req.setProps("20000:33564;21514:38489");
//		req.setNum(50L);
//		req.setPrice(product.price);
//		req.setTitle("Nokia N97全新行货");
       req.setDesc(product.setDescription());
//		req.setPostFee("5.07");
//		req.setExpressFee("15.07");
//		req.setEmsFee("25.07");
//		req.setIncrement("10.50");
//           FileItem fItem = new FileItem(new File(Constant.picPath + "\\ITEMPICS\\" + product.itemsList.get(0).pid + "-0" +
//                   ".jpg"));
//           System.out.println(Constant.picPath + "\\ITEMSPIC\\" + product.itemsList.get(0).pid + "-0.jpg");
//           req.setImage(fItem);
//		req.setStuffStatus("new");
//		req.setAuctionPoint(5L);
//		req.setPropertyAlias("pid:vid:别名;pid1:vid1:别名1");
//		req.setInputPids("pid1,pid2,pid3");
//		req.setSkuQuantities("2,3,4");
//		req.setSkuPrices("10.00,5.00");
//		req.setSkuProperties("pid:vid;pid:vid");
//		req.setSellerCids("1105");
       req.setPostageId(755800881L);
//       req.setOuterId();
//		req.setProductId(123456789L);
//		req.setPicPath("i7/T1rfxpXcVhXXXH9QcZ_033150.jpg");
//		req.setAutoFill("time_card");
//		req.setSkuOuterIds("1234,1342");
//		req.setIsTaobao(true);
//		req.setIsEx(true);
//		req.setIs3D(true);
//		req.setIsReplaceSku(true);
//		req.setInputStr("耐克;耐克系列;科比系列;科比系列;2K5,Nike乔丹鞋;乔丹系列;乔丹鞋系列;乔丹鞋系列;json5");
//		req.setLang("zh_CN");
//		req.setHasDiscount(true);
//		req.setHasShowcase(true);
//       req.setApproveStatus("onsale");
//		req.setFreightPayer("buyer");
//		req.setValidThru(7L);
//		req.setHasInvoice(true);
//		req.setHasWarranty(true);
//		req.setAfterSaleId(47745L);
//		req.setSellPromise(true);
//		req.setCodPostageId(53899L);
//		req.setIsLightningConsignment(true);
//		req.setWeight(1L);
//		req.setIsXinpin(true);
//		req.setSubStock(1L);
//		req.setGlobalStockType("1");
//		req.setItemSize("bulk:8");
//       req.setItemWeight("1");
//		req.setEmptyFields("food_security.plan_storage,food_security.period");
//		req.setLocalityLifeExpirydate("2012-08-06,2012-08-16");
//		req.setLocalityLifeNetworkId("5645746");
//		req.setLocalityLifeMerchant("56879:码商X");
//		req.setLocalityLifeVerification("1");
//		req.setLocalityLifeRefundRatio(50L);
//		req.setLocalityLifeChooseLogis("0");
//		req.setLocalityLifeOnsaleAutoRefundRatio(80L);
//		req.setScenicTicketPayWay(1L);
//		req.setScenicTicketBookCost("5.99");
//		req.setPaimaiInfoMode(1L);
//		req.setPaimaiInfoDeposit(20L);
//		req.setPaimaiInfoInterval(5L);
//		req.setPaimaiInfoReserve("11");
//		req.setPaimaiInfoValidHour(2L);
//		req.setPaimaiInfoValidMinute(22L);
       try {

           ItemUpdateResponse response = client.execute(req, Constant.sessionKey);
           System.out.println(response.getBody());

       } catch (ApiException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }
       }
       else
           System.err.println("No tid");
   }

    /**
     *getSellerCid
     * @return
     */
    private static ArrayList<SellerCid> getSellerCid() {
        ArrayList<SellerCid> tbcategories = new ArrayList<SellerCid>();
        TaobaoClient client = new DefaultTaobaoClient(Constant.url, Constant.appkey, Constant.appSecret);
        SellercatsListGetRequest req = new SellercatsListGetRequest();
        req.setNick(Constant.nick);
        SellercatsListGetResponse response;
        String tmp = null;
        try {
            response = client.execute(req);
            tmp = response.getBody();
        } catch (ApiException e1) {
            e1.printStackTrace();
        }
        try {
            JSONObject root = new JSONObject(tmp);
            JSONObject seller_cats = (JSONObject) root.get("sellercats_list_get_response");
            JSONObject seller_cat = (JSONObject) seller_cats.get("seller_cats");
            JSONArray seller_cat_array = seller_cat.getJSONArray("seller_cat");
            int i = 0;
            JSONObject product;
            while (!seller_cat_array.isNull(i)) {
                product = (JSONObject) seller_cat_array.get(i++);
                if ("0".equals(product.getString("parent_cid"))) {
                    continue;
                } else {
                    SellerCid tbp = new SellerCid();
                    tbp.cid = product.getString("cid");
                    tbp.name = product.getString("name");
                    if (tbcategories.contains(tbp)) {
                        SellerCid older = tbcategories.get(tbcategories.indexOf(tbp));
                        older.cid = older.cid + "," + tbp.cid;
                    } else {
                        tbcategories.add(tbp);
                    }
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tbcategories;
    }

    static class SellerCid {
        public String cid;
        public String name;

        @Override
        public boolean equals(Object obj) {
            return ((SellerCid) obj).name.equals(name);
        }

        @Override
        public String toString() {
            return name + Constant.split + cid;
        }
    }
   public static void deleteUnusePics(){
       TaobaoClient client=new DefaultTaobaoClient(Constant.url, Constant.appkey, Constant.appSecret);
       PictureGetRequest req=new PictureGetRequest();
//       req.setPictureId(1001L);
       req.setPictureCategoryId(15718119237420486L);
//       req.setDeleted("1");
//       req.setTitle("标题");
       req.setOrderBy("time:desc");
//       Date dateTime = SimpleDateFormat.getDateTimeInstance().parse("2000-01-01 00:00:00");
//       req.setStartDate(dateTime);
//       Date dateTime = SimpleDateFormat.getDateTimeInstance().parse("2000-01-01 00:00:00");
//       req.setEndDate(dateTime);
       req.setPageNo(1L);
       req.setPageSize(100L);
//       Date dateTime = SimpleDateFormat.getDateTimeInstance().parse("2011-01-01 11:11:11");
//       req.setModifiedTime(dateTime);
       try{
       PictureGetResponse response = client.execute(req , Constant.sessionKey);
           System.out.println(response.getBody());
           List<Picture> pictureList=response.getPictures();
           for(int i=0;i<pictureList.size();i++)
           {Long pictureId=pictureList.get(i).getPictureId();
               PictureIsreferencedGetRequest req1=new PictureIsreferencedGetRequest();
               req1.setPictureId(pictureId);
               PictureIsreferencedGetResponse response1 = client.execute(req1 , Constant.sessionKey);
               System.out.println(response1.getIsReferenced());
                if(!response1.getIsReferenced()){
                    PictureDeleteRequest req2=new PictureDeleteRequest();
                    req2.setPictureIds(pictureId.toString());
                    PictureDeleteResponse response2 = client.execute(req2 , Constant.sessionKey);
                    System.out.println(response2.getSuccess());
                }
           }
           } catch (ApiException e) {

        // TODO Auto-generated catch block
        e.printStackTrace();
    }
   }
    public static  void getPicCategory(String name){
        TaobaoClient client=new DefaultTaobaoClient(Constant.url, Constant.appkey, Constant.appSecret);
        PictureCategoryGetRequest req=new PictureCategoryGetRequest();
//        req.setPictureCategoryId(100L);
        req.setPictureCategoryName(name);
//        req.setType("auction");
        req.setParentId(-1L);
        try{
        PictureCategoryGetResponse response = client.execute(req , Constant.sessionKey);
            System.out.println(response.getBody());
        } catch (ApiException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
