package by.tc.photobook.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import by.tc.photobook.bean.Photo;
import by.tc.photobook.bean.UserInfo;
import by.tc.photobook.service.PhotosService;
import by.tc.photobook.service.ServiceException;
import by.tc.photobook.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@MultipartConfig(fileSizeThreshold = 1024 * 1024,
maxFileSize = 1024 * 1024 * 5, 
maxRequestSize = 1024 * 1024 * 5 * 5)
public class LoadImage extends HttpServlet
{
	private static String NEW_PHOTO = "new_photo";
	private static final String AUTH_ATTRIBUTE = "auth";
	private static final String USER_ATTRIBUTE = "user";
	private static final String LOAD_MAIN_PAGE = "Controller?command=loadmainpage&message=";
	private static final String LOAD_PROFILE_PAGE = "Controller?command=loadprofilepage";
	private static final String IMAGE_FOLDER = "images";
	private static final String LOAD_MAIN_PAGE_WITH_MESSAGE = "Controller?command=loadmainpage&message=";
	private static final String SESSION_EXPIRED_MESSAGE="message.session_expired";
	private static final String UPLOAD_DIRECTORY = "E:\\epam\\eclipse-workplace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\photobook\\images";
	
	private static final int START_RATING = 0;
	
	private static final long serialVersionUID = 1L;

	public LoadImage() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        process(request, response);
    }

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
			
			LocalDate date = LocalDate.now();
			Photo newPhoto = new Photo(username, date, photoPath, START_RATING);
			
			ServiceProvider serviceProvider = ServiceProvider.getInstance();
	        PhotosService photosService = serviceProvider.getPhotosService();
	        
	        try
	        {
	        	photosService.addNewPhoto(newPhoto);
	        	response.sendRedirect(LOAD_PROFILE_PAGE);
	        }
	        catch(ServiceException e)
	        {
	        	response.sendRedirect(LOAD_MAIN_PAGE+e.getDescription());
	        }
		}
    }
}
