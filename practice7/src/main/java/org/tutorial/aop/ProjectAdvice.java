package org.tutorial.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ProjectAdvice {

	// 切入點表達式(訪問修飾(可省略) 返回值 包名 類(介面)名 方法名 參數)
	@Pointcut("execution(* *..*Service+.*(..))")
	public void serviceLayerMethods() {
	} // 不需寫內容

	@Around("serviceLayerMethods()")
	public Object method(ProceedingJoinPoint pjp) throws Throwable {
		Object[] args = pjp.getArgs();
		String methodName = pjp.getSignature().getName(); // 取得方法名稱
		String className = pjp.getTarget().getClass().getName(); // 取得類別名稱
		System.out.println(
				"ClassName: " + className + "，Method: " + methodName + ", Begin with args: " + Arrays.toString(args));
		Object ret = pjp.proceed();
		System.out.println("ClassName: " + className + "，Method: " + methodName + ", End");
		return ret;
	}

	// 另有@Before、@After等，@Around最強大
}
