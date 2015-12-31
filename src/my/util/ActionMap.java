package my.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName:ActionMap
 * @description: �洢�����ķ���·�� 
 * @author Richard Tang
 * 
 */
public class ActionMap {
	private static Map<String,Class<?>> actionMap=new HashMap<String,Class<?>>();
	
	public static Class<?> getClassName(String path){
		return actionMap.get(path);
	}
	
	public static void put(String path,Class<?> clazz){
		actionMap.put(path, clazz);
	}
	
	public static Map<String,Class<?>> getActionMap(){
		return actionMap;
	}
	
}
