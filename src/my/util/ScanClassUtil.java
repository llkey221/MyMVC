package my.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @className ScanClassUtil
 * @description 扫描指定包下的class
 * @author Richard Tang
 * @date 2015-12-31 23:08
 *
 */
public class ScanClassUtil {
	public static Set<Class<?>> getClassess(String pack){
		Set<Class<?>> classes=new LinkedHashSet<Class<?>>();
		//是否循环迭代
		boolean recursive=true;
		
		String packageName=pack;
		String packageDirName=packageName.replace('.', '/');
		
		Enumeration<URL> dirs;
		try {
			dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
			while (dirs.hasMoreElements()) {
				URL url = dirs.nextElement();
				System.out.println(url);
				String protocol=url.getProtocol();
				
				if("file".equals(protocol)){
					System.err.println("file 类型的扫描 ");
					String filePath=URLDecoder.decode(url.getFile(),"UTF-8");
					findAndAddClassesInPackageByFile(packageName,filePath,
							recursive,classes);
				}else if("jar".equals(protocol)){
					System.err.println("jar 类型的扫描");
					JarFile jar;
					try{
						jar=((JarURLConnection) url.openConnection()).getJarFile();
						Enumeration<JarEntry>entries=jar.entries();
						while(entries.hasMoreElements()){
							JarEntry entry=entries.nextElement();
							String name=entry.getName();
							if(name.charAt(0)=='/'){
								name=name.substring(1);
							}
							if(name.startsWith(packageDirName)){
								int idx=name.lastIndexOf('/');
								
								if(idx!=-1){
									packageName=name.substring(0, idx)
											.replace('/', '.');
								}
								if((idx!=-1)|| recursive){
									if(name.endsWith(".class")
											&&!entry.isDirectory()){
										String className=name.substring(packageName.length()+1,name.length()-6);
										try{
											classes.add(Class.forName(packageName+'.'+className));
										}catch(ClassNotFoundException e){
											e.printStackTrace();
										}
									}
								}
							}
						}
					}catch(IOException e){
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return classes;
	}
	
	public static void findAndAddClassesInPackageByFile(String packageName,
			String packagePath,final boolean recursive,Set<Class<?>>classes){
		File dir=new File(packagePath);
		if(!dir.exists() ||!dir.isDirectory()){
			return;
		}
		
		File [] dirfiles=dir.listFiles(new FileFilter(){
			public boolean accept(File file){
				return (recursive && file.isDirectory())||(file.getName().endsWith(".class"));
			}
		});
		
		for(File file : dirfiles){
			if(file.isDirectory()){
				findAndAddClassesInPackageByFile(packageName+"."
						+file.getName(),file.getAbsolutePath(),recursive,
						classes);
			} else {
				String className = file.getName().substring(0, file.getName().length() - 6);
				try {
					classes.add(
							Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));

				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		
		}
	}
}
