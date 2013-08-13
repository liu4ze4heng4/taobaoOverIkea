package TOI.dao;

import TOI.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Wk
 * Date: 13-8-11
 * Time: 下午11:51
 * To change this template use File | Settings | File Templates.
 */
public class UserDao implements ParameterizedRowMapper<User> {
    public JdbcTemplate ikeaTemplate;

    public JdbcTemplate getIkeaTemplate() {
        return ikeaTemplate;
    }

    public void setIkeaTemplate(JdbcTemplate ikeaTemplate) {
        this.ikeaTemplate = ikeaTemplate;
    }

    @Override
    public User   mapRow(ResultSet rs, int i) throws SQLException {
        User bean = new User();
        bean.setId(rs.getInt("id"));
        bean.setName(rs.getString("name"));
        bean.setRole(rs.getInt("role"));
        bean.setCtime(rs.getTimestamp("ctime"));
        bean.setMtime(rs.getTimestamp("mtime"));
        bean.setTbExpressId(rs.getString("tb_express_id"));
        bean.setTbName(rs.getString("tb_name"));
        bean.setTbPicCategoryId(rs.getString("tb_pic_cid"));
        bean.setTbToken(rs.getString("tb_token"));
        bean.setVM(rs.getString("descriptionVM"));


        return bean;
    }


    public User getUserByTbName(String  tbName) {
        String sql = "select  * from  user where tb_name=? ";
        User user = ikeaTemplate.queryForObject(sql, new Object[] { tbName }, this);
        return user;
    }

    public int updateUserByTbName(User user){
        String sql="update user set tb_token=? where tb_name=?";
        int result=ikeaTemplate.update(sql, new Object[] {user.getTbToken(),user.getTbName()});
        return result;
    }



}
