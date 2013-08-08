package TOI.util;

import TOI.dao.DaoFactory;
import TOI.model.SendOrder;
import TOI.model.User;
import com.taobao.api.domain.Trade;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Wk
 * Date: 13-8-5
 * Time: 下午12:02
 * To change this template use File | Settings | File Templates.
 */
public class SendOrderUtils {
    public static SendOrder generateSenderOrder(Trade trade){
        SendOrder senderOrder = new SendOrder();
        System.out.println(trade.getTid());
        senderOrder.setTid(trade.getTid().toString());
senderOrder.setPayTime(trade.getPayTime());
        senderOrder.setBuyerNIck(trade.getBuyerNick());
        senderOrder.setReceiverName(trade.getReceiverName());
        senderOrder.setRecerverState(trade.getReceiverState());
        senderOrder.setReceiverCity(trade.getReceiverCity());
        senderOrder.setReceiverAddress(trade.getReceiverAddress());
        senderOrder.setReceiverMobile(trade.getReceiverMobile());
        senderOrder.setReceiverPhone(trade.getReceiverPhone());
        senderOrder.setSellerFlag(trade.getSellerFlag().intValue());
        senderOrder.setSellerMemo(trade.getSellerMemo());
     return  senderOrder;

    }

    public static void main(String[] args){
        TaobaoUtils taobaoUtils=new TaobaoUtils("6102224bcf708272d219dbd6f0bf1f941834e157f6f5cec42635718");
        List<Trade> tradeList= taobaoUtils.tradeFilter("2013-07-30 00:00:00","2013-08-10 00:00:00");
        for(Trade trade:tradeList)
        {
            DaoFactory.getSendOrderDao().insert(SendOrderUtils.generateSenderOrder(trade));

        }



    }
}
