package com.tangpeng.bean;

import java.util.List;

import org.codehaus.jettison.json.JSONArray;

/**
 *
 */
public class PageBean<T extends BaseBean>{
	
	private int startRow;
	
	private int endRow;
	
	private int totalRow;
	
	/**
	 */
	private List<T> data;
	
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}



	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}



	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
	}



	public void setData(List<T> data) {
		this.data = data;
	}



	public String toJsonString() 
	{
		return "{response:{startRow:"+this.startRow +",endRow:"+this.endRow+",totalRows:"+
		this.totalRow+","+getExtJson()+"data:"+new JSONArray(this.data).toString()+"}}";
	}
	
	/**
	 * @return xxx:xx,
	 */
	protected String getExtJson()
	{
		return "";
	}
}
