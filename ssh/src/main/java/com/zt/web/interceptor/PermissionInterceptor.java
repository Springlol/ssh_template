package com.zt.web.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.zt.entity.user.User;
import com.zt.util.PermissionUtils;
import com.zt.util.WebUtil;
import org.apache.log4j.Logger;

/**
 * struts2 拦截器，用于拦截用户请求的action权限验证。
 */
public class PermissionInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 0L;
	private static Logger logger = Logger.getLogger(PermissionInterceptor.class);

	/**
	 * 权限拦截器
	 */
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		String path = getServletPath();
		User loginUser = WebUtil.getLoginUser();
		if (loginUser == null) {
			logger.info("未登录。ip:" + WebUtil.getRemoteIp() + " 目标资源:" + path);
			return "/";
		}

		//校验权限
		if (!PermissionUtils.hasPerssion(path)) {
			logger.info("无权限," + loginUser.toString() + ", path: " + path + " ip:" + WebUtil.getRemoteIp());
			return "noPermission";
		}
		return this.doInvoke(actionInvocation);
	}

	/**
	 * 获取url需要校验的部分
	 *
	 * @return
	 */
	private String getServletPath() {
		return WebUtil.getRequest().getServletPath();
	}

	/**
	 * 继续拦截器链
	 *
	 * @param actionInvocation
	 * @return
	 * @throws Exception
	 */
	private String doInvoke(ActionInvocation actionInvocation) throws Exception {
		return actionInvocation.invoke();
	}

}
