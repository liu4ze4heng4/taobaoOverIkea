package TOI.dao;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class DaoFactory {
	static Resource resource = null;
	static BeanFactory beanFactory = null;

	static {
		init();
	}

	public static synchronized void init() {
		resource = new ClassPathResource("dao-bean.xml");
		beanFactory = new XmlBeanFactory(resource);
	}

	public static SendOrderDao getSendOrderDao() {
		return (SendOrderDao) beanFactory.getBean("sendOrderDao");
	}
	public static ItemDao getItemDao() {
		return (ItemDao) beanFactory.getBean("itemDao");
	}
    public static ProductDao getProductDao() {
		return (ProductDao) beanFactory.getBean("productDao");
	}
    public static UserProductDao getUserProductDao() {
        return (UserProductDao) beanFactory.getBean("userProductDao");
    }
    public static UserDao getUserDao() {
        return (UserDao) beanFactory.getBean("userDao");
    }
}
