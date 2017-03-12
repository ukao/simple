/*
 * $Id: I18nService.java 7596 2012-12-13 08:21:05Z tangpeng $
 * �ļ�����: I18NSerivce.java
 * �ļ�����: ��
 * ��Ȩ����: ��Ȩ����(C)2001-2011
 * ��       ˾: ����������ͨѶ�ɷ����޹�˾
 * ����ժҪ: ��
 * ����˵��: ��
 * ��������: 2011-6-13
 * ��������: $Date:: 2012-12-13 16:21:05 +0800#$:
 * �޸ļ�¼1: // �޸���ʷ��¼�������޸����ڡ��޸��߼��޸�����
 *    �޸����ڣ�
 *    �� �� �ţ�
 *    �� �� �ˣ�
 *    �޸����ݣ�
 * �޸ļ�¼2����
 */
package com.tangpeng.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tangpeng.sdk.New;


/**
 * @author tangpeng
 * @version $Rev: 7596 $
 */
public class I18nService {

    final public static Locale DEFAULT_LOCALE = Locale.CHINA;
    
    private Logger dMsg = LoggerFactory.getLogger(I18nService.class.getName());
    
    final public static String FILE_NAME = "I18n";

    final public static String ROLE = I18nService.class.getSimpleName();
    
    private Map<Locale,ResourceBundle> i18nLabelRes = New.hashMap();
    
    
    
    /**
     */
    public I18nService()
    {
    	initI18nRes();
    }

    /**
     */
    public String getLabel(String key)
    {
    	return this.getLabel(key, DEFAULT_LOCALE);
    }
    
    /**
     * @param key
     * @param locale
     * @return
     */
    public String getLabel(String key, Locale locale)
    {
        ResourceBundle bundle = i18nLabelRes.get(locale);
        if (bundle == null)
        {
            if (locale.getCountry() != null && locale.getCountry().length() > 0)
            {
                Iterator<Entry<Locale, ResourceBundle>> it = i18nLabelRes.entrySet().iterator();
                while (it.hasNext())
                {
                    Entry<Locale, ResourceBundle> entry = (Map.Entry<Locale, ResourceBundle>) it.next();
                    if (locale.getLanguage().equalsIgnoreCase(entry.getKey().getLanguage()))
                    {
                        return entry.getValue().getString(key);
                    }
                }
            }
            bundle = i18nLabelRes.get(DEFAULT_LOCALE);
        }
        try
        {
            return bundle.getString(key);
        }
        catch (MissingResourceException e)
        {
            dMsg.warn(e.getMessage(), e);
            return key;
        }
    }
    
    /**
     */
    private void initI18nRes(){
        Locale[] localeList = new Locale[]{ DEFAULT_LOCALE};
        for(Locale locale:localeList){
            i18nLabelRes.put(locale, getRes(locale));
        }
    }
    
  
    /**
     * @param locale
     * @return
     */
    private ResourceBundle getRes(Locale locale)
    {
        ResourceBundle rb = ResourceBundle.getBundle(FILE_NAME, locale, 
                new ResourceBundle.Control() { 
                    public List<String> getFormats(String baseName) { 
                        if (baseName == null) 
                            throw new NullPointerException(); 
                        return Arrays.asList("xml"); 
                    } 
                    public ResourceBundle newBundle(String baseName, 
                                                    Locale locale, 
                                                    String format, 
                                                    ClassLoader loader, 
                                                    boolean reload) 
                                     throws IllegalAccessException, 
                                            InstantiationException, 
                                            IOException { 
                        if (baseName == null || locale == null 
                              || format == null || loader == null) 
                            throw new NullPointerException(); 
                        ResourceBundle bundle = null; 
                        if (format.equals("xml")) { 
                            String bundleName = toBundleName(baseName, locale); 
                            String resourceName = toResourceName(bundleName, format); 
                            InputStream stream = null; 
                            if (reload) { 
                                URL url = loader.getResource(resourceName); 
                                if (url != null) { 
                                    URLConnection connection = url.openConnection(); 
                                    if (connection != null) { 
                                        // Disable caches to get fresh data for 
                                        // reloading. 
                                        connection.setUseCaches(false); 
                                        stream = connection.getInputStream(); 
                                    } 
                                } 
                            } else { 
                                stream = loader.getResourceAsStream(resourceName); 
                            } 
                            if (stream != null) { 
                                BufferedInputStream bis = new BufferedInputStream(stream); 
                                bundle = new XMLResourceBundle(bis); 
                                bis.close(); 
                            } 
                        } 
                        return bundle; 
                    } 
                });
        return rb; 

    }

    
    /**
     * 
     * xml��Դ�ļ���ж
     * @author tangpang
     * @version $Rev: 7596 $
     */
    private static class XMLResourceBundle extends ResourceBundle {
        private Properties props;

        XMLResourceBundle(InputStream stream) throws IOException
        {
            props = new Properties();
            props.loadFromXML(stream);
        }

        protected Object handleGetObject(String key)
        {
            return props.getProperty(key);
        }

        public Enumeration<String> getKeys()
        {
            Set<String> handleKeys = props.stringPropertyNames();
            return Collections.enumeration(handleKeys);
        }
    }

   
    public static void main(String[] args){
        I18nService s = new I18nService();
        System.out.println(s.getLabel("module.system.management"));
//        System.out.println(s.getTimeDesc(System.currentTimeMillis(), "", Locale.ENGLISH));;
    }
    
}
