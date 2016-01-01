package my.web.UI;

import my.annotation.Action;
import my.annotation.Controller;
import my.web.view.View;

/**
 * ʹ��Controller ע���עLoginUI��
 * @author Richard Tang
 * @createdate 2015-12-31
 */
@Controller
public class LoginUI {

		/**
		 * ͨ��Actionָ�������ķ���·��
		 * @methodName forward1
		 * @return
		 */
		@Action("LoginUI/Login2")
		public View forward1(){
			return new View("/login2.jsp");
		}
		
		@Action("LoginUI/Login3")
		public View forward2(){
			return new View("/login3.jsp");
		}
}
