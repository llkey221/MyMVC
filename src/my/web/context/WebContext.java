package my.web.context;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @methodName WebContext
 * @description 主要用来存储当前线程 httpServletRequest和httpServletResponse
 * @author Richard Tang
 * @date 2015-12-31 23:56
 */
public class WebContext {
	public static ThreadLocal<HttpServletRequest> requestHolder=new ThreadLocal<HttpServletRequest>();
	
	public static ThreadLocal<HttpServletResponse> responseHolder=new ThreadLocal<HttpServletResponse>();
	
	public HttpServletRequest getRequest(){
		return requestHolder.get();
	}
		
	public HttpServletResponse getResponse(){
		return responseHolder.get();
	}
	
	public ServletContext getServletContext(){
		return requestHolder.get().getServletContext();
	}
	
	public HttpSession getSession(){
		return requestHolder.get().getSession();
	}
}

