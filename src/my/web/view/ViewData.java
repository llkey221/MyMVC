package my.web.view;

import javax.servlet.http.HttpServletRequest;

import my.web.context.WebContext;

/**
 * ���͵��ͻ�����ʾ������ģ��
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
	 * @description �ӵ�ǰ�̻߳�ȡrequest����
	 * @author Richard Tang
	 */
	private void initRequest(){
		//�ӵ�ǰ�̻߳�ȡrequest����
		request=WebContext.requestHolder.get();
	}
	
	/**
	 * @description ����ֵ��request��
	 * @param name
	 * @param value
	 */
	public void put(String name,Object value){
		request.setAttribute(name, value);
	}
	
}
