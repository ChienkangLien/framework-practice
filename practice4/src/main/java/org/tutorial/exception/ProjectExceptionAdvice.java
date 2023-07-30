package org.tutorial.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// 全局的異常處理，返回錯誤頁面
@ControllerAdvice
public class ProjectExceptionAdvice {
	@ExceptionHandler(Exception.class)
	public String doException() {
		return "/error";
	}
}
