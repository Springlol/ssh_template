package com.zt.service;

import com.zt.dao.BaseDao;
import com.zt.dao.DaoUtil;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by zhoutao on 2017/1/5.
 */
@SuppressWarnings({"rawTypes,unchecked"})
public abstract class BaseService<T> {
	/**
	 * 继承BaseService<T></>，初始化对象时，会初始化aClass为给定类型
	 */
	private Class aClass;

	@Resource
	private BaseDao baseDao;

	public BaseService() {
		//反射泛型
		Type type = this.getClass().getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) type;
			Type type1 = parameterizedType.getActualTypeArguments()[0];
			this.aClass = (Class) type1;
		}
	}

	/**
	 * 根据Id查询实体对象
	 *
	 * @param id
	 * @return
	 */
	public T getByEntityId(String id) {
		return (T) baseDao.get(aClass, id);
	}

	/**
	 * 根据指定类型，id查询实体对象
	 *
	 * @param e
	 * @param id
	 * @return
	 */
	public <E> E getByEntityId(Class<E> e, String id) {
		return (E) baseDao.get(e, id);
	}

	/**
	 * 查询所有
	 *
	 * @return
	 */
	public List<T> findAll() {
		String hql = DaoUtil.getFindPrefix(aClass);
		return baseDao.find(hql);
	}

	/**
	 * 查询指定类型的对象
	 *
	 * @param eClass
	 * @return
	 */
	public <E> List<E> findAll(Class<E> eClass) {
		String hql = DaoUtil.getFindPrefix(eClass);
		return baseDao.find(hql);
	}

	/**
	 * 根据ids集合查询对象
	 *
	 * @param ids
	 * @return
	 */
	public List<T> findByIds(Collection<String> ids) {
		String hql = DaoUtil.getFindPrefix(aClass) + " WHERE id in ( :ids ) ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids);
		return baseDao.find(hql, params);
	}

	/**
	 * 根据ids集合查询对象
	 *
	 * @param ids
	 * @return
	 */
	public <E> List<E> findByIds(Class<E> e, Collection<String> ids) {
		String hql = DaoUtil.getFindPrefix(e) + " WHERE id in ( :ids ) ";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids);
		return baseDao.find(hql, params);
	}

	/**
	 * 根据ids字符串查询对象。以","分割  例如 idStr = "123,124,125";
	 *
	 * @param idStr
	 * @return
	 */
	public List<T> findByIdStr(String idStr) {
		String[] split = idStr.split(",");
		List<String> ids = Arrays.asList(split);
		return findByIds(ids);
	}

	/**
	 * 根据ids字符串查询对象。以","分割  例如 idStr = "123,124,125";
	 *
	 * @param idStr
	 * @return
	 */
	public <E> List<E> findByIdStr(Class<E> e, String idStr) {
		String[] split = idStr.split(",");
		List<String> ids = Arrays.asList(split);
		return findByIds(e, ids);
	}

	/**
	 * 根据条件查询对象
	 *
	 * @param condition
	 * @return
	 */
	public List<T> findByCondition(String condition) {
		String hql = DaoUtil.getFindPrefix(aClass);
		return baseDao.find(hql + condition);
	}

	/**
	 * 根据条件查询对象
	 *
	 * @param condition
	 * @param params
	 * @return
	 */
	public List<T> findByCondition(String condition, Map<String, Object> params) {
		String hql = DaoUtil.getFindPrefix(aClass);
		return baseDao.find(hql + condition, params);
	}

	/**
	 * 根据条件查询指定对象
	 *
	 * @param eClass
	 * @param condition
	 * @param params
	 * @return
	 */
	public <E> List<E> findByCondition(Class<E> eClass, String condition, Map<String, Object> params) {
		String hql = DaoUtil.getFindPrefix(eClass);
		return baseDao.find(hql + condition, params);
	}

	/**
	 * 分页查询
	 *
	 * @param condition
	 * @param params
	 * @param rows
	 * @param page
	 * @return
	 */
	public List<T> findByPage(String condition, Map<String, Object> params, int rows, int page) {
		String hql = DaoUtil.getFindPrefix(aClass);
		return baseDao.find(hql + condition, params, page, rows);
	}

	/**
	 * 分页查询指定对象
	 *
	 * @param eClass
	 * @param condition
	 * @param params
	 * @param rows
	 * @param page
	 * @return
	 */
	public <E> List<E> findByPage(Class<E> eClass, String condition, Map<String, Object> params, int rows, int page) {
		String hql = DaoUtil.getFindPrefix(eClass);
		return baseDao.find(hql + condition, params, page, rows);
	}

	/**
	 * 更新对象
	 *
	 * @param e
	 */
	public <E> void updateEntity(E e) {
		baseDao.saveOrUpdate(e);
	}

	/**
	 * 删除对象
	 *
	 * @param e
	 */
	public <E> void deleteEntity(E e) {
		baseDao.delete(e);
	}

	/**
	 * 批量删除集合对中象
	 *
	 * @param t
	 */
	public <E> void deleteEntityByCollection(Collection<E> e) {
		for (E t1 : e) {
			deleteEntity(t1);
		}
	}

	/**
	 * 根据id删除对象
	 *
	 * @param id
	 */
	public void deleteById(String id) {
		baseDao.delete(aClass, id);
	}

	/**
	 * 根据id删除指定对象
	 *
	 * @param eClass
	 * @param id
	 */
	public <E> void deleteById(Class<E> eClass, String id) {
		baseDao.delete(eClass, id);
	}

	/**
	 * 根据id集合删除对象
	 *
	 * @param ids
	 */
	public void deleteByIds(Collection<String> ids) {
		for (String id : ids) {
			deleteById(id);
		}
	}

	/**
	 * 通过id集合删除
	 *
	 * @param e
	 * @param ids
	 */
	public <E> void deleteByIds(Class<E> e, Collection<String> ids) {
		for (String id : ids) {
			deleteById(e, id);
		}
	}

	/**
	 * 根据idStr删除对象 列如 idStr = "123,124,125";
	 *
	 * @param idStr
	 */
	public void deleteByIdStr(String idStr) {
		String[] ids = idStr.split(",");
		List<String> idList = Arrays.asList(ids);
		deleteByIds(idList);
	}

	/**
	 * 根据idStr删除对象 列如 idStr = "123,124,125";
	 *
	 * @param idStr
	 */
	public <E> void deleteByIdStr(Class<E> e, String idStr) {
		String[] ids = idStr.split(",");
		List<String> idList = Arrays.asList(ids);
		deleteByIds(e, idList);
	}


	/**
	 * 保存对象
	 *
	 * @param e
	 */
	public <E> void saveEntity(E e) {
		baseDao.saveOrUpdate(e);
	}


}
