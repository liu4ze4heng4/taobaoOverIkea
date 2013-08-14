package TOI.util;

import TOI.Constant.Constant;
import TOI.dao.DaoFactory;
import TOI.model.Item;
import TOI.model.Product;
import TOI.model.TradeItem;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.tools.generic.MathTool;

import java.io.*;
import java.security.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FahuoPrinter {
    public static List<TradeItem> enrichTradeItem(List<TradeItem> tradeItems) {
        List tradeItemList = new ArrayList();
        for (TradeItem tradeItem : tradeItems) {
            Item item = DaoFactory.getItemDao().getItemByIid(tradeItem.getId());
            if (item != null) {
                tradeItem.setName(item.getName());
                tradeItem.setFacts(item.getFacts());
                tradeItem.setType(item.getType());
                tradeItem.setPrice(item.getPrice());
                tradeItem.setStockInfo(item.getStockType());
            }
//            List stockInfo = new ArrayList();
//            Collections.addAll(stockInfo, IkeaStockUtil.StockInfoCatcher(tradeItem.id).split("#######"));
//            tradeItem.stockInfoCode = Integer.valueOf((String) stockInfo.get(1)).intValue();
//            tradeItem.total = Integer.valueOf(((String) stockInfo.get(0)).replace(",", "")).intValue();
//            tradeItem.stockInfo = ((String) stockInfo.get(2));
            tradeItemList.add(tradeItem);
        }
//        sortTradeItemList(tradeItemList);
        return tradeItemList;
    }

    private static void sortTradeItemList(List<TradeItem> tradeItemList) {
        int i = 0;
        int j = 0;
        for (i = 0; i < tradeItemList.size() - 1; i++) {
            for (j = i + 1; j < tradeItemList.size(); j++) {
                if (((TradeItem) tradeItemList.get(i)).stockInfoCode < ((TradeItem) tradeItemList.get(j)).stockInfoCode) {
                    TradeItem tmp = (TradeItem) tradeItemList.get(i);
                    tradeItemList.set(i, tradeItemList.get(j));
                    tradeItemList.set(j, tmp);
                }
            }

        }

        List flag = new ArrayList();
        for (i = 0; i < tradeItemList.size(); i++) {
            if (((TradeItem) tradeItemList.get(i)).stockInfoCode == 1) {
                flag.add(Integer.valueOf(i));
            }

        }

        int k = 0;
        int l = 0;
        for (k = 0; k < flag.size() - 1; k++) {
            i = ((Integer) flag.get(k)).intValue();
            for (l = k + 1; l < flag.size(); l++) {
                j = ((Integer) flag.get(l)).intValue();
                if (Integer.valueOf(((TradeItem) tradeItemList.get(i)).stockInfo.split("@!@")[0]).intValue() > Integer.valueOf(((TradeItem) tradeItemList.get(j)).stockInfo.split("@!@")[0]).intValue()) {
                    TradeItem tmp = (TradeItem) tradeItemList.get(i);
                    tradeItemList.set(i, tradeItemList.get(j));
                    tradeItemList.set(j, tmp);
                }
            }
        }
    }

    public static void LetsPrint(List<TradeItem> tradeItemList) {
        VelocityContext context = new VelocityContext();
        context.put("tradeItemList", tradeItemList);
        context.put("math", new MathTool());
        String result = VelocityUtil.filterVM("Fahuo.vm", context);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File("\\user\\wk\\清单\\" + System.nanoTime() + ".html"));
            out.write(result.getBytes("UTF-8"));
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}