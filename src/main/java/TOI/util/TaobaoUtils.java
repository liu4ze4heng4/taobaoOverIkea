package TOI.util;

import TOI.Constant.Constant;
import TOI.dao.DaoFactory;
import TOI.model.*;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.FileItem;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Picture;
import com.taobao.api.domain.Trade;
import com.taobao.api.request.*;
import com.taobao.api.response.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TaobaoUtils {
    public static TaobaoUtils getInstance(String topSession) {
        TaobaoUtils t = new TaobaoUtils(topSession);
        return t;
    }

    public User getTBUser() {
        User user = new User();
        TaobaoClient client = new DefaultTaobaoClient(Constant.url, Constant.appkey, Constant.appSecret);
        UserSellerGetRequest req = new UserSellerGetRequest();
        req.setFields("nick");
        try {
            UserSellerGetResponse response = client.execute(req, topSession);
            user.setTbName(response.getUser().getNick());
            user.setTbToken(topSession);
        } catch (ApiException e) {
            e.printStackTrace();
        }

        return user;
    }

    public static Map<String, String> getCCMapFromFile() {
        Map result = new HashMap();
        try {
            InputStreamReader insReader = new InputStreamReader(new FileInputStream(new File("tb_category")), "utf-8");
            BufferedReader bufReader = new BufferedReader(insReader);
            String temp;
            while ((temp = bufReader.readLine()) != null) {
                String[] tempA = temp.split("#######");
                result.put(tempA[0], tempA[1]);
            }
            bufReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    //    public static void getOnlineProducts() {
//        TaobaoClient client = new DefaultTaobaoClient(Constant.url, Constant.appkey, Constant.appSecret);
//        ItemsOnsaleGetRequest req = new ItemsOnsaleGetRequest();
//        req.setFields("num_iid,title,price");
//
//        req.setPageNo(Long.valueOf(1L));
//
//        req.setOrderBy("list_time:desc");
//        req.setIsTaobao(Boolean.valueOf(true));
//
//        req.setPageSize(Long.valueOf(200L));
//        try {
//            ItemsOnsaleGetResponse response = (ItemsOnsaleGetResponse) client.execute(req, Constant.sessionKey);
//            System.out.println(response.getTotalResults());
//            System.out.println(response.getItems().get(0));
//            int count = 0;
//            for (com.taobao.api.domain.Item onlineProduct : response.getItems()) {
//                String numId = onlineProduct.getNumIid().toString();
//                String title = onlineProduct.getTitle();
//                if (title.contains(".")) {
//                    int firstDot = title.indexOf(".");
//                    String pid = title.substring(firstDot - 3, firstDot + 7).replace(".", "");
//                    System.out.println(pid);
//                    Product p = new Product(pid);
//                    p.tid = numId;
//                    p.title = title;
//                    ProductUtils.addProductToSQL(p);
//                    count++;
//                }
//            }
//            System.out.println(count);
//        } catch (ApiException e) {
//            e.printStackTrace();
//        }
//    }
    public  List<String> uploadExtraPic(Product p,String tid) {
        TaobaoClient client = new DefaultTaobaoClient(Constant.url, Constant.appkey, Constant.appSecret);
        ItemImgUploadRequest req = new ItemImgUploadRequest();
        List<String> picIds=new ArrayList<String>();
        for (int i = 1; (i < p.getItemObjs().size()) && (i < 5); i++) {
            req.setNumIid(Long.valueOf(tid));
            req.setPosition(Long.valueOf(i));

            FileItem fItem = new FileItem(new File(Constant.picPath + (p.getItemObjs().get(i)).getPid() + "-0.jpg"));
            req.setImage(fItem);
            if (i == 0)
                req.setIsMajor(Boolean.valueOf(true));
            else
                req.setIsMajor(Boolean.valueOf(false));
            try {
                ItemImgUploadResponse response = client.execute(req, topSession);
                picIds.add(response.getItemImg().getId().toString());
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
        return picIds;
    }


    public void updateTaobaoItem(Product p,User user)
    {
        List<Item> itemList= p.getItemObjs();
        Item mainItem=itemList.get(0);
        for (Item item:itemList)
        {
            setPicUrlAtTaobao(item,user);
        }
        int pid=p.getPid();
    int userId=user.getId();
    String tid=DaoFactory.getUserProductDao().getUserProduct(userId,pid).getTid();
        TaobaoClient client = new DefaultTaobaoClient(Constant.url, Constant.appkey, Constant.appSecret);
            ItemUpdateRequest req = new ItemUpdateRequest();
            req.setNumIid(Long.valueOf(Long.parseLong(tid)));

            req.setDesc(VelocityUtil.generateDescription(p,user));

            req.setSellerCids( getCCMapFromFile().get(mainItem.getSubCategory()));
            req.setPostageId(Long.valueOf(user.getTbExpressId()));

            req.setItemWeight(mainItem.getWeight()+"");
            try {
                ItemUpdateResponse response =  client.execute(req, Constant.sessionKey);
                System.out.println(response.getBody());
            } catch (ApiException e) {
            }

    }

    public Product selectProduct(String id){
        Item mainItem = DaoFactory.getItemDao().getItemByIid(id);
        int productId = mainItem.getProductId();
        Product p = DaoFactory.getProductDao().getProduct(productId + "");
        List<Item> itemList = DaoFactory.getItemDao().getItemByPid(p.getPid());
        IkeaUtils.sortItemsByPrice(itemList);
        p.setItemObjs(itemList);
        return p;
    }

    public void addTaobaoItem2(Product p, User user) {
        List<Item> itemList= p.getItemObjs();
        Item mainItem=itemList.get(0);
        for (Item item:itemList)
        {
            setPicUrlAtTaobao(item,user);
        }

        UserProduct userProduct = new UserProduct();
        userProduct.setUser(user);
        List<String> productPics = new ArrayList<String>();
        userProduct.setPics(productPics);
        userProduct.setPid(p.getPid());

        TaobaoClient client = new DefaultTaobaoClient(Constant.url, Constant.appkey, Constant.appSecret);
        ItemAddRequest req = new ItemAddRequest();
        req.setNum(Long.valueOf(100L));
        req.setPrice(mainItem.getPrice());
        req.setType("fixed");
        req.setStuffStatus("new");
        req.setTitle(mainItem.getName() + mainItem.getFacts());
        req.setDesc(VelocityUtil.generateDescription(p,user));
        req.setLocationState("北京");
        req.setLocationCity("北京");
        req.setApproveStatus("instock");
        req.setCid(Long.valueOf(50006298L));
        req.setFreightPayer("buyer");
        req.setHasInvoice(Boolean.valueOf(true));
        req.setHasWarranty(Boolean.valueOf(true));
        req.setSellerCids( getCCMapFromFile().get(mainItem.getSubCategory()));
        req.setItemWeight(String.valueOf(mainItem.getWeight()));
        req.setPostageId(Long.valueOf(user.getTbExpressId()));
        FileItem fItem = new FileItem(new File(Constant.picPath + (p.itemObjs.get(0)).pid + "-0.jpg"));
        req.setImage(fItem);
        req.setOuterId(mainItem.pid);
        try {
            ItemAddResponse response = client.execute(req, topSession);
            System.out.println(response.getBody());

            JSONObject root = new JSONObject(response.getBody());
            JSONObject item_add_response = (JSONObject) root.get("item_add_response");
            JSONObject item = (JSONObject) item_add_response.get("item");

            String num_iid = item.getString("num_iid");
            userProduct.setTid(num_iid);
            System.out.println(num_iid.toString());
            productPics.addAll(uploadExtraPic(p, num_iid));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ApiException e) {
            e.printStackTrace();
        }
        DaoFactory.getUserProductDao().insert(userProduct);
    }


    public List<String> setPicUrlAtTaobao(Item item, User user) {
        int count = 0;
        List<String> picUrls = new ArrayList<String>();
        StringBuilder picUrlsAtTB = new StringBuilder();
        for (String url : item.picUrls) {
            String taobaoUrl = picUpload(new StringBuilder().append(item.pid).append("-").append(count++).toString(), Long.valueOf(user.getTbPicCategoryId()));
            picUrls.add(taobaoUrl.replace("http://img.taobaocdn.com/imgextra/", ""));
        }
        item.setPicUrlsAtTaobao(picUrls);
        return picUrls;

    }


    public String picUpload(String title, Long picCid) {
        try {
            TaobaoClient client = new DefaultTaobaoClient(Constant.url, Constant.appkey, Constant.appSecret);
            PictureUploadRequest req = new PictureUploadRequest();
            req.setPictureCategoryId(picCid);
            File file = new File(Constant.picPath + title + ".jpg");
            FileItem fItem;
            if (file.exists())
                fItem = new FileItem(file);
            else
                fItem = new FileItem(Constant.picPath + "noPic.jpg");
            req.setImg(fItem);
            req.setImageInputTitle(title + ".jpg");
            req.setTitle(title);
            PictureUploadResponse response = (PictureUploadResponse) client.execute(req, topSession);
            String url = response.getPicture().getPicturePath();
            System.out.println(response.getPicture().getPicturePath());
            return url;
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 更新线上产品
     */
//    public static void updateOnlineItem(Product product) {
//        TaobaoClient client = new DefaultTaobaoClient(Constant.url, Constant.appkey, Constant.appSecret);
//        if (product.tid != null) {
//            ItemUpdateRequest req = new ItemUpdateRequest();
//            req.setNumIid(Long.valueOf(Long.parseLong(product.tid)));
//
//            req.setDesc(product.setDescription());
//
//            req.setSellerCids((String) getCCMapFromFile().get(product.subCategoryLocal));
//            req.setPostageId(Long.valueOf(755800881L));
//
//            req.setItemWeight(product.virtualWeight);
//            try {
//                ItemUpdateResponse response = (ItemUpdateResponse) client.execute(req, Constant.sessionKey);
//                System.out.println(response.getBody());
//            } catch (ApiException e) {
//            }
//        } else {
//            System.err.println("No tid");
//        }
//    }

    private static ArrayList<SellerCid> getSellerCid() {
        ArrayList tbcategories = new ArrayList();
        TaobaoClient client = new DefaultTaobaoClient(Constant.url, Constant.appkey, Constant.appSecret);
        SellercatsListGetRequest req = new SellercatsListGetRequest();
        req.setNick(Constant.nick);

        String tmp = null;
        try {
            SellercatsListGetResponse response = (SellercatsListGetResponse) client.execute(req);
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

            while (!seller_cat_array.isNull(i)) {
                JSONObject product = (JSONObject) seller_cat_array.get(i++);
                if (!"0".equals(product.getString("parent_cid"))) {
                    SellerCid tbp = new SellerCid();
                    tbp.cid = product.getString("cid");
                    tbp.name = product.getString("name");
                    if (tbcategories.contains(tbp)) {
                        SellerCid older = (SellerCid) tbcategories.get(tbcategories.indexOf(tbp));
                        older.cid = (older.cid + "," + tbp.cid);
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

    public static void deleteUnusePics() {
        TaobaoClient client = new DefaultTaobaoClient(Constant.url, Constant.appkey, Constant.appSecret);
        PictureGetRequest req = new PictureGetRequest();

        req.setPictureCategoryId(Long.valueOf(15718119237420486L));

        req.setOrderBy("time:desc");

        req.setPageNo(Long.valueOf(1L));
        req.setPageSize(Long.valueOf(100L));
        try {
            PictureGetResponse response = (PictureGetResponse) client.execute(req, Constant.sessionKey);
            System.out.println(response.getBody());
            List pictureList = response.getPictures();
            for (int i = 0; i < pictureList.size(); i++) {
                Long pictureId = ((Picture) pictureList.get(i)).getPictureId();
                PictureIsreferencedGetRequest req1 = new PictureIsreferencedGetRequest();
                req1.setPictureId(pictureId);
                PictureIsreferencedGetResponse response1 = (PictureIsreferencedGetResponse) client.execute(req1, Constant.sessionKey);
                System.out.println(response1.getIsReferenced());
                if (!response1.getIsReferenced().booleanValue()) {
                    PictureDeleteRequest req2 = new PictureDeleteRequest();
                    req2.setPictureIds(pictureId.toString());
                    PictureDeleteResponse response2 = (PictureDeleteResponse) client.execute(req2, Constant.sessionKey);
                    System.out.println(response2.getSuccess());
                }
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    public void getPicCategory(String name) {
        TaobaoClient client = new DefaultTaobaoClient(Constant.url, Constant.appkey, Constant.appSecret);
        PictureCategoryGetRequest req = new PictureCategoryGetRequest();

        req.setPictureCategoryName(name);

        req.setParentId(Long.valueOf(-1L));
        try {
            PictureCategoryGetResponse response = (PictureCategoryGetResponse) client.execute(req, topSession);
            System.out.println(response.getBody());
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    public static String WAIT_SELLER_SEND_GOODS = "WAIT_SELLER_SEND_GOODS";//(等待卖家发货,即:买家已付款)

    public static String SELLER_CONSIGNED_PART = "SELLER_CONSIGNED_PART";     //卖家部分发货
    public static String WAIT_BUYER_CONFIRM_GOODS = "WAIT_BUYER_CONFIRM_GOODS";     //卖家发货
    public static String TRADE_BUYER_SIGNED = "TRADE_BUYER_SIGNED";     //签收
    public static String TRADE_FINISHED = "TRADE_FINISHED";     //货到付款专用
    static class SellerCid {

        public String cid;
        public String name;
        public boolean equals(Object obj) {
            return ((SellerCid) obj).name.equals(this.name);
        }

        public String toString() {
            return this.name + "#######" + this.cid;
        }

    }
    public String topSession;

    public TaobaoUtils(String topSession) {
        this.topSession = topSession;
    }

    public ArrayList<Trade> tradeFilter(String start, String end) {
        try {
            TaobaoClient client = new DefaultTaobaoClient(Constant.url, Constant.appkey, Constant.appSecret);
            TradesSoldGetRequest req = new TradesSoldGetRequest();
            req.setFields("tid,pay_time,buyer_nick,receiver_name, receiver_state, receiver_city, receiver_district, receiver_address, receiver_zip, receiver_phone,seller_flag");
            Date dateTime = SimpleDateFormat.getDateTimeInstance().parse(start);
            req.setStartCreated(dateTime);
            Date dateTime2 = SimpleDateFormat.getDateTimeInstance().parse(end);
            req.setEndCreated(dateTime2);
            req.setStatus("WAIT_SELLER_SEND_GOODS");
//            req.setBuyerNick("tb749866_2012");

            TradesSoldGetResponse response = client.execute(req, topSession);
            List<Trade> trades = response.getTrades();
            ArrayList<Trade> tradeList = new ArrayList<Trade>();

            for (Trade trade : trades) {
                if (trade.getSellerFlag() == 1) {
                    enhanceTrade(trade);
                    tradeList.add(trade);
                } else
                    System.out.println("刚刚掠过了一个订单:" + trade.getBuyerNick());


            }


            return tradeList;
        } catch (ApiException e1) {
            e1.printStackTrace();
            return null;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void enhanceTrade(Trade trade) {
        try {
            TaobaoClient client = new DefaultTaobaoClient(Constant.url, Constant.appkey, Constant.appSecret);
            TradeFullinfoGetRequest req = new TradeFullinfoGetRequest();
            req.setFields("receiver_mobile,seller_memo");
            req.setTid(trade.getTid());
            TradeFullinfoGetResponse response = client.execute(req, topSession);
            trade.setReceiverMobile(response.getTrade().getReceiverMobile());
            trade.setSellerMemo(response.getTrade().getSellerMemo());
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }



    /**
     * 获取线上宝贝
     */

    //    }
//        ProductUtils.updateSingleValue(p.pid, "isChanged", "0");
//        System.err.print(p.pid);
//        }
//            e.printStackTrace();
//        } catch (ApiException e) {
//            }
//                e.printStackTrace();
//            } catch (JSONException e) {
//                System.out.println(num_iid.toString());
//                ProductUtils.updateProductTid(p, num_iid);
//
//                String num_iid = item.getString("num_iid");
//
//                JSONObject item = (JSONObject) item_add_response.get("item");
//                JSONObject item_add_response = (JSONObject) root.get("item_add_response");
//                JSONObject root = new JSONObject(response.getBody());
//            try {
//            System.out.println(response.getBody());
//            ItemAddResponse response = (ItemAddResponse) client.execute(req, Constant.sessionKey);
//        try {
//        req.setOuterId(p.pid);
//
//        req.setImage(fItem);
//        System.out.println("E:\\IKEAPIC\\ITEMSPIC\\" + ((TOI.model.Item) p.itemsList.get(0)).pid + "-0.jpg");
//        FileItem fItem = new FileItem(new File("E:\\IKEAPIC\\ITEMPICS\\" + ((TOI.model.Item) p.itemsList.get(0)).pid + "-0.jpg"));
//
//        }
//            req.setEmsFee("5");
//            req.setExpressFee("5");
//            req.setPostFee("5");
//        } else {
//            req.setPostageId(Long.valueOf(755800881L));
//        if (Constant.nick == "charick") {
//
//        req.setItemWeight(p.virtualWeight);
//        req.setSellerCids((String) getCCMapFromFile().get(p.subCategoryLocal));
//        System.out.println((String) getCCMapFromFile().get(p.subCategoryLocal));
//
//        req.setHasWarranty(Boolean.valueOf(true));
//        req.setHasInvoice(Boolean.valueOf(true));
//
//        req.setFreightPayer("buyer");
//
//        req.setCid(Long.valueOf(50006298L));
//        req.setApproveStatus("instock");
//        req.setLocationCity("北京");
//        req.setLocationState("北京");
//        req.setDesc(p.setDescription());
//        req.setTitle(p.title);
//        req.setStuffStatus("new");
//        req.setType("fixed");
//        req.setPrice(p.price);
//        req.setNum(Long.valueOf(30L));
//        ItemAddRequest req = new ItemAddRequest();
//        TaobaoClient client = new DefaultTaobaoClient(Constant.url, Constant.appkey, Constant.appSecret);
//        }
//            ItemUtils.setPicUrlAtTaobao(item);
//        for (TOI.model.Item item : p.itemsList) {
    public static void saveTBcategory2File() {
        try {
            ArrayList<SellerCid> tbclist = getSellerCid();
            FileWriter fw = new FileWriter(new File("tb_category"));
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
}