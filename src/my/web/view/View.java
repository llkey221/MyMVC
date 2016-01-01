package my.web.view;

/**
 * ��ͼ
 * @author Richard Tang
 * @date 2016-01-01
 */
public class View {
	//��ת·�� 
	private String url;
	/*
	 * ��ת��ʽ 
	 */
	private String dispatchAction =DispatchActionConstant.FORWARD;
	
	/**
	 * ��ͼ���أ�ָ����������ת·�� 
	 * @param url
	 */
	public View(String url){
		this.url=url;
	}
	
	/**
	 * ��ͼ���أ�ָ����ת·�����Ҵ���չʾ����
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
	 * ��ͼ���أ�ָ����תurl����ת���ͣ����Ҵ�������
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
