package my.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Java ����ĳ��÷�����װ
 * @author Richard Tang
 *
 */
public class BeanUtils {
	/**
	 * @description ʵ����һ��class
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
	 * ͨ�����캯��ʵ����
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
		return ctor.newInstance(arg);	//���ù��캯��ʵ����
	}
	
	/**
	 * �������methodName��ȡclass�з��� 
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
	 * ������� methodName��ȡ���� 
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
	 * ��ȡclass�����з��� 
	 * @param clazz
	 * @return
	 */
	public static Method[] findDeclaredMethods(Class<?> clazz){
		return clazz.getDeclaredMethods();
	}
	
	/**
	 * ʹ˽�й��캯��������� 
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
	 * �����ȡ�ֶ�
	 * @param clazz
	 * @return
	 */
	public static Field[] findDeclaredField(Class<?> clazz){
		return clazz.getDeclaredFields();
	}
}
