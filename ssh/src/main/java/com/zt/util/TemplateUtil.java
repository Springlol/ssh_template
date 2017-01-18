package com.zt.util;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

public class TemplateUtil {
	static Properties velocityProperties;

	static {
		velocityProperties = new Properties();
		velocityProperties.put("resource.loader", "class");
		velocityProperties.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		velocityProperties.put("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.SimpleLog4JLogSystem");
		velocityProperties.put("runtime.log.logsystem.log4j.category", "velocity_log");
	}

	/**
	 * 替换模板文件中的内容
	 *
	 * @param templateLocation（模板文件路径）
	 * @param model                    要替换的内容
	 * @return 替换后的字符串
	 */
	public static String getTemplateText(String templateLocation, Map<String, Object> model) {
		VelocityEngine velocityEngine = new VelocityEngine(velocityProperties);
		String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateLocation, "utf8", model);
		return text;
	}

	/**
	 * 替换模板中的内容
	 *
	 * @param map     map中的key 要和替换内容 content 中 ${}里面的数据一致
	 * @param content 要替换的字符串
	 * @return 替换后的字符串
	 */
	public static String getRemindText(Map<String, Object> map, String content) {
		VelocityEngine ve = new VelocityEngine(velocityProperties);
		ve.init();
		// 取得velocity的上下文context
		VelocityContext context = new VelocityContext();
		// 把数据填入上下文
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			context.put(entry.getKey(), entry.getValue());
		}
		// 输出流
		StringWriter writer = new StringWriter();
		// 转换输出
		ve.evaluate(context, writer, "", content); // 关键方法
		return writer.toString();
	}
}
