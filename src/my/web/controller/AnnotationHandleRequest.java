package my.web.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import my.annotation.Action;
import my.annotation.Controller;
import my.util.ActionMap;
import my.util.BeanUtils;
import my.util.ScanClassUtil;
import my.web.context.WebContext;
import my.web.view.DispatchActionConstant;
import my.web.view.View;

/**
 * Servlet implementation class AnnotationHandleRequest
 */
@WebServlet("/AnnotationHandleRequest")
public class AnnotationHandleRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AnnotationHandleRequest() {
        super();
        // TODO Auto-generated constructor stub
    }
    @Override
    public void init(ServletConfig config) throws ServletException{
    	//��дinit������Ҫ�ȵ���super�����init������������doGet��doPost����ʱ
    	//����getServletContext��ȡServletContext����ʱ�ͻ����java.lang.NullPointerException�쳣
    	super.init(config);
    	System.out.println("��ʼ����ʼ");
    	String basePackage=config.getInitParameter("basePackage");
    	if(basePackage.indexOf('.')>0){
    		String []packageNameAttr=basePackage.split(",");
    		for(String packageName :packageNameAttr){
    			initActionMap(packageName);
    		}
    	}else{
    		initActionMap(basePackage);
    	}
    	System.out.println("��ʼ������ ");
    }
    
    /**
     * @methodName initActionMap
     * @description �������Controller��Actionע���class��method��ӳ�����
     * @author Richard Tang
     * @param pakageName
     */
    private void initActionMap(String pakageName){
    	//ɨ��ָ����������Class
    	Set<Class<?>> setClasses=ScanClassUtil.getClassess(pakageName);
    	
    	//���������࣬�����Controllerע���class��
    	//���Actionע�����������ӵ�ActionMap��
    	for(Class<?> clazz : setClasses){
    		if(clazz.isAnnotationPresent(Controller.class)){
    			Method [] methods=BeanUtils.findDeclaredMethods(clazz);
    			for(Method method :methods ){
    				if(method.isAnnotationPresent(Action.class)){
    					String anoPath=method.getAnnotation(Action.class).value();
    					if(anoPath!=null &&!"".equals(anoPath.trim())){
    						if(ActionMap.getActionMap().containsKey(anoPath)){
    							throw new RuntimeException("Actionӳ��ĵ�ַ�������ظ�");
    						}
    						ActionMap.put(anoPath, clazz);
    					}
    				}
    			}
    		}
    	}
    }
    
    /**
     * @methodName parseRequest
     * @description ��ȡURL�����Action method
     * @author Rihard Tang
     * @param request
     * @return
     */
    public String parseRequestURI(HttpServletRequest request){
    	String path=request.getContextPath()+"/";
    	String requestUri=request.getRequestURI();
    	String midUrl=requestUri.replaceFirst(path, "");
    	String lastUrl=midUrl.substring(0, midUrl.lastIndexOf("."));
    	
    	return lastUrl;
    	
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.execute(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.execute(request,response);
	}
	
	/**
	 * ��������URl��Ӧ��Class��method��ִ��
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void execute(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		WebContext.requestHolder.set(request);
		WebContext.responseHolder.set(response);
		//����URL
		String lastUrl=parseRequestURI(request);
		//��ȡ����URL����Ӧ��class
		Class<?> clazz=ActionMap.getActionMap().get(lastUrl);
		//����ʵ�� 
		Object classInstance=BeanUtils.instanceClass(clazz);
		//��ȡ���ж���ķ��� 
		Method []methods=BeanUtils.findDeclaredMethods(clazz);
		Method method=null;
		//�����ҵ�����Url��Ӧ�ķ���ִ��
		for(Method m :methods){
			if(m.isAnnotationPresent(Action.class)){
				String anoPath=m.getAnnotation(Action.class).value();
				if(anoPath!=null&& !"".equals(anoPath) && anoPath.trim().equals(lastUrl)){
					//�ҵ�Ŀ�귽�� 
					method=m;
					break;
				}
			}
		}
		
		try{
			if(method!=null){
				//ִ��Ŀ�귽���������û�������
				Object retObject=method.invoke(classInstance);
				//�������ֵ�����ʾ�û���Ҫ������ͼ
				if(retObject!=null){
					View view=(View)retObject;
					if(view.getDispatchAction().equals(DispatchActionConstant.FORWARD)){
						//�������ת
						request.getRequestDispatcher(view.getUrl()).forward(request, response);
					}else if(view.getDispatchAction().equals(DispatchActionConstant.REDIRECT)){
						//�ͻ�����ת��ʽ 
						response.sendRedirect(view.getUrl());
					}else{
						request.getRequestDispatcher(view.getUrl()).forward(request, response);
					}
				}
			}
		}catch(IllegalArgumentException e){
			e.printStackTrace();
		}catch(IllegalAccessException e){
			e.printStackTrace();
		}catch(InvocationTargetException e){
			e.printStackTrace();
		}
	}

}
