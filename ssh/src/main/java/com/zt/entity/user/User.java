package com.zt.entity.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 企业用户实体（快递公司、网点）
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "users")
public class User implements java.io.Serializable {
	public static final String[] VIEW_SYNC = {"id", "username", "realName", "password", "role", "objectId", "regionManagement", "isLocked",
			"isDeleted", "artifPersonPhone", "artifPersonAddr", "kdybCode", "logisticsCompanyName", "parent", "isDeleted", "isLocked", "regionManagement"};
	/**
	 * 用户id。如果用户导出自员工，则用户id与员工id一致
	 */
	@Id
	@Column(nullable = false, length = 32, unique = true)
	private String id;
	/**
	 * 用户名
	 */
	@Column(nullable = false, length = 30, unique = true)
	private String username;
	/**
	 * 真实名字（员工名字或公司名字）
	 */
	@Column(length = 60)
	private String realName;
	/**
	 * 密码（SHA256加密）
	 */
	@Column(nullable = false, length = 64)
//    @JSONField(serialize=false)//该注解用于标明JSON转换时不输出此属性
	private String password;
	/**
	 * 公司/网点/员工id
	 **/
	@Column(length = 30)
	private String objectId;
	/**
	 * 账户是否锁定（true：锁定 ，false：未锁定）
	 **/
	@Column(nullable = false)
	private Boolean isLocked = false;

	@Column(nullable = false)
	private Boolean isDeleted = false;

	@Column
	private String telephone;

	public User() {
		super();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	/**
	 * @return the isLocked
	 */
	public Boolean getIsLocked() {
		return isLocked;
	}

	/**
	 * @param isLocked the isLocked to set
	 */
	public void setIsLocked(Boolean isLocked) {
		this.isLocked = isLocked;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Override
	public String toString() {
		return "账号：" + username;
	}


}