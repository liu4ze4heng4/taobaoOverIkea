package TOI.util;

import TOI.Constant.Constant;
import TOI.model.TradeItem;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Trade;
import com.taobao.api.request.TradeGetRequest;
import com.taobao.api.request.TradesSoldGetRequest;
import com.taobao.api.response.TradeGetResponse;
import com.taobao.api.response.TradesSoldGetResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Fahuo {

    public static void main(String[] args) {
        TaobaoUtils taobaoUtil=TaobaoUtils.getInstance("6102224bcf708272d219dbd6f0bf1f941834e157f6f5cec42635718");
        ArrayList<Trade> tradeList = taobaoUtil.tradeFilter("2013-07-24  00:00:00", "2013-08-10  23:59:59");
        List<TradeItem> tradeItems = generateTradeItems(tradeList);
        List<TradeItem> tradeItemList = FahuoPrinter.enrichTradeItem(tradeItems);
        FahuoPrinter.LetsPrint(tradeItemList);

    }

    private static List<String> Memo2TradeItemsList(String memo) {
        List tradeItemInfoList = new ArrayList();

        if (memo == null) {
            System.out.println("空的备忘");
        } else {
            String[] tradeItemInfo = memo.replace(" ", "").split("\\|");
            for (int i = 0; i < tradeItemInfo.length; i++) {
                tradeItemInfoList.add(tradeItemInfo[i]);
            }
        }

        return tradeItemInfoList;
    }

    public static List<TradeItem> tradeItemConvert(List<String> tradeItemInfoList) {
        Map tradeItemMap = new HashMap();
        for (String tradeItemInfo : tradeItemInfoList) {
            if (tradeItemInfo.contains("(")) {
                int beginIx = tradeItemInfo.indexOf("(");
                int endIx = tradeItemInfo.indexOf(")", beginIx);
                String id = tradeItemInfo.substring(beginIx + 1, endIx);
                beginIx = tradeItemInfo.indexOf("*");
                endIx = tradeItemInfo.length();
                int count = Integer.valueOf(tradeItemInfo.substring(beginIx + 1, endIx)).intValue();
                TradeItem temp = null;
                if (tradeItemMap.containsKey(id)) {
                    temp = (TradeItem) tradeItemMap.get(id);
                } else {
                    temp = new TradeItem(id);
                    tradeItemMap.put(id, temp);
                }
                temp.quantity += count;
            }
        }
        List<TradeItem> tradeItems = new ArrayList<TradeItem>();
        tradeItems.addAll(tradeItemMap.values());
        return tradeItems;
    }

    public static List<TradeItem> generateTradeItems(List<Trade> tradeList) {
        List<String> memoList = getMemoList(tradeList);
        List<String> tradeItemInfoList = new ArrayList<String>();
        for (String memo : memoList)
            tradeItemInfoList.addAll(Memo2TradeItemsList(memo));
        List<TradeItem> tradeItems = new ArrayList<TradeItem>();
        if (tradeItemInfoList == null) {
            System.out.println("empty");
        } else {
            tradeItems = tradeItemConvert(tradeItemInfoList);

        }
        return tradeItems;

    }
    private static List<TradeItem> generateTradeItems2(Trade trade){
        String memo=trade.getSellerMemo();
         List<TradeItem> tradeItems = tradeItemConvert(Memo2TradeItemsList(memo));
        return tradeItems;
    }

    private static String getTradeMemo(Long tid) {
        try {
            TaobaoClient client = new DefaultTaobaoClient(Constant.url, Constant.appkey, Constant.appSecret);
            TradeGetRequest req = new TradeGetRequest();
            req.setFields("seller_memo");
            req.setTid(tid);
            TradeGetResponse response = client.execute(req, Constant.sessionKey);

            return response.getTrade().getSellerMemo();
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<String> getMemoList(List<Trade> tradeList) {
        List<String> memoList = new ArrayList<String>();
        for (Trade trade : tradeList) {
//                if (memoMap.containsKey(tradeMap.get(tradeId).getBuyerNick())) {
//                    memoMap.put(tradeMap.get(tradeId).getBuyerNick(), memoMap.get(tradeMap.get(tradeId).getBuyerNick())
//                            + tradeMap.get(tradeId).getSellerMemo());
//                } else {
//                    memoMap.put(tradeMap.get(tradeId).getBuyerNick(), tradeMap.get(tradeId).getSellerMemo());
//                }
            memoList.add(trade.getSellerMemo());
        }

        return memoList;
    }
}