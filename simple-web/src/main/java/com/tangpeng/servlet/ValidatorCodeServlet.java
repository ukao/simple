package com.tangpeng.servlet;

import com.tangpeng.bean.BeanFactory;
import com.tangpeng.db.DBUtils;
import com.tangpeng.handle.AbstractRestListHandle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;

/**
 */

public class ValidatorCodeServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger( ValidatorCodeServlet.class );
    private static final long serialVersionUID = 1L;
    DataSource ds = null;

    @Override
    public void init(ServletConfig config){
        try {
            WebApplicationContext context = (WebApplicationContext) config.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
            BeanFactory.setContext(context);
            ds = (DataSource) BeanFactory.getBean("DataSource");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");
        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String path = null;
        ServletOutputStream sos = null;
        try {
            con = ds.getConnection();
            ps = con.prepareStatement("select id,path from t_image where id = ?");
            ps.setInt(1,Integer.parseInt(id));
            rs = ps.executeQuery();
            if(rs.next()){
                path = rs.getString("path");
            }
            File file = new File(path);
            if(!file.exists()){
                logger.error("Image:["+path+"] does not exists. ");
                return ;
            }
            BufferedImage buffImg = ImageIO.read(new FileInputStream(path));
            sos = response.getOutputStream();
            ImageIO.write(buffImg, "jpeg", sos);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            DBUtils.release(rs,ps,con);
            if( sos !=null)
                sos.close();
        }

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
