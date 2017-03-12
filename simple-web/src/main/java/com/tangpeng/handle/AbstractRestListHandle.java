package com.tangpeng.handle;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tangpeng.bean.BaseBean;
import com.tangpeng.bean.PageBean;
import com.tangpeng.sdk.New;
import com.tangpeng.sdk.Pair;

public abstract class AbstractRestListHandle implements RequestHandle {

    private static final Logger logger = LoggerFactory.getLogger( AbstractRestListHandle.class );

    @Override
    public void doService( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
        PageBean < ? extends BaseBean > page = this.responsePageByJson( req, resp );
        ServletOutputStream out = resp.getOutputStream();
        out.write( page.toJsonString().getBytes( "utf-8" ) );
        out.close();
    }




    protected String decode( String str ) {
        if ( str != null ) {
            return str;
        }
        return null;
    }

    /**
     * @param req
     * @param resp
     * @return
     */
    public abstract PageBean < ? extends BaseBean > responsePageByJson( HttpServletRequest req,
                                                                        HttpServletResponse resp );
}
