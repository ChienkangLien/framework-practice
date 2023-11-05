package org.tutorial.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// 全局的異常處理，返回錯誤頁面
// 默認springboot會嘗試查找/error，error.html或是error.jsp，都沒有的話就是白板畫面
// 所以即便不配置這個類也可以
@ControllerAdvice
public class ProjectExceptionAdvice {
	@ExceptionHandler(Exception.class)
	public String doException() {
		return "/error";
	}
}
