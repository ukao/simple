package com.tangpeng.bean;

public class ResultBean extends BaseBean {

	public final static byte SUCCESS=0;
	
	public final static byte FAILED=1;
	
	
	/**
	 */
	@JsonField
	private byte result = FAILED;
	
	@JsonField
	private String desc;
	public byte getResult() {
		return result;
	}
	public void setResult(byte result) {
		this.result = result;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
