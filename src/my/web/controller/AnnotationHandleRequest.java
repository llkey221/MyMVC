package my.web.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    /**
     * @methodName parseRequest
     * @description ½ØÈ¡URLÇëÇóµÄAction method
     * @author Rihard Tang
     * @param request
     * @return
     */
    public String parseRequest(HttpServletRequest request){
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
	
	public void execute(HttpServletRequest request,HttpServletResponse response){
		
	}

}
