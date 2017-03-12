package com.tangpeng.derby;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author
 *
 */
public class InitDerby {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		String url = "jdbc:derby://127.0.0.1:1527/hjj;create=true;user=app;password=app";
		Connection con =  null;
		Statement stm = null;
		try 
		{
			Class.forName("org.apache.derby.jdbc.ClientDriver");
			con = DriverManager.getConnection(url);
			stm = con.createStatement();
//			stm.execute(InitSql.dropImage);
			stm.execute(InitSql.createImage);
			stm.execute("insert into t_image(path) values('/Users/tangpeng/Documents/PICTURE/旺德福小绘本/9.23头发乱了.JPG')");
			stm.execute("insert into t_image(path) values('/Users/tangpeng/Documents/PICTURE/旺德福小绘本/9.25拓本.JPG')");
			stm.execute("insert into t_image(path) values('/Users/tangpeng/Documents/PICTURE/旺德福小绘本/Cover.jpg')");
			stm.execute("insert into t_image(path) values('D:/1/1.gif')");
//			stm.execute("insert into t_image(path) values('D:/1/4.png')");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				stm.close();
				con.close();
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}
}
