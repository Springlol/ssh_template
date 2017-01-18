package com.zt.web.actions;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;

import java.io.IOException;

/**
 * Created by zhoutao on 2017/1/17.
 */
@Namespace("/")
public class TestAction extends BaseAction {
	@Action("test")
	public void test() {
		try {
			getResponse().getWriter().write("hello");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
