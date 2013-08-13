package TOI.dao;

import TOI.model.Item;
import TOI.model.Product;
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
 * Date: 13-8-10
 * Time: 下午10:21
 * To change this template use File | Settings | File Templates.
 */
public class ProductDao implements ParameterizedRowMapper<Product> {
    public JdbcTemplate ikeaTemplate;

    public JdbcTemplate getIkeaTemplate() {
        return ikeaTemplate;
    }

    public void setIkeaTemplate(JdbcTemplate ikeaTemplate) {
        this.ikeaTemplate = ikeaTemplate;
    }

    public Product mapRow(ResultSet rs, int i) throws SQLException {
Product product=new Product();
        product.setItemIds(rs.getString("items"));
        product.setPid(rs.getInt("id"));

        return product;
    }

    public int insert(final Product product) {
        final String sql = "insert into product (items) values (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        ikeaTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, product.getItemss());


                return ps;
            }
        }, keyHolder);
        int generatedId = keyHolder.getKey().intValue();
        System.out.println(new StringBuilder().append("Product:").append(generatedId).append("已添加！").toString());
        return generatedId;
    }


    public Product getProduct(String id) {
        String sql = "select  * from  product where id= ? ";
        List<Product> mediaUserScopeList = ikeaTemplate.query(sql, new Object[] { id }, this);
        if (mediaUserScopeList != null && mediaUserScopeList.size() > 0) {
            return mediaUserScopeList.get(0);
        }
        return null;
    }

    public int check(Product product) {
        int id=product.getPid();
        List<Product> products=new ArrayList<Product>();
        String sql=     "SELECT * FROM product WHERE id = " + id ;
        try {
            products=ikeaTemplate.query(sql, new Object[] { id }, this);
            if(products.size()>1){
                System.out.println("item【"+id+"】重复");
            }
        } catch (Exception e) {
        }
        return products.size();
    }

    public int updateProduct(final Product p)
    {
        final String sql = "update product set items=?  where id= ?";

        int result=ikeaTemplate.update(sql, new Object[] {p.getItemss(),p.getPid()});
        return result;



    }
}
