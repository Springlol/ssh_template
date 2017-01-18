/**
 *
 */
package com.zt.web.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.zt.util.WebUtil;

import javax.servlet.http.HttpServletResponse;

/**
 * @author zxl
 */
@SuppressWarnings("serial")
public class CacheInterceptor extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletResponse response = WebUtil.getResponse();
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		return invocation.invoke();
	}

}
