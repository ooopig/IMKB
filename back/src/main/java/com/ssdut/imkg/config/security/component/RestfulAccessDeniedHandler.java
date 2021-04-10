package com.ssdut.imkg.config.security.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssdut.imkg.pojo.pub.RespBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 当访问接口没有权限时，自定义返回结果
 *
 * @author fanyuanxin
 * @since 1.0.0
 */
@Component
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
		System.out.println("RestfulAccessDeniedHandler拦截====");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		RespBean bean = RespBean.error("RestfulAccessDeniedHandler=权限不足，请联系管理员！");
		bean.setCode(403);
		out.write(new ObjectMapper().writeValueAsString(bean));
		out.flush();
		out.close();
	}
}