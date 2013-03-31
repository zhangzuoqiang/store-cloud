package com.graby.store.base;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;

public class DelegatingFilterExProxy extends DelegatingFilterProxy {
	
	private String excludePatterns;
	
	private PathMatcher pathMatcher = new AntPathMatcher();
	
	@Override
	protected Filter initDelegate(WebApplicationContext wac) throws ServletException {
		this.excludePatterns = getFilterConfig().getInitParameter("excludePatterns");
		return super.initDelegate(wac);
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException,
			IOException {
		if (request instanceof  HttpServletRequest) {
			String url = ((HttpServletRequest)request).getRequestURL().toString();
			if (excludePatterns.indexOf(",") >0) {
				String[] patterns = excludePatterns.split(",");
				for (String p : patterns) {
					if (pathMatcher.match(p.trim(), url)) {
						filterChain.doFilter(request, response);
						return;
					}
				}
			} else {
				if (pathMatcher.match(excludePatterns, url)) {
					filterChain.doFilter(request, response);
					return;
				}
			}
		}
		super.doFilter(request, response, filterChain);
	}
	
	public static void main(String[] args) {
		String path = "http://127.0.0.1/xtaoAuth.html";
		String pattern = "**/xtaoAuth*";
		PathMatcher pathMatcher = new AntPathMatcher();
		System.out.println(pathMatcher.match(pattern, path));
	}
}
