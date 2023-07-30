package org.tutorial.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

public class CorsInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String origin = request.getHeader("origin");
		System.out.println("request.getHeader(\"Origin\"): " + origin);
		if (origin != null && origin.equals("")) {
			// 設定允許特定網域的跨來源請求
			response.setHeader("Access-Control-Allow-Origin", origin);
			// 設定允許的請求方法，例如：GET, POST, OPTIONS, PUT, DELETE, PATCH
			response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE, PATCH");
			// 設定允許的請求標頭
			response.setHeader("Access-Control-Allow-Headers",
					"Origin, X-Requested-With, Content-Type, Accept, Authorization");
			// 設定是否允許携帶認證信息，例如：cookies
			response.setHeader("Access-Control-Allow-Credentials", "true");
			// 設定允許的最大緩存時間（秒），在此期間內相同的跨來源請求將不會再發送預檢請求（OPTIONS請求）
			response.setHeader("Access-Control-Max-Age", "3600");
			System.out.println("CorsInterceptor approved");
			
//			Origin：瀏覽器會自動在跨來源請求中添加此標頭，指示請求的來源網域。後端服務器可以根據這個標頭來決定是否允許該來源進行跨來源請求。
//
//			X-Requested-With：如果您的前端應用程式使用AJAX或類似技術發送請求，通常會自動在請求中添加此標頭。這是一個自定義標頭，有些後端服務器可能會使用它來識別AJAX請求。
//
//			Content-Type：如果您的請求包含主體內容，例如使用POST或PUT方法，瀏覽器會自動添加這個標頭，指示請求主體內容的類型。
//
//			Accept：瀏覽器會自動添加此標頭，指示它可以接受的回應內容類型。
//
//			Authorization：如果您的應用程式需要驗證用戶身份，並且使用了令牌或其他身份驗證機制，則應該將驗證憑據放在這個標頭中，以便後端服務器進行驗證。

			return true;
		} else {
			// 如果來源不是指定的網域，拒絕請求
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			System.out.println("CorsInterceptor rejected");
			return false;
		}
	}

}
