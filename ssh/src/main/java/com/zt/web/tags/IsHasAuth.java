/**
 *
 */
package com.zt.web.tags;

import com.zt.util.PermissionUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * 自定义标签，通过给定参数URL判断用户是否拥有指定权限。有则会输出true否则输出false
 */
@SuppressWarnings("serial")
public class IsHasAuth extends TagSupport {
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
		JspWriter out = this.pageContext.getOut();

		try {
			if (PermissionUtils.hasPerssion(url)) {
				out.print(true);
			} else {
				out.print(false);
			}
		} catch (IOException e) {
			e.printStackTrace();
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
