package TOI.Constant;

import TOI.util.commen.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: W.k
 * Date: 13-4-30
 * Time: 上午1:04
 * To change this template use File | Settings | File Templates.
 */
public class Constant {
    public static Properties properties=Properties.getInstant("config.properties");


    public static final String split = "#######";
    public static final String split2 = "@@@@@@@@";
    public static final String picPath = properties.getString("picPath");
    public static final String tb_category_file = "resources/category/tb_category";
    public static String url = "http://gw.api.taobao.com/router/rest";
    public static String appkey = "21402582";
    public static String appSecret = "daf18bbfd74783b446f989f72f07de87";
    public static String nick = "charick";
    public static String sessionKey="6101e179fcef6feeb81d05d331a2bae22805b45d2e558ba42635718";
    public static String vm="productDetail.vm";
    public static String product_sql="product";
    public static String item_sql="item_charick";

//    public static String nick = "zhangtiqiu5673881";
//    public static String sessionKey="6101b06e8d25b4178afad5d83c0855424a602147e1b08bc183118826";
//    public static String vm="zhangtiqiu.vm";
//    public static String product_sql="product_zhangtiqiu";
//    public static String item_sql="item_zhangtiqiu";
}
