package com.zt.entity.dict;


/**
 * 字典实体
 */
public class DictField {

	/**
	 * 属性代号
	 */
	private String code;

	/**
	 * 属性描述
	 */
	private String name;

	/**
	 * 是否可新增项
	 */
	private Boolean canAddItem;

	/**
	 * 属性类型
	 */
	private String type;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getCanAddItem() {
		return canAddItem;
	}

	public void setCanAddItem(Boolean canAddItem) {
		this.canAddItem = canAddItem;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String format() {
		return formatProperties(name, code);
	}

	private String formatProperties(String str1, String str2) {
		return str1 + "(" + str2 + ")";
	}
}