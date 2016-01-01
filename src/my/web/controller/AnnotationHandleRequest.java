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
    	//重写init方法后，要先调用super父类的init方法，否则在doGet和doPost方法时
    	//调用getServletContext获取ServletContext对象时就会出现java.lang.NullPointerException异常
    	super.init(config);
    	System.out.println("初始化开始");
    	String basePackage=config.getInitParameter("basePackage");
    	if(basePackage.indexOf('.')>0){
    		String []packageNameAttr=basePackage.split(",");
    		for(String packageName :packageNameAttr){
    			initActionMap(packageName);
    		}
    	}else{
    		initActionMap(basePackage);
    	}
    	System.out.println("初始化结束 ");
    }
    
    /**
     * @methodName initActionMap
     * @description 解析添加Controller和Action注解的class和method到映射表中
     * @author Richard Tang
     * @param pakageName
     */
    private void initActionMap(String pakageName){
    	//扫描指定包下所有Class
    	Set<Class<?>> setClasses=ScanClassUtil.getClassess(pakageName);
    	
    	//遍历所有类，把添加Controller注解的class里
    	//添加Action注解的所有类添加到ActionMap中
    	for(Class<?> clazz : setClasses){
    		if(clazz.isAnnotationPresent(Controller.class)){
    			Method [] methods=BeanUtils.findDeclaredMethods(clazz);
    			for(Method method :methods ){
    				if(method.isAnnotationPresent(Action.class)){
    					String anoPath=method.getAnnotation(Action.class).value();
    					if(anoPath!=null &&!"".equals(anoPath.trim())){
    						if(ActionMap.getActionMap().containsKey(anoPath)){
    							throw new RuntimeException("Action映射的地址不允许重复");
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
     * @description 截取URL请求的Action method
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
	 * 查找请求URl对应的Class和method并执行
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void execute(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		WebContext.requestHolder.set(request);
		WebContext.responseHolder.set(response);
		//解析URL
		String lastUrl=parseRequestURI(request);
		//获取请求URL，对应的class
		Class<?> clazz=ActionMap.getActionMap().get(lastUrl);
		//创建实例 
		Object classInstance=BeanUtils.instanceClass(clazz);
		//获取类中定义的方法 
		Method []methods=BeanUtils.findDeclaredMethods(clazz);
		Method method=null;
		//遍历找到请求Url对应的方法执行
		for(Method m :methods){
			if(m.isAnnotationPresent(Action.class)){
				String anoPath=m.getAnnotation(Action.class).value();
				if(anoPath!=null&& !"".equals(anoPath) && anoPath.trim().equals(lastUrl)){
					//找到目标方法 
					method=m;
					break;
				}
			}
		}
		
		try{
			if(method!=null){
				//执行目标方法，处理用户有请求
				Object retObject=method.invoke(classInstance);
				//如果返回值，则表示用户需要返回视图
				if(retObject!=null){
					View view=(View)retObject;
					if(view.getDispatchAction().equals(DispatchActionConstant.FORWARD)){
						//服务端跳转
						request.getRequestDispatcher(view.getUrl()).forward(request, response);
					}else if(view.getDispatchAction().equals(DispatchActionConstant.REDIRECT)){
						//客户端跳转方式 
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
