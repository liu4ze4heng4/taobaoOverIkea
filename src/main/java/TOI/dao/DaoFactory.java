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
		resource = new ClassPathResource("../dao-bean.xml");
		beanFactory = new XmlBeanFactory(resource);
	}

	public static SendOrderDao getSendOrderDao() {
		return (SendOrderDao) beanFactory.getBean("sendOrderDao");
	}
	public static ItemDao getItemDao() {
		return (ItemDao) beanFactory.getBean("itemDao");
	}
}
