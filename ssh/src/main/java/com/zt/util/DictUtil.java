package com.zt.util;

import com.zt.entity.dict.DictField;
import com.zt.entity.dict.DictItem;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.BufferedInputStream;
import java.util.*;

public class DictUtil {
	private static Element dictRootElement;

	private static List<Map<String, String>> fieldTypes;
	private static Map<String, List<DictField>> typeFields;
	private static List<String> fieldCodes;
	private static Map<String, List<DictItem>> fieldItems;

	static {
		try {
			String dictFile = "dict.xml";
			SAXReader xmlReader = new SAXReader();
			Document dictDocument = xmlReader.read(new BufferedInputStream(
					Thread.currentThread().getContextClassLoader()
							.getResourceAsStream(dictFile)));
			dictRootElement = dictDocument.getRootElement();
			loadDictData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加数据项
	 *
	 * @param item
	 */
	public static void insertItem(DictItem item) {
		List<DictItem> items = fieldItems.get(item.getFieldCode());
		if (items == null) {
			return;
		}
		items.add(item);
	}

	/**
	 * 获取属性
	 *
	 * @param fieldCode
	 * @return
	 */
	public static DictField getField(String fieldCode) {
		for (List<DictField> fields : typeFields.values()) {
			for (DictField dictField : fields) {
				if (dictField.getCode().equals(fieldCode)) {
					return dictField;
				}
			}
		}
		return null;
	}

	/**
	 * 获取项
	 *
	 * @param fieldCode
	 * @param itemCode
	 * @return
	 */
	public static DictItem getItem(String fieldCode, String itemCode) {
		List<DictItem> items = fieldItems.get(fieldCode);
		if (items == null) {
			return null;
		}
		for (int i = 0; i < items.size(); i++) {
			DictItem item = items.get(i);
			if (item.getItemCode().equals(itemCode)) {
				return item;
			}
		}
		return null;
	}

	/**
	 * 更新数据项
	 *
	 * @param newItem
	 * @param oldItemCode
	 */
	public static void updateItem(DictItem newItem, String oldItemCode) {
		DictItem oldItem = getItem(newItem.getFieldCode(), oldItemCode);
		if (oldItem == null) {
			return;
		}
		oldItem.update(newItem);
	}

	/**
	 * 是否存在指定的项
	 *
	 * @param fieldCode
	 * @param itemCode
	 * @return
	 */
	public static boolean containsItem(String fieldCode, String itemCode) {
		if (getItem(fieldCode, itemCode) != null) {
			return true;
		}
		return false;
	}

	/**
	 * 删除数据项
	 *
	 * @param fieldCode
	 * @param itemCode
	 */
	public static void deleteItem(String fieldCode, String itemCode) {
		DictItem item = getItem(fieldCode, itemCode);
		if (item == null) {
			return;
		}
		if (item.getIsSystem() == null || item.getIsSystem()) {
			return;
		}
		List<DictItem> items = fieldItems.get(fieldCode);
		items.remove(item);
	}

	/**
	 * 恢复某属性为默认值
	 *
	 * @param fieldCode
	 */
	public static void defaultField(String fieldCode) {
		fieldItems.remove(fieldCode);
		fieldItems.put(fieldCode, getDefaultItemsByFieldCode(fieldCode));
	}

	/**
	 * 通过属性代号获取属性名
	 *
	 * @param fieldCode
	 * @param itemCode
	 * @return
	 */
	public static String getItemName(String fieldCode, String itemCode) {
		DictItem item = getItem(fieldCode, itemCode);
		if (item == null) {
			return "";
		}
		return item.getItemName();
	}

	/**
	 * 获取属性列表
	 *
	 * @param fieldCode
	 * @return
	 */
	public static List<DictItem> getItemsByFieldCode(String fieldCode) {
		List<DictItem> items = fieldItems.get(fieldCode);
		if (items == null) {
			items = new ArrayList<DictItem>();
		}
		return items;
	}

	/**
	 * 获取属性类型列表
	 *
	 * @return
	 */
	public static List<Map<String, String>> getFieldTypes() {
		return fieldTypes;
	}

	/**
	 * 根据类型获取属性列表
	 *
	 * @param type
	 * @return
	 */
	public static List<DictField> getFieldsByType(String type) {
		if (StringUtils.isBlank(type)) {
			return new ArrayList<DictField>();
		}
		return typeFields.get(type);
	}

	/**
	 * 初始化字典（加载数据）
	 *
	 * @throws Exception
	 */
	private static void loadDictData() throws Exception {
		loadFieldTypes();
		loadTypeFields();
		loadFieldItems();
	}

	/**
	 * 加载属性类型
	 */
	@SuppressWarnings("unchecked")
	private static void loadFieldTypes() {
		fieldTypes = new ArrayList<Map<String, String>>();
		Element fieldTypesElement = dictRootElement.element("fieldTypes");

		// 遍历属性类型
		Iterator<Element> fieldTypeIterator = fieldTypesElement
				.elementIterator();
		while (fieldTypeIterator.hasNext()) {
			Element fieldTypeElement = fieldTypeIterator.next();
			Map<String, String> fieldType = new HashMap<String, String>();
			fieldType.put("code", fieldTypeElement.attributeValue("code"));
			fieldType.put("name", fieldTypeElement.attributeValue("name"));
			fieldTypes.add(fieldType);
		}
	}

	/**
	 * 加载类型属性列表
	 */
	@SuppressWarnings("unchecked")
	private static void loadTypeFields() {
		fieldCodes = new ArrayList<String>();
		typeFields = new HashMap<String, List<DictField>>();
		// 遍历属性
		Iterator<Element> fieldsIterator = dictRootElement
				.elementIterator("fields");
		while (fieldsIterator.hasNext()) {
			Element fieldsElement = fieldsIterator.next();
			String fieldsType = fieldsElement.attributeValue("type");
			// 遍历各类属性
			Iterator<Element> fieldIterator = fieldsElement.elementIterator();
			List<DictField> defaultFields = new ArrayList<DictField>();
			while (fieldIterator.hasNext()) {
				Element fieldElement = fieldIterator.next();
				DictField dictField = new DictField();
				dictField.setCode(fieldElement.attributeValue("code"));
				dictField.setName(fieldElement.attributeValue("name"));
				dictField.setType(fieldsType);
				String canAddItem = fieldElement.attributeValue("canAddItem");
				if (canAddItem == null || canAddItem.equals("true")) {
					dictField.setCanAddItem(true);
				} else {
					dictField.setCanAddItem(false);
				}
				fieldCodes.add(dictField.getCode());
				defaultFields.add(dictField);
			}
			typeFields.put(fieldsType, defaultFields);
		}
	}

	/**
	 * 获取系统默认的属性项列表
	 *
	 * @param fieldCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<DictItem> getDefaultItemsByFieldCode(String fieldCode) {
		List<DictItem> items = new ArrayList<DictItem>();
		// 遍历属性
		Iterator<Element> fieldsIterator = dictRootElement
				.elementIterator("fields");
		while (fieldsIterator.hasNext()) {
			Element fieldsElement = fieldsIterator.next();
			// 遍历各类属性
			Iterator<Element> fieldIterator = fieldsElement.elementIterator();
			while (fieldIterator.hasNext()) {
				Element fieldElement = fieldIterator.next();
				if (fieldElement.attributeValue("code").equals(fieldCode)) {
					// 遍历属性的数据项
					Iterator<Element> itemIterator = fieldElement
							.elementIterator();
					while (itemIterator.hasNext()) {
						Element itemElement = itemIterator.next();
						DictItem dictItem = new DictItem();
						dictItem.setFieldCode(fieldCode);
						dictItem.setIsSystem(true);
						dictItem.setItemCode(itemElement.attributeValue("code"));
						dictItem.setItemName(itemElement.attributeValue("name"));
						items.add(dictItem);
					}
					return items;
				}
			}
		}
		return items;
	}

	/**
	 * 加载用户自定义内容
	 */
	private static void loadFieldItems() throws Exception {
		fieldItems = new HashMap<String, List<DictItem>>();
		for (String fieldCode : fieldCodes) {
			List<DictItem> items = getDefaultItemsByFieldCode(fieldCode);
			fieldItems.put(fieldCode, items);
		}
	}
}
