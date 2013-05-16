package TOI.util;

import TOI.model.Item;
import TOI.model.Product;

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
 * Time: 下午11:44
 * To change this template use File | Settings | File Templates.
 */
public class ProductUtils {


    /**
     * 添加product到数据库
     */
    public static void addProductToSQL(Product product) {
        String sql = "insert into product(id,title,price,description,category," +
                "items,modifyTime,tid) values(?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, product.pid);
            stmt.setString(2, product.title);
            stmt.setString(3, product.price);
            stmt.setString(4, product.description);
            stmt.setString(5, product.subCategoryLocal);

            stmt.setString(6, product.itemIds);
            stmt.setTimestamp(7, getCurrentDateStr());
            stmt.setString(8, product.tid);



            stmt.execute();
            System.out.println(product.pid + "added!");

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
     * 更新一个product到数据库
     */
    public static boolean updateProductToSQL(Product product) {

        String sql = "select * from  product where items like '%" + product.pid + "%'";
        try {
            Statement stmt = getConnection().createStatement();
            ResultSet rs = null;
            rs = stmt.executeQuery(sql);
            String sqll="select * from  product where id like '%" + product.pid + "%'";
            Statement stmtt = getConnection().createStatement();
            ResultSet rss=stmtt.executeQuery(sqll);
            if (rss.next()) {
                String sql2 = "update product set id=?,title=?,price=?,description=?,category=?," +
                        "items=?,modifyTime=? where items like  '%" + product.pid + "%' or id like '%" + product.pid + "%'";
                PreparedStatement pstmt = getConnection().prepareStatement(sql2);
                pstmt.setString(1, product.pid);
                pstmt.setString(2, product.title);
                pstmt.setString(3, product.price);
                pstmt.setString(4, product.description);
                pstmt.setString(5, product.subCategoryLocal);
                StringBuilder itemsBuilder = new StringBuilder();
                pstmt.setString(6, product.itemIds);

                pstmt.setTimestamp(7, getCurrentDateStr());

                pstmt.execute();
                pstmt.close();
                System.out.println("PRODUCT:" + product.pid + "update!");
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
     * 更新一个product到数据库
     */
    public static boolean updateProductTid(Product product, String tid) {

        String sql = "select * from  product where items like '%" + product.pid + "%'";
        try {
            Statement stmt = getConnection().createStatement();
            ResultSet rs = null;
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String sql2 = "update product set tid=? where items like  '%" + product.pid + "%' ";
                PreparedStatement pstmt = getConnection().prepareStatement(sql2);
                pstmt.setString(1, tid);
                pstmt.execute();
                pstmt.close();
                System.out.println("[TID]:" + product.pid + "update!");
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
     * 把product放入数据库
     */
    public static void sendProductToSQL(Product product) {
        if (updateProductToSQL(product)) ;
        else addProductToSQL(product);
    }

    /**
     * 从IKEA获得一个完整的产品
     */
    public static Product grabProductFromIKEA(String id) {
        System.out.println("=============================\n"+"NOW:"+id);
        Product p = new Product(id);
        String buf = IkeaUtils.setBuf(id);

        p.setCategory(IkeaUtils.setCategory(buf));
        p.itemTypes = IkeaUtils.setItemTypes(buf);
        p.setItemsId();

        p.initItemListFromIKEA();
        p.setTitleAndPrice();
        return p;

    }

    /**
     * 添加一个完整的产品到数据库
     */
    public static void addToSQL(Product p) {
        ProductUtils.sendProductToSQL(p);
        for (Item item : p.itemsList)
            ItemUtils.sendItemToSQL(item);
    }

    /**
     * 从数据库获得一个完整的产品
     */
    public static Product setFromSQL(String id) {
        Product p = new Product(id);

        String sql = "select * from  product where items like '%" + id + "%'";
        try {
            Statement stmt = getConnection().createStatement();
            ResultSet rs = null;
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                p.title = (rs.getString("title"));
                System.out.println(p.title);
                p.price = (rs.getString("price"));
                p.subCategoryLocal = (rs.getString("category"));
                p.itemIds = rs.getString("items");
                p.tid= rs.getString("tid");

            }
            p.initItemListFromSQL();
        } catch (SQLException e) {
            System.out.println("=========SQLException==========" + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("========ClassNotFoundException===========" + e.getMessage());
            e.printStackTrace();
        }
        return p;
    }

    /**
     * 从数据库获得Items的清单
     */
    public static List<String> setItemsFromSQL(String id) {
        List<String> itemList = new ArrayList<String>();
        String sql = "select * from  product where items like  '%" + id + "%' ";
        PreparedStatement pstmt = null;
        try {
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String itemListString = rs.getString("items").replace("[", "").replace("]", "").replace(" ","");
                Collections.addAll(itemList, itemListString.split(","));
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
     * 从数据库获得产品列表
     */
    public static List<String> setProductListFromSQL() {
        List<String> productList = new ArrayList<String>();
        String sql = "select * from  product ";
        PreparedStatement pstmt = null;
        try {
            Statement stmt = getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String pid = rs.getString("id");
                productList.add(pid);
            }
        } catch (SQLException e) {
            System.out.println("=========SQLException==========" + e.getMessage());
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            System.out.println("========ClassNotFoundException===========" + e.getMessage());
            e.printStackTrace();

        }
        return productList;
    }
}
