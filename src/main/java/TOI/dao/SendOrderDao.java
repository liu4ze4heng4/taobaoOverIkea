package TOI.dao;

import TOI.model.SendOrder;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SendOrderDao implements ParameterizedRowMapper<SendOrder> {
	public JdbcTemplate ikeaTemplate;

	public JdbcTemplate getIkeaTemplate() {
		return ikeaTemplate;
	}

	public void setIkeaTemplate(JdbcTemplate ikeaTemplate) {
		this.ikeaTemplate = ikeaTemplate;
	}

	@Override
	public SendOrder mapRow(ResultSet rs, int i) throws SQLException {
		SendOrder bean = new SendOrder();
		bean.setId(rs.getInt("id"));
		bean.setTid(rs.getString("tid"));
		bean.setBuyerNIck(rs.getString("buyer_nick"));
		bean.setPayTime(rs.getTimestamp("pay_time"));
		bean.setSellerMemo(rs.getString("trade_memo"));
        bean.setExpressId(rs.getString("express_id"));
		bean.setExpressNum(rs.getString("express_number"));
		bean.setReceiverMobile(rs.getString("receiver_mobile"));
        bean.setReceiverName(rs.getString("receiver_name"));
        bean.setReceiverAddress(rs.getString("receiver_address"));
        bean.setRecerverState(rs.getString("receiver_state"));
        bean.setReceiverCity(rs.getString("receiver_city"));
        bean.setExpressNum(rs.getString("express_number"));
        bean.setStatus(rs.getInt("status"));
		return bean;
	}

    public  void updateExpressCode(int id, String code,int status){
        String sql = "update  user_sendorder set express_number=? , status=? where id=? ";
        ikeaTemplate.update(sql, code, status,id);
    }


	public List<SendOrder> getSendOrderByStatus(int status) {
		String sql = "select  * from  user_sendorder where status=? ";
		List<SendOrder> mediaUserScopeList = ikeaTemplate.query(sql, new Object[] { status }, this);
		if (mediaUserScopeList != null && mediaUserScopeList.size() > 0) {
			return mediaUserScopeList;
		}
		return null;
	}
    public List<SendOrder> getSendOrderByFlag(int status) {
        String sql = "select  * from  user_sendorder where seller_flag= ? ";
        List<SendOrder> mediaUserScopeList = ikeaTemplate.query(sql, new Object[] { status }, this);
        if (mediaUserScopeList != null && mediaUserScopeList.size() > 0) {
            return mediaUserScopeList;
        }
        return null;
    }
    public SendOrder getSendOrderById(int id) {
        String sql = "select  * from  user_sendorder where id= ? ";
        List<SendOrder> mediaUserScopeList = ikeaTemplate.query(sql, new Object[] { id }, this);
        if (mediaUserScopeList != null && mediaUserScopeList.size() > 0) {
            return mediaUserScopeList.get(0);
        }
        return null;
    }

	public int insert(final SendOrder trade) {
		final String sql = "insert into user_sendorder (tid,pay_time,buyer_nick,receiver_name, receiver_state, receiver_city, receiver_address, receiver_mobile, receiver_phone,seller_flag,trade_memo,status) values (?,?,?,?,?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();

		ikeaTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, trade.getTid() + "");
				ps.setTimestamp(2, new Timestamp(trade.getPayTime().getTime()));
                ps.setString(3, trade.getBuyerNIck());
                ps.setString(4, trade.getReceiverName());
                ps.setString(5, trade.getRecerverState());
                ps.setString(6, trade.getReceiverCity());
                ps.setString(7, trade.getReceiverAddress());
                ps.setString(8, trade.getReceiverMobile());
                ps.setString(9, trade.getReceiverPhone());
                ps.setInt(10, trade.getSellerFlag());
                ps.setString(11, trade.getSellerMemo());
                ps.setInt(12, 1);

                return ps;
			}
		}, keyHolder);

		int generatedId = keyHolder.getKey().intValue();
		return generatedId;
	}

	public int find(String tid) {
		int id = 0;
		try {
			id = ikeaTemplate.queryForInt("SELECT id FROM user_sendorder WHERE tid = " + tid);
		} catch (Exception e) {
		}
		return id > 0 ? id : 0;
	}

    public   List<SendOrder> searchSendOrder(int status, int expressId, String keyWord, String date,String endDate){
        String sql= "select * from user_sendorder ";

        StringBuilder whereProperty=new StringBuilder();
        boolean has=false;
        if(status>0){ whereProperty.append(" status="+status); has=true;}
        if(expressId>0)  { whereProperty.append(has?" and ":" ").append(" express_id="+expressId); has=true;}
        if(StringUtils.isNotBlank(keyWord)){
            whereProperty.append(has?" and ":" ").append(" trade_memo like '%"+keyWord+"%' ");
            has=true;
        }
        whereProperty.append(has?" and ":" ").append(" pay_time>'"+date +"' and pay_time<'"+endDate+"'");
        if(StringUtils.isNotBlank(whereProperty.toString())){
            sql=sql+" where "+whereProperty.toString() ;
        }
        List<SendOrder> mediaUserScopeList = ikeaTemplate.query(sql, this);
        return mediaUserScopeList;
    }
}
