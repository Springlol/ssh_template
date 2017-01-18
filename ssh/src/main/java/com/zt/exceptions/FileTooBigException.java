/**
 *
 */
package com.zt.exceptions;

/**
 * 文件超出限制大小异常
 */
@SuppressWarnings("serial")
public class FileTooBigException extends Exception {
	public FileTooBigException() {
		super();
	}

	public FileTooBigException(String msg) {
		super(msg);
	}
}
