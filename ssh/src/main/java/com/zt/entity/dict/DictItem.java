package com.zt.entity.dict;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 字典属性值实体
 */
@Entity
@Table(name = "sys_dict")
public class DictItem implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@Id
	@Column(nullable = false, length = 32, unique = true)
	private String id;

	/**
	 * 属性代号
	 */
	@Column(length = 32)
	private String fieldCode;

	/**
	 * 属性代号
	 */
	@Column(length = 50)
	private String itemCode;

	/**
	 * 属性名
	 */
	@Column(length = 50)
	private String itemName;

	/**
	 * 是否系统预置。1：是、0：否
	 */
	@Column
	private Boolean isSystem;

	/**
	 * 更新属性
	 * 将新对象的属性设置到自己里
	 *
	 * @param newItem
	 */
	public void update(DictItem newItem) {
		this.setId(newItem.getId());
		this.setItemName(newItem.getItemName());
		if (!this.getIsSystem()) {//只有非系统默认的数据项才能对其code进行更新
			this.setItemCode(newItem.getItemCode());
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFieldCode() {
		return fieldCode;
	}

	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Boolean getIsSystem() {
		return isSystem;
	}

	public void setIsSystem(Boolean isSystem) {
		this.isSystem = isSystem;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
}