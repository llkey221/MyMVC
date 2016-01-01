package my.web.view;

import javax.servlet.http.HttpServletRequest;

import my.web.context.WebContext;

/**
 * 发送到客户端显示的数据模型
 * @author Richard Tang
 * @date 2016-1-1
 */
public class ViewData {
	
	private HttpServletRequest request;
	
	public ViewData(){
		initRequest();
	}
	
	/**
	 * @methodName initRequest
	 * @description 从当前线程获取request对象
	 * @author Richard Tang
	 */
	private void initRequest(){
		//从当前线程获取request对象
		request=WebContext.requestHolder.get();
	}
	
	/**
	 * @description 保存值到request中
	 * @param name
	 * @param value
	 */
	public void put(String name,Object value){
		request.setAttribute(name, value);
	}
	
}
