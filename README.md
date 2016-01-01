# MyMVC
使用注解实现简单的MVC框架

主要技术点：

	1、Java反射
	
	2、Java IO
	
	3、Java 注解
	
	4、Servlet
	
主要实现：

	完成类似SpringMVC框架基本MVC功能，实现在一个普通的Java类上标记@Controller注解,则把这个类标注为一个Controller允许客户端请求，在类的方法上标记@Action注解，最终完成用户请求执行的Controller和Action过程。

实现思路：

	通过Servlet来完成拦截用户请求的，转向对应的Controller和Action执行。
	
	在Servlet初始化时，根据配置的Controller包目录，扫描指定目录下所有添加@Controller注解的Class。
	
	找到标注的Class后，扫描所有方法注释 ，把标记的Action加载到内存Map中，以Action指定的路径为key,所在的类为value；
	
	客户端的发送*.do请求到服务端，Servlet解析Url,通过请求的路径，在Map中匹配需要执行的Action。
	
	Action执行完成后返回的View视图，View视图里包含需要跳转的URL，跳转的类型，并且封装ViewData临时存储数据，根据视图内容查找对应jsp页面展示。
	