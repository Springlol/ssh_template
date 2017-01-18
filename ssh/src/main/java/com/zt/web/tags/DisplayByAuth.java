/**
 *
 */
package com.zt.web.tags;

import com.zt.util.PermissionUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 自定义标签，通过给定参数URL判断用户是否拥有指定权限来确定是否显示标签内容
 */
@SuppressWarnings("serial")
public class DisplayByAuth extends TagSupport {
	private String url;

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
		if (PermissionUtils.hasPerssion(url)) {
			return EVAL_BODY_INCLUDE;
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
		this.url = null;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
