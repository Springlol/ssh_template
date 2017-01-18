/**
 *
 */
package com.zt.web.tags;


import com.zt.entity.user.User;
import com.zt.util.WebUtil;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 自定义标签，通过给定参数URL判断用户是否拥有指定权限来确定是否显示标签内容
 */
@SuppressWarnings("serial")
public class DisplayByRole extends TagSupport {
	private String roleNosStr;

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
	 */
	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		// TODO Auto-generated method stub
		User loginUser = WebUtil.getLoginUser();
		if (loginUser == null) {
			return SKIP_BODY;
		}

		String[] roleNos = roleNosStr.split(",");

		for (String roleNo : roleNos) {
		   /* if(roleNo.equals(loginUser.getRole().getNo())) {
				return EVAL_BODY_INCLUDE;
            }*/
		}

		return SKIP_BODY;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.servlet.jsp.tagext.TagSupport#release()
	 */
	@Override
	public void release() {
		super.release();
		this.roleNosStr = null;
	}

	public String getRoleNosStr() {
		return roleNosStr;
	}

	public void setRoleNosStr(String roleNosStr) {
		this.roleNosStr = roleNosStr;
	}

}
