package com.tangpeng.derby;

/**
 * @author tangpeng
 *
 */
public interface InitSql {

	String dropImage = "drop table T_IMAGE";
	
	/**
	 */
	String createImage =
	"create table T_IMAGE(" +
	" id integer  GENERATED BY DEFAULT AS IDENTITY primary key," +
	" path varchar(200) " +
	" ) ";

}