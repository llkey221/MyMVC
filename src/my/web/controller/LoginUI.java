package my.web.controller;

import my.annotation.Controller;
import my.annotation.RequestMapping;
import my.web.view.View;

/**
 * 使用Controller 注解标注LoginUI类
 * @author Richard Tang
 * @createdate 2015-12-31
 */
@Controller
public class LoginUI {

		/**
		 * 通过RequestMapping指明方法的访问路径
		 * @methodName forward1
		 * @return
		 */
		@RequestMapping("/LoginUI/Login2")
		public View forward1(){
			return new View("/login2.jsp");
		}
		
		@RequestMapping("/LoginUI/Login3")
		public View forward2(){
			return new View("/login3.jsp");
		}
}
