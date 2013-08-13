package TOI.dao;

import TOI.model.UserProduct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;

/**
 * Created with IntelliJ IDEA.
 * User: Wk
 * Date: 13-8-11
 * Time: 下午1:42
 * To change this template use File | Settings | File Templates.
 */
    public class UserProductItemDao implements ParameterizedRowMapper<UserProduct> {
        public JdbcTemplate ikeaTemplate;

        public JdbcTemplate getIkeaTemplate() {
            return ikeaTemplate;
        }

        public void setIkeaTemplate(JdbcTemplate ikeaTemplate) {
            this.ikeaTemplate = ikeaTemplate;
        }

        public UserProduct mapRow(ResultSet rs, int i) throws SQLException {
            UserProduct userProduct=new UserProduct();
            userProduct.setUserId(rs.getInt("user_id"));
            userProduct.setPid(rs.getInt("product_id"));
            userProduct.setPics(rs.getString("pics"));
            return userProduct;
        }

        public int insert(final UserProduct userProduct) {
            final String sql = "insert into user_product (user_id,product_id,pics) values (?,?,?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();

            ikeaTemplate.update(new PreparedStatementCreator() {
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, userProduct.getUserId());
                    ps.setInt(2, userProduct.getPid());
                    ps.setString(3, userProduct.getPics());


                    return ps;
                }
            }, keyHolder);
            int generatedId = keyHolder.getKey().intValue();
            return generatedId;
        }




//        public int check(Product product) {
//            String id=product.getPid();
//            List<Product> products=new ArrayList<Product>();
//            String sql=     "SELECT * FROM product WHERE pid = " + id ;
//            try {
//                products=ikeaTemplate.query(sql, new Object[] { id }, this);
//                if(products.size()>1){
//                    System.out.println("item【"+id+"】重复");
//                }
//            } catch (Exception e) {
//            }
//            return products.size();
//        }
//    }

}
