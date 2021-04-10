package com.ssdut.imkg.config.security.component;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssdut.imkg.pojo.pub.RespBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 当未登录或者token失效时访问接口时，自定义的返回结果
 *
 * @author zhoubin
 * @since 1.0.0
 */
@Component
public class RestAuthorizationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
		System.out.println("RestAuthorizationEntryPoint拦截====");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		RespBean bean = RespBean.error("尚未登录，请登录！");
		bean.setCode(401);
		out.write(new ObjectMapper().writeValueAsString(bean));
		out.flush();
		out.close();
	}
}