package com.tangpeng.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tangpeng
 *
 */
public class DBUtils 
{
	

	private static final Logger logger = LoggerFactory.getLogger(DBUtils.class);

	
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
