package my.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Java 反射的常用方法封装
 * @author Richard Tang
 *
 */
public class BeanUtils {
	/**
	 * @description 实例化一个class
	 * @param clazz
	 * @return
	 */
	public static <T> T instanceClass(Class<T> clazz){
		if(!clazz.isInterface()){
			try{
				return clazz.newInstance();
			}catch(InstantiationException e){
				e.printStackTrace();
			}catch(IllegalAccessException e){
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 通过构造函数实例化
	 * @param ctor
	 * @param arg
	 * @return
	 * @throws IllegalArgumentException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static <T> T instanceClass(Constructor<T> ctor,Object...arg)
		throws IllegalArgumentException,InstantiationException,
		IllegalAccessException,InvocationTargetException{
		makeAccessible(ctor);
		return ctor.newInstance(arg);	//调用构造函数实例化
	}
	
	/**
	 * 反射根据methodName获取class中方法 
	 * @param clazz
	 * @param methodName
	 * @param paramTypes
	 * @return
	 */
	public static Method findMethod(Class<?> clazz,String methodName,Class<?>...paramTypes){
		try{
			return clazz.getMethod(methodName, paramTypes);
		}catch(NoSuchMethodException e){
			return findDeclaredMethod(clazz,methodName,paramTypes);
		}
	}
	
	/**
	 * 反射根据 methodName获取方法 
	 * @param clazz
	 * @param methodName
	 * @param paramTypes
	 * @return
	 */
	public static Method findDeclaredMethod(Class<?> clazz,String methodName,Class<?>[] paramTypes){
		try{
			return clazz.getDeclaredMethod(methodName, paramTypes);
		}catch(NoSuchMethodException e){
			if(clazz.getSuperclass()!=null){
				return findDeclaredMethod(clazz.getSuperclass(),methodName,paramTypes);
			}
			return null;
		}
	}
	
	/**
	 * 获取class中所有方法 
	 * @param clazz
	 * @return
	 */
	public static Method[] findDeclaredMethods(Class<?> clazz){
		return clazz.getDeclaredMethods();
	}
	
	/**
	 * 使私有构造函数允许访问 
	 * @param ctor
	 */
	public static void makeAccessible(Constructor<?> ctor ){
		if((!Modifier.isPublic(ctor.getModifiers()))
				|| !Modifier.isPublic(ctor.getDeclaringClass().getModifiers())
				&&!ctor.isAccessible()){
			ctor.setAccessible(true);
		}
	}
	
	/**
	 * 反射获取字段
	 * @param clazz
	 * @return
	 */
	public static Field[] findDeclaredField(Class<?> clazz){
		return clazz.getDeclaredFields();
	}
}
