package TOI.util;

import TOI.Constant.Constant;
import TOI.model.Product;
import TOI.model.User;
import TOI.model.UserProduct;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.generic.MathTool;

import java.io.StringWriter;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: W.k
 * Date: 13-5-1
 * Time: 下午11:31
 * To change this template use File | Settings | File Templates.
 */
public class VelocityUtil {
    private static VelocityEngine engine;

    public static String filterVM(String vmFile, VelocityContext context) {
        String result = null;
        VelocityEngine engine = getEngine();
        try {
            Template template = engine.getTemplate(vmFile);
            StringWriter writer = new StringWriter();
            template.merge(context, writer); /* 显示结果 */
            result = writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static VelocityEngine getEngine() {
        if (engine == null) {
            engine = new VelocityEngine();
            Properties p = new Properties();
            p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, "velocity");
            p.setProperty(Velocity.INPUT_ENCODING, "utf-8");
            p.setProperty(Velocity.OUTPUT_ENCODING, "utf-8");
            try {
                engine.init(p);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return engine;
    }
    public static String generateDescription(Product product,User user)
    {
        VelocityContext context = new VelocityContext();
        context.put("itemList", product.getItemObjs());

        context.put("math", new MathTool());
        String description = VelocityUtil.filterVM(user.getVM(), context);
        return description;
    }
}
