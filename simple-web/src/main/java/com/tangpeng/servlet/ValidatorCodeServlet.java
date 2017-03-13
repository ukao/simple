package com.tangpeng.servlet;

import static com.tangpeng.db.WebLogicJndiUtil.ORACLE_JNDI_NAME;
import static com.tangpeng.db.WebLogicJndiUtil.PROVIDER_URL;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.tangpeng.bean.BeanFactory;
import com.tangpeng.db.DBUtils;

import com.tangpeng.db.WebLogicJndiUtil;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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

    private static final Logger logger = LoggerFactory.getLogger(ValidatorCodeServlet.class);
    private static final long serialVersionUID = 1L;
    public static String type = "2";


    @Override
    public void init(ServletConfig config) {
        try {
            WebApplicationContext context = (WebApplicationContext) config.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
            BeanFactory.setContext(context);
            WebLogicJndiUtil.initDataSource(ORACLE_JNDI_NAME,PROVIDER_URL);
        } catch (Exception e) {
            logger.error("init datasource error",e);
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
        String path = "error";
        int loop = 1;
        while (path.equals("error") ) {
            path = queryPathFromDatabase(id);
            if (path.equals("error")) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    logger.error("sleep error", e);
                }
            }
            loop++;
            if(loop > 100){
                return;
            }
        }
        loop =1;
        boolean fileExsit = true;
        while (fileExsit) {
            fileExsit = !this.isImageExsited(path);
            if (fileExsit) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    logger.error("sleep error", e);
                }
            }
            loop++;
            if(loop > 100){
                return;
            }
        }
        pushImage(response, path);

    }

    private String queryPathFromDatabase(String id) throws IOException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String path = null;
        try {
            if( "1".equals(type)) {
                con = WebLogicJndiUtil.getOracleConnection();
            }else{
                con = DBUtils.getConnection();
            }
            ps = con.prepareStatement("select id,path from t_image where id = ?");
            ps.setInt(1, Integer.parseInt(id));
            rs = ps.executeQuery();
            if (rs.next()) {
                path = rs.getString("path");
                logger.info("[File path]=" + path);
                return path;
            }
        } catch (Exception e) {
            logger.error("Database error!", e);
            return "error";
        } finally {
            DBUtils.release(rs, ps, con);
        }
        return "1";
    }

    private void pushImage(HttpServletResponse response, String path) throws IOException {
        ServletOutputStream sos = null;
        try {
            sos = response.getOutputStream();
            BufferedImage buffImg = ImageIO.read(new FileInputStream(path));
            sos = response.getOutputStream();
            ImageIO.write(buffImg, "jpeg", sos);
        } catch (IOException e) {
            logger.error("push error",e);
        } finally {
            if (sos != null) {
                sos.close();
            }
        }
    }

    private boolean isImageExsited(String path) {
        File file = new File(path);
        if (!file.exists()) {
            logger.error("Image:[" + path + "] does not exists. ");
            return false;
        }
        logger.info("[Image load successfully!]path=" + path);
        return true;
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
