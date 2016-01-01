package my.web.view;

/**
 * 视图
 * @author Richard Tang
 * @date 2016-01-01
 */
public class View {
	//跳转路径 
	private String url;
	/*
	 * 跳转方式 
	 */
	private String dispatchAction =DispatchActionConstant.FORWARD;
	
	/**
	 * 视图重载，指定服务器跳转路径 
	 * @param url
	 */
	public View(String url){
		this.url=url;
	}
	
	/**
	 * 视图重载，指定跳转路径并且传递展示数据
	 * @param url
	 * @param name
	 * @param value
	 */
	public View(String url,String name,Object value){
		this.url=url;
		ViewData viewData=new ViewData();
		viewData.put(name, value);
	}
	
	/**
	 * 视图重载，指定跳转url，跳转类型，并且传递数据
	 * @param url
	 * @param dispatchAction
	 * @param name
	 * @param value
	 */
	public View(String url,String dispatchAction,String name,Object value){
		this.dispatchAction=dispatchAction;
		this.url=url;
		ViewData viewData=new ViewData();
		viewData.put(name, value);		
	}
	
	public String getUrl(){
		return this.url;
	}
	
	public String getDispatchAction(){
		return this.dispatchAction;
	}
	
	public void setDispatchAction(String dispatchAction){
		this.dispatchAction=dispatchAction;
	}
	
}
