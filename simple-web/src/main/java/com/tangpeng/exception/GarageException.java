package com.tangpeng.exception;

/**
 * @author tangpeng
 */
public class GarageException extends Exception {
	
	int code;
	
	/**
	 * 
	 */
	public GarageException(int code,Exception e)
	{
		super(getDesc(code),e);
		this.code = code;
	}

	/**
	 * @param code  ErrorCode#
	 */
	public GarageException(int code)
	{
		super(getDesc(code));
		this.code = code;
	}
	
	/**
	 * @return
	 */
	public String getDisplayDesc()
	{
		return getDesc(code);
	}
	
	private static String getDesc(int code)
	{
		return "";
	}
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -364545370296439168L;

}
