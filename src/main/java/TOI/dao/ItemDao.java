package TOI.dao;

import TOI.model.Item;
import TOI.model.Product;
import TOI.util.SQLUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Wk
 * Date: 13-8-8
 * Time: 下午4:05
 * To change this template use File | Settings | File Templates.
 */
public class ItemDao implements ParameterizedRowMapper<Item> {
    public JdbcTemplate ikeaTemplate;

    public JdbcTemplate getIkeaTemplate() {
        return ikeaTemplate;
    }

    public void setIkeaTemplate(JdbcTemplate ikeaTemplate) {
        this.ikeaTemplate = ikeaTemplate;
    }

    public Item mapRow(ResultSet rs, int i) throws SQLException {
        Item item = new Item();
        item.setPid(rs.getString("pid"));
        item.setName(rs.getString("name"));
        item.setFacts(rs.getString("facts"));
        item.setPrice(rs.getString("price"));
        item.setAssembledSize(rs.getString("assembledSize"));
        item.setDesigner(rs.getString("designer"));
        item.setEnvironment(rs.getString("environment"));
        item.setGoodToKnow(rs.getString("goodToKnow"));
        item.setCareInst(rs.getString("careInst"));
        item.setCustMaterials(rs.getString("custMaterials"));
        item.setCustBenefit(rs.getString("custBenefit"));
        item.setPicUrls(rs.getString("picUrls"));
        item.setType(rs.getString("type"));
        item.setProductId(rs.getInt("productId"));
        item.setWeight(rs.getFloat("weight"));
        item.setCategory(rs.getString("category"));
        item.setSubCategory(rs.getString("subCategory"));


        return item;
    }
    public void insertItem(final Item item)
    {
        final String sql = new StringBuilder().append("insert into item").append("(pid,name,facts,price,assembledSize,designer,environment,goodToKnow,").append("careInst,")
                .append("custMaterials,custBenefit,picUrls,type,productId,weight,category,subCategory) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)").toString();

        ikeaTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
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
                stmt.setString(12, item.getPicUrlss());
                stmt.setString(13, item.type);
                stmt.setInt(14, item.getProductId());
            stmt.setFloat(15,item.getWeight());
            stmt.setString(16,item.getCategory());
            stmt.setString(17,item.getSubCategory());
                return stmt;
            }
        });


    }
    public int updateItem(final Item item)
    {
        final String sql = new StringBuilder().append("update item ").append("set name=?,facts=?,price=?,assembledSize=?,designer=?,environment=?,goodToKnow=?,careInst=?,")
                .append("custMaterials=?,custBenefit=?,picUrls=?,type=?,weight=?,category=?,subCategory=? where pid= ?").toString();

        int result=ikeaTemplate.update(sql, new Object[] {item.getName(),item.getFacts(),item.getPrice(),item.getAssembledSize(),item.getDesigner(),item.getEnvironment(),item.getGoodToKnow(),item.getCareInst(),item.getCustMaterials(),item.getCustBenefit(),item.getPicUrlss(),item.getType(),item.getWeight(),item.getCategory(),item.getSubCategory(),item.getPid()});
        return result;



    }

    public Item getItemByIid(String id) {
        String sql = "select  * from  item where pid= ? ";
        List<Item> mediaUserScopeList = ikeaTemplate.query(sql, new Object[] { id }, this);
        if (mediaUserScopeList != null && mediaUserScopeList.size() > 0) {
            return mediaUserScopeList.get(0);
        }
        return null;
    }

    public List<Item> getItemByPid(int id) {
        String sql = "select  * from  item where productId= ? ";
        List<Item> mediaUserScopeList = ikeaTemplate.query(sql, new Object[]{id}, this);
        if (mediaUserScopeList != null && mediaUserScopeList.size() > 0) {
            return mediaUserScopeList;
        }
        return null;
    }

    public int check(Item item) {
        String id=item.getPid();
        List<Item> items=new ArrayList<Item>();
        String sql=     "SELECT * FROM item WHERE pid = ?" ;
        try {
            items=ikeaTemplate.query(sql, new Object[] { id }, this);
            if(items.size()>0){
                System.out.println("item【"+id+"】重复");
            }
        } catch (Exception e) {
        }
        return items.size();
    }
}
