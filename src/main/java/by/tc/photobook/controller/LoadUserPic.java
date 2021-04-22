package by.tc.photobook.controller;

import java.io.File;

import java.io.IOException;

import by.tc.photobook.bean.UserInfo;
import by.tc.photobook.service.ServiceException;
import by.tc.photobook.service.ServiceProvider;
import by.tc.photobook.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

/** 
 * Controller that processes requests related to loading images from user's drive
 * @see HttpServlet
 * @author Darya Minina
*/
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
maxFileSize = 1024 * 1024 * 5, 
maxRequestSize = 1024 * 1024 * 5 * 5)
public class LoadUserPic extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	private static final String AUTH_ATTRIBUTE = "auth";
	private static final String USER_ATTRIBUTE = "user";
	private static final String IMAGE_FOLDER = "images";
	private static final String LOAD_MAIN_PAGE_WITH_MESSAGE = "Controller?command=loadmainpage&message=";
	private static final String SESSION_EXPIRED_MESSAGE="message.session_expired";
	private static final String LOAD_PROFILE_PAGE = "Controller?command=loadprofilepage";
	
	public LoadUserPic()
	{
		super();
	}
	
	/**
     * Receives get requests
	 * @param request  {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response);
    }

	/**
     * Receives posts requests
	 * @param request  {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @throws ServletException
	 * @throws IOException
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        process(request, response);
    }
    
    /**
   	 * Processes a file loaded by user, stores it in specific folder with all app images
   	 * and updates user's profile picture
   	 * @param request  {@link HttpServletRequest}
   	 * @param response {@link HttpServletResponse}
   	 * @throws ServletException
   	 * @throws IOException
   	 */
    private void process(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
    {
    	String uploadPath = getServletContext().getRealPath(IMAGE_FOLDER);
    	File uploadDir = new File(uploadPath);
    	if (!uploadDir.exists())
    	{
    		uploadDir.mkdir();
    	}
    	String fileName = null;
    	
    	
    	for (Part part : request.getParts()) 
    	{
    		fileName = part.getSubmittedFileName().toString();
    	    part.write(uploadPath + File.separator + part.getSubmittedFileName());
    	    break;
    	}
    	String photoPath = IMAGE_FOLDER+File.separator+fileName;
		
		HttpSession session = request.getSession();
		
		if(session == null || session.getAttribute(AUTH_ATTRIBUTE) == null || 
				session.getAttribute(USER_ATTRIBUTE) == null)
		{
			response.sendRedirect(LOAD_MAIN_PAGE_WITH_MESSAGE+SESSION_EXPIRED_MESSAGE);
		}
		else
		{
			UserInfo user = (UserInfo)session.getAttribute(USER_ATTRIBUTE);
			String username = user.getUsername();
			ServiceProvider serviceProvider = ServiceProvider.getInstance();
	        UserService userService = serviceProvider.getUserService();
	        
	        try
	        {
	        	userService.updateProfilePic(username, photoPath);
	        	user.setProfilePicPath(photoPath);
	        	response.sendRedirect(LOAD_PROFILE_PAGE);
	        }
	        catch(ServiceException e)
	        {
	        	response.sendRedirect(LOAD_MAIN_PAGE_WITH_MESSAGE+e.getDescription());
	        }
		}
    }
}
