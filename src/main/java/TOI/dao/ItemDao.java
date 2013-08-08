package TOI.dao;

import TOI.model.Item;
import TOI.util.SQLUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

        return item;
    }
    public void insertItem(Item item)
    {
        String sql = new StringBuilder().append("insert into item_charick").append("(pid,name,facts,price,assembledSize,designer,environment,goodToKnow,").append("careInst,")
                .append("custMaterials,custBenefit,picUrls,modifyTime,type) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)").toString();
        try
        {
            PreparedStatement stmt = SQLUtils.getConnection().prepareStatement(sql);
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
            ArrayList PicIds = item.picUrls;
            for (int i = 0; i < PicIds.size(); i++) {
                String picId = (String)PicIds.get(i);
                picUrls.append(picId);
                if (i != PicIds.size() - 1)
                    picUrls.append(",");
            }
            stmt.setString(12, picUrls.toString());
            stmt.setTimestamp(13, SQLUtils.getCurrentDateStr());
            stmt.setString(14, item.type);

            stmt.execute();
            System.out.println(new StringBuilder().append("ITEM:").append(item.pid).append("已添加！").toString());
        }
        catch (SQLException e) {
            System.out.println(new StringBuilder().append("=========SQLException==========").append(e.getMessage()).toString());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println(new StringBuilder().append("========ClassNotFoundException===========").append(e.getMessage()).toString());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(new StringBuilder().append("========Exception===========未知").append(e.getMessage()).toString());
            e.printStackTrace();
        }
    }

    public int check(Item item) {
        String id=item.getPid();
        List<Item> items=new ArrayList<Item>();
        String sql=     "SELECT * FROM item_charick WHERE pid = " + id ;
        try {
            items=ikeaTemplate.query(sql, new Object[] { id }, this);
            if(items.size()>1){
                System.out.println("item【"+id+"】重复");
            }
        } catch (Exception e) {
        }
        return items.size();
    }
}
