package com.tangpeng.servlet;

import com.tangpeng.db.DBUtils;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tangpeng.db.WebLogicJndiUtil;

public class BusinessServlet extends HttpServlet {

    private static final long serialVersionUID = -7906985049640504566L;
    private static final Logger logger = LoggerFactory.getLogger(BusinessServlet.class);


    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }


    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if( "1".equals(req.getParameter("type"))) {
            String jndi = req.getParameter("jndi");
            String url = req.getParameter("url");
            ValidatorCodeServlet.type = "1";
            resp.getWriter().write(WebLogicJndiUtil.initDataSource(jndi, url));
        }else{
            String url = req.getParameter("url");
            String user = req.getParameter("user");
            String password = req.getParameter("name");
            ValidatorCodeServlet.type = "2";
            resp.getWriter().write(DBUtils.setConnectionInfo(url,user,password));
        }
    }

}
