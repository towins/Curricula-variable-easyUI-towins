package org.towins.scss.aspect;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.forten.utils.system.ValidateException;
import org.forten.utils.system.ValidateUtil;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ValidAspect {
	@Before("@annotation(javax.validation.Valid)")
	public void valid(JoinPoint jp) {
		Object[] args = jp.getArgs();
		if (args.length == 0) {
			System.out.println("方法无参数可进行校验。");
			return;
		}
		List<String> errorList = new ArrayList<>();
		if (args.length == 1) {
			errorList.addAll(ValidateUtil.validate(args[0]));
		} else {
			MethodSignature ms = (MethodSignature) jp.getSignature();
			Method m = ms.getMethod();
			Parameter[] params = m.getParameters();
			int i = 0;
			for (Parameter p : params) {
				Valid vann = p.getAnnotation(Valid.class);
				if (vann != null) {
					errorList.addAll(ValidateUtil.validate(args[i]));
				}
				i++;
			}
		}
		if (errorList.size() > 0) {
			throw new ValidateException(errorList);
		}
	}
}
