package com.tangpeng.handle;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tangpeng.bean.ResultBean;
import com.tangpeng.exception.ErrorCode;
import com.tangpeng.exception.GarageException;
import com.tangpeng.sdk.New;
import com.tangpeng.sdk.Triple;

public abstract class AbstractRestResultHandle implements RequestHandle {

	private static final Logger logger = LoggerFactory.getLogger(AbstractRestResultHandle.class);
	
	@Override
	public void doService(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ResultBean result = this.responsePageByJson(req, resp);
		if( result != null )
		{
			ServletOutputStream out = resp.getOutputStream();
			String str = "{\"response\":{\"result\":\"" + result.getResult()
					+ "\",\"desc\":\"" + result.getDesc().replaceAll("\"", "\\\"")
					+ "\"}}";
			// ������write byte�ķ�ʽ
			out.write(str.getBytes("utf-8"));
			out.close();
		}
	}

	/**
	 * @throws Exception
	 * @throws InstantiationException
	 */
	protected <T> T createBaseBean(HttpServletRequest request,
			Class<T> cs) throws GarageException {
		T t = null;
		try {
			t = cs.newInstance();
			Method[] methods = cs.getMethods();
			JSONObject jsonObject = new JSONObject(getPostParameter(request));
			@SuppressWarnings("rawtypes")
			Iterator i = jsonObject.keys();
			while (i.hasNext()) {
				String s = (String) i.next();
				for (Method method : methods) {
					if (method.getName().equalsIgnoreCase("set" + s)) {
						Class<?> paraType = method.getParameterTypes()[0];
						if( String.class.equals( paraType ) )
						{
							method.invoke(t, jsonObject.getString(s));	
						}
						else if(int.class.equals(paraType))
						{
							method.invoke(t, jsonObject.getInt(s));
						}
					}
				}
			}
		}
		catch (Exception e) 
		{
			logger.warn(e.getMessage(),e);
			throw new GarageException(ErrorCode.WEB_BEAN_TRANSFORM_ERROR,e);
		}
		return t;
	}
	
	/**
	 * 
	 * @param <T>
	 * @param request
	 * @param cs
	 * @return
	 * @throws GarageException
	 */
	protected <T> T createForm(HttpServletRequest request,
			Class<T> cs) throws GarageException {
		T t = null;
		try {
			t = cs.newInstance();
			Method[] methods = cs.getMethods();
			String contents = getPostParameter(request);
			String[] fields = contents.split("&");
			
			for(String field:fields ) 
			{
				String[] keyValue = field.split("=");
				for (Method method : methods) 
				{
					if (method.getName().equalsIgnoreCase("set" + keyValue[0])) {
						method.invoke(t, keyValue[1]);
					}
				}
			}
		}
		catch (Exception e) 
		{
			logger.warn(e.getMessage(),e);
			throw new GarageException(ErrorCode.WEB_BEAN_TRANSFORM_ERROR,e);
		}
		return t;
	}

	/**
	 * ��ȡ����
	 * @param request
	 * @return
	 * @throws IOException
	 */
	protected String getPostParameter(HttpServletRequest request)
			throws IOException {
		BufferedInputStream buf = null;
		int iContentLen = request.getContentLength();
		byte sContent[] = new byte[iContentLen];
		String sContent2 = null;
		try {
			buf = new BufferedInputStream(request.getInputStream());
			buf.read(sContent, 0, sContent.length);
			sContent2 = new String(sContent, 0, iContentLen, "UTF-8");
			// ��ȡ���ַ���Ҫ����
			sContent2 = URLDecoder.decode(sContent2, "UTF-8");
		}
		catch (IOException e) 
		{
			throw new IOException("Parse data error!", e);
		} 
		finally 
		{
			try 
			{
				buf.close();
			} 
			catch (IOException e) 
			{

			}
		}
		return sContent2;
	}
	
	/**
	 * �ϴ��ļ�������id��relativePath
	 * @param request
	 * @param rootPath
	 * @return id,fileName,orginal_fileName
	 * @throws Exception 
	 */
	protected Triple<String,String,String> uploadFile(HttpServletRequest request,String rootPath) throws Exception
    {	
		
        InputStream input = null;
        //���Ŀ¼
        //����ļ�
        File file = null;
        String id = null;
        String fileName = null;
        String orginalFileName= null;
        //�ܵ�
        FileOutputStream ouput = null;

        byte[] b = null;
        //servlet�ļ��ϴ�����
        ServletFileUpload sfu = new ServletFileUpload(new DiskFileItemFactory());
        sfu.setHeaderEncoding("utf-8");
        try
        {
            //��ȡ�ļ�·��
            @SuppressWarnings("unchecked")
            List<FileItem> items = sfu.parseRequest(request);
            for (FileItem item : items)
            {
            	//
                if( item.isFormField() && item.getFieldName().equals("id") )
                {
                    id = item.getString();
                }
            }
            
            
            
            for (FileItem item : items) 
            {
            	if( item.getFieldName().equals("upload") )
            	{
            		orginalFileName = item.getName();
            		fileName = getFileName(id,""+System.currentTimeMillis() );
            		File dir = new File(rootPath);
            		if( !dir.exists() )
            		{
            			dir.mkdirs();
            		}
                    file = new File( rootPath + fileName);
                    b = new byte[request.getContentLength()];
                    input = item.getInputStream();
                    ouput = new FileOutputStream(file);
                    input.read(b);
                    ouput.write(b);
                    break;
            	}
			}
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            try
            {
                if (input != null)
                {
                    input.close();
                }
                if (ouput != null)
                {
                    ouput.close();
                }
            }
            catch (IOException e)
            {
//                dMsg.info("Close template stream error", e);
            }
            //��Ϊnull������������
            b = null;
        }
        return New.triple(id,fileName,orginalFileName);
    }
	
	/**
	 * �ɸ��ǻ�ȡ�ļ���
	 * @return
	 */
	protected String getFileName(String id,String oName)
	{
		return id+"-"+oName;
	}
	
	/**
	 * ��ȡ�ļ�����׺
	 * @param fileName
	 * @return
	 */
	@SuppressWarnings("unused")
	private String getFileNameSuffix(String fileName)
	{
		int suffixIndex = fileName.lastIndexOf('.');
		return fileName.substring(suffixIndex);
	}

	/**
	 * ��Ӧ
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public abstract ResultBean responsePageByJson(HttpServletRequest req,
			HttpServletResponse resp);
}
