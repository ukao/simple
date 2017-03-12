package com.tangpeng.bean;

import java.lang.reflect.Field;

import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author tangpeng
 */
public class BeanFactory {

	private static WebApplicationContext context;

	/**
	 * @param <T>
	 * @param c
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> c) throws Exception {
		String beanName = null;
        try
        {
            Field f = c.getField("ROLE");
            Object value = f.get(c);
            beanName = value.toString();
        }
        catch (Exception e)
        {
            beanName = c.getSimpleName();
        }

        return (T) BeanFactory.getBean(beanName);
	}

	/**
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public static Object getBean(String name) throws Exception {
		Object obj = null;
		try 
		{
			obj = context.getBean(name);
		} 
		catch (Exception e) 
		{
			throw e;
		}
		return obj;
	}

	/**
	 * @param name
	 * @return
	 * @throws Exception 
	 */
	public static Object getBean(String name, Object... obj) throws Exception 
	{
		Object bean = null;
		try 
		{
			bean = context.getBean(name,obj);
		} 
		catch (Exception e) 
		{
			throw e;
		}
		return bean;
	}

	/**
	 * @param context
	 */
	public static void setContext(WebApplicationContext context) {
		BeanFactory.context = context;
	}

	/**
	 * @return
	 */
	public static WebApplicationContext getContext() {
		return context;
	}

}
