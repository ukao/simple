package com.tangpeng.handle;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RequestHandle {

	void doService(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException;
	
}
