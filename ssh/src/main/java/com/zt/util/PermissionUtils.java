package com.zt.util;

public class PermissionUtils {

	private static String[] commonUrls;

	static {
		commonUrls = "/random/execute,/main,/getMenuData,/sys/dict/getUploadFileAllowedExts,/sys/dict/getUploadFileMaxsize,/main/center,/changePwd,/password/savePwd,/hr/dept/loadSimpleTreeJson".split(",");
	}

	/**
	 * 根据url判断登录用户是否有相应权限
	 * <p>
	 * 2015-9-15更新。将indexOf改为equals。避免类似于拥有权限"/user/editPhone"时误认为也拥有权限"/user/edit"的情况发生
	 *
	 * @param url
	 * @return
	 */
	public static boolean hasPerssion(String url) {
		// 过滤公共URL
		for (String commonUrl : commonUrls) {
			if (url.equals(commonUrl)) {
				return true;
			}
		}

       /* Collection<SysResource> userResources = WebUtil.getLoginUser().getUserResources().values();
		for (SysResource resource : userResources) {
            if(url.equals(resource.getUrl())) {
                return true;
            }
        }*/
		return false;
	}
}
