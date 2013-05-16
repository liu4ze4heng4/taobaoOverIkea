package TOI.util;

import TOI.Constant.Constant;
import TOI.model.Item;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static TOI.util.SQLUtils.getConnection;
import static TOI.util.SQLUtils.getCurrentDateStr;

/**
 * Created with IntelliJ IDEA.
 * User: W.k
 * Date: 13-5-3
 * Time: 下午11:54
 * To change this template use File | Settings | File Templates.
 */
public class ItemUtils {
    /**
     * 添加一个item到数据库
     */
    private static void additem(Item item) {
        String sql = "insert into item(pid,name,facts,price,assembledSize,designer,environment,goodToKnow,careInst," +
                "custMaterials,custBenefit,picUrls,modifyTime,type) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, item.pid);
            stmt.setString(2, item.name);
            stmt.setString(3, item.facts);
            stmt.setString(4, item.price);
            stmt.setString(5, item.assembledSize);
            stmt.setString(6, item.designer);
            stmt.setString(7, item.environment);
            stmt.setString(8, item.goodToKnow);
            stmt.setString(9, item.careInst);
            stmt.setString(10, item.custMaterials);
            stmt.setString(11, item.custBenefit);
            StringBuilder picUrls = new StringBuilder();
            ArrayList<String> PicIds = item.picUrls;
            for (int i = 0; i < PicIds.size(); i++) {
                String picId = PicIds.get(i);
                picUrls.append(picId);
                if (i != PicIds.size() - 1)
                    picUrls.append(",");
            }
            stmt.setString(12, picUrls.toString());
            stmt.setTimestamp(13, getCurrentDateStr());
            stmt.setString(14,item.type);

            stmt.execute();
            System.out.println("ITEM:"+item.pid + "added!");

        } catch (SQLException e) {
            System.out.println("=========SQLException==========" + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("========ClassNotFoundException===========" + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("========Exception===========未知" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 更新item信息
     */
    private static boolean updateItem(Item item) {

        String sql = "select * from  item where pid= '" + item.pid+"'";
        try {
            Statement stmt = getConnection().createStatement();
            ResultSet rs = null;
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String sql2 = "update item set name=?,facts=?,price=?,assembledSize=?,designer=?,environment=?," +
                        "goodToKnow=?,careInst=?,custMaterials=?,custBenefit=?,picUrls=?,modifyTime=?,type=? " +
                        "where pid = '" + item.pid + "' ";
                PreparedStatement pstmt = getConnection().prepareStatement(sql2);
                pstmt.setString(1, item.name);
                pstmt.setString(2, item.facts);
                pstmt.setString(3, item.price);
                pstmt.setString(4, item.assembledSize);
                pstmt.setString(5, item.designer);
                pstmt.setString(6, item.environment);
                pstmt.setString(7, item.goodToKnow);
                pstmt.setString(8, item.careInst);
                pstmt.setString(9, item.custMaterials);
                pstmt.setString(10, item.custBenefit);
                StringBuilder picUrls = new StringBuilder();
                ArrayList<String> PicIds = item.picUrls;
                for (int i = 0; i < PicIds.size(); i++) {
                    String picId = PicIds.get(i);
                    picUrls.append(picId);
                    if (i != PicIds.size() - 1)
                        picUrls.append(",");
                }
                pstmt.setString(11, picUrls.toString());
                pstmt.setTimestamp(12, getCurrentDateStr());
                pstmt.setString(13,item.type);
                pstmt.execute();
                pstmt.close();
                System.out.println("ITEM:"+item.pid + "update!");
                return true;
            } else return false;
        } catch (SQLException e) {
            System.out.println("=========SQLException==========" + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            System.out.println("========ClassNotFoundException===========" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 把item放入数据库
     */
    public static void sendItemToSQL(Item item) {
        if (updateItem(item)) ;
        else additem(item);
    }
    /**
     * 获取item
     */
    public static Item setItemFromSQL(String pid) {
        String sql = "select * from  item where pid='" + pid+"'";
        Item item = new Item();
        try {
            Statement stmt = getConnection().createStatement();
            ResultSet rs = null;
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                item.pid = (rs.getString("pid"));
                item.name = (rs.getString("name"));
                item.facts = (rs.getString("facts"));
                item.price = (rs.getString("price"));
                item.assembledSize = (rs.getString("assembledSize"));
                item.designer = (rs.getString("designer"));
                item.environment = (rs.getString("environment"));
                item.goodToKnow = (rs.getString("goodToKnow"));
                item.careInst = (rs.getString("careInst"));
                item.custMaterials = (rs.getString("custMaterials"));
                item.custBenefit = (rs.getString("custBenefit"));
                String picUrl = (rs.getString("pid"));
                Collections.addAll(item.picUrls, picUrl.split(","));
                item.setTypeAndColorCode(rs.getString("type"));
            }
        } catch (SQLException e) {
            System.out.println("=========SQLException==========" + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("========ClassNotFoundException===========" + e.getMessage());
            e.printStackTrace();
        }
        return item;
    }


    /**
     * 获取itemList
     */
    public static List<Item> getItems(String product) {
        String sql = "select * from  item where product=" + product;

        List<Item> itemList=new ArrayList<Item>();
        try {
            Statement stmt = getConnection().createStatement();
            ResultSet rs = null;
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Item item = new Item();
                item.pid = (rs.getString("pid"));
                item.name = (rs.getString("name"));
                item.facts = (rs.getString("facts"));
                item.price = (rs.getString("price"));
                item.assembledSize = (rs.getString("assembledSize"));
                item.designer = (rs.getString("designer"));
                item.environment = (rs.getString("environment"));
                item.goodToKnow = (rs.getString("goodToKnow"));
                item.careInst = (rs.getString("careInst"));
                item.custMaterials = (rs.getString("custMaterials"));
                item.custBenefit = (rs.getString("custBenefit"));
                String picUrl = (rs.getString("pid"));
                Collections.addAll(item.picUrls, picUrl.split(","));
                itemList.add(item);
            }
        } catch (SQLException e) {
            System.out.println("=========SQLException==========" + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("========ClassNotFoundException===========" + e.getMessage());
            e.printStackTrace();
        }
        return itemList;
    }
    /**
     * 保存图片
     */
    public static void savePicToLocal(Item item){
            try {
                for (int i = 0; i < item.picUrls.size(); i++) {
                    URL url = new URL(item.picUrls.get(i));
                    URLConnection urlCon = url.openConnection();
                    InputStream is = urlCon.getInputStream();
                    BufferedInputStream bis = new BufferedInputStream(is);
                    FileOutputStream fos = new FileOutputStream(Constant.picPath + "\\\\ITEMPICS\\\\" + item.pid+
                            "-"+i+
                     ".jpg");
                    BufferedOutputStream bos = new BufferedOutputStream(fos);

                    int read;
                    while ((read = bis.read()) != -1) {
                        bos.write(read);
                    }
                    bos.close();// 不关闭，输出流不刷新，有可能得到无效图片
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

//        }
    }



}
