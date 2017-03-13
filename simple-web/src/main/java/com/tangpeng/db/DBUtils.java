package com.tangpeng.db;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.tangpeng.servlet.DataBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tangpeng
 *
 */
public class DBUtils 
{
	

	private static final Logger logger = LoggerFactory.getLogger(DBUtils.class);

	private static String url = "jdbc:oralce:thin:@//18.10.100.15:1521/racdbldm";
	private static String user = "soe";
	private static String password = "soe";

	public static String setConnectionInfo(String url,String user,String password){
		DBUtils.url = url;
		DBUtils.user = user;
		DBUtils.password = password;
		Connection con = null;
		try {
			con = getConnection();
		} catch (Exception e) {
			return e.getMessage();
		}finally {
			release(null,null,con);
		}
		return "success";
	}

	public static Connection getConnection() throws Exception {

		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			return DriverManager.getConnection(url , user , password ) ;
		}catch(Exception se){
			throw se;
		}
	}


	/**
	 * @param pst
	 * @param con
	 */
	public static void release(ResultSet rs, Statement pst, Connection con)
	{
		if( rs!= null )
		{
			try 
			{
					rs.close();	
			} 
			catch (SQLException e) 
			{
				logger.warn("Close ResultSet error!",e);
			}
		}
		
		if( pst != null )
		{
			try 
			{
				pst.close();
			} 
			catch (SQLException e) 
			{
				logger.warn("Close Statement error!",e);
			}
		}
		
		if( con != null )
		{
			try 
			{
				con.close();
			} 
			catch (SQLException e) 
			{
				logger.warn("Close Connection error!",e);
			}
		}
	}
}
