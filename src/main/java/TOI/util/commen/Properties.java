
package TOI.util.commen;


import org.apache.commons.lang.StringUtils;

import java.io.InputStream;
import java.util.*;
import java.util.Map.Entry;

public class Properties {

    private static Map<String, String> config = new HashMap<String, String>();

    private static Properties          bundle;

    private Properties() {

    }

    public static Properties getInstant(String file) {
        if (bundle == null) {
            bundle = new Properties();
        }
        bundle.init_config(file);
        return bundle;
    }

    private void init_config(String file) {
        java.util.Properties props = new java.util.Properties();
        try {
            InputStream ins = Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
            if (ins != null) {
                props.load(ins);
                ins.close();
                for (Entry<Object, Object> ent : props.entrySet()) {
                    config.put((String) ent.getKey(), (String) ent.getValue());
                }
            }
        } catch (Exception e) {
        }
    }

    public String getProperty(String key) {
        return config.get(key);
    }

    public String getString(String key) {
        return bundle.getProperty(key);
    }

    /**
     * 获取key对应的字符串值，如果为空，返回默认值
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public String getString(String key, String defaultValue) {
        String value = bundle.getProperty(key);
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }
        return value;
    }

    /**
     * 获取key对应的整型值
     *
     * @param key
     * @return
     */
    public int getInt(String key) {
        return getInt(key, 0);
    }

    /**
     * 获取key对应的整型值，如果为空，返回默认值
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public int getInt(String key, int defaultValue) {
        String value = bundle.getProperty(key);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * 获取key对应的长整型值，如果为空，返回默认值
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public long getLong(String key, long defaultValue) {
        String value = bundle.getProperty(key);
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * 获取bool值
     *
     * @param key
     * @return
     */
    public Boolean getBoolean(String key) {
        return Boolean.parseBoolean(bundle.getProperty(key));
    }

    /**
     * 把逗号分隔的值以List方式返回
     *
     * @param key
     * @return
     */
    public List<String> getList(String key) {
        String values = bundle.getProperty(key);
        String[] items = values.split(",");
        List<String> list = new ArrayList();
        for (String item : items) {
            list.add(item);
        }
        return list;
    }

    /**
     * 把逗号分隔的值以Set方式返回，滤重
     *
     * @param key
     * @return
     */
    public HashSet<String> getSet(String key) {
        String values = bundle.getProperty(key);
        String[] items = values.split(",");
        HashSet<String> set = new HashSet<String>();
        for (String item : items) {
            set.add(item);
        }
        return set;
    }
}