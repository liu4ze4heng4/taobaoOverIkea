import TOI.dao.DaoFactory;
import TOI.model.Product;
import TOI.model.User;
import TOI.util.TaobaoUtils;

/**
 * Created with IntelliJ IDEA.
 * User: W.k
 * Date: 13-5-4
 * Time: 下午8:09
 * To change this template use File | Settings | File Templates.
 */
public class MainGarden {
    public static void main(String[] args) {
////////////////////////更新product表内全部产品/////////////////////////
//        List<String> pids = ProductUtils.setProductListFromSQL();
//        for (String pid:pids){
//        Product p = ProductUtils.grabProductFromIKEA(pid);
//        ProductUtils.addToSQL(p,1);  }
 ////////////////////////更新item表内全部产品库存/////////////////////////
//        List<String> pids = ItemUtils.setItemListFromSQL();
//        for (String pid:pids){
//            IkeaStockUtil.StockInfo2SQL(pid);}
////////////////////////更新product表内一个产品/////////////////////////
//        Product p = ProductUtils.grabProductFromIKEA("10265127");
//        ProductUtils.addToSQL2(p, 1);
//////////////////////从SQL获取一个产品/////////////////////////
//        Product p = ProductUtils.setFromSQL("80243189");
////////////////////////上传至淘宝/////////////////////////
//        TaobaoUtils tu=TaobaoUtils.getInstance("6100b01cb1fd582bbe96e89874da66a27632cf0a3def5cc183118826");
//        User user=tu.getTBUser();
//        DaoFactory.getUserDao(). updateUserByTbName(user);
//        Product p=tu.selectProduct("90195201");
//        tu.addTaobaoItem2(p, DaoFactory.getUserDao().getUserByTbName(user.getTbName()));
///////////////////////////////////////////////////////////
//        TaobaoUtils tu=TaobaoUtils.getInstance("6100b01cb1fd582bbe96e89874da66a27632cf0a3def5cc183118826");
//        tu.getPicCategory("宝贝图片");
///////////////////////////////////////////////////////////////
//       TaobaoUtils.saveTBcategory2File();
//        TaobaoUtils.getPicCategory("宝贝图片");
////////////////////////删除未引用图片/////////////////////////
//        for(int i=0;i<20;i++)
//        TaobaoUtils.deleteUnusePics();
//new////////////////////更新线上宝贝/////////////////////////
        TaobaoUtils tu=TaobaoUtils.getInstance("6102902e2515c567b4728240e466bdd4878d4f5d3b73a6842635718");
        User user=tu.getTBUser();
        DaoFactory.getUserDao(). updateUserByTbName(user);
        Product p=tu.selectProduct("30160964");
        tu.updateTaobaoItem(p, DaoFactory.getUserDao().getUserByTbName(user.getTbName()));
        //////////////////////全部更新线上////////////////////////
//        List<String> pids = ProductUtils.setProductListFromSQL();
//        for (String pid:pids){
//            Product p = ProductUtils.setFromSQL(pid);
//            TaobaoUtils.updateOnlineItem(p);
//        }
////////////////////////获取线上宝贝/////////////////////////
//        TaobaoUtils.getOnlineProducts();

////////////////////////更新SQL内虚重/////////////////////////
//        List<String> pids = ProductUtils.setProductListFromSQL();
//        for (String pid:pids){
//        Product p = ProductUtils.setFromSQL(pid);
//         p.setVirtualWeight2();
//            ProductUtils.addToSQL(p,0);
//           }
    }
}
