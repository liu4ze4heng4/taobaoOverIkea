import TOI.model.Product;
import TOI.util.ProductUtils;
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
//        ProductUtils.addToSQL(p);  }
////////////////////////更新product表内全部产品/////////////////////////
        Product p = ProductUtils.grabProductFromIKEA("70176388");
        ProductUtils.addToSQL(p);
////////////////////////////////////////////////////////////////////
//        System.out.println(p.itemsList.get(0).colorCode + "!" + p.setDescription());
        TaobaoUtils.addTaobaoItem(p);
//       TaobaoUtils.saveTBcategory2File();
//        TaobaoUtils.getPicCategory("宝贝图片");
////////////////////////删除未引用图片/////////////////////////
//        for(int i=0;i<20;i++)
//        TaobaoUtils.deleteUnusePics();
//////////////////////更新线上宝贝/////////////////////////

//        TaobaoUtils.updateOnlineItem("S49930925");
////////////////////////获取线上宝贝/////////////////////////
//        TaobaoUtils.getOnlineProducts();
    }


}
