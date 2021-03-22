package by.tc.photobook.service;

import by.tc.photobook.service.impl.CommentsServiceImpl;
import by.tc.photobook.service.impl.PhotosServiceImpl;
import by.tc.photobook.service.impl.UserServiceImpl;

public class ServiceProvider 
{
	private static final ServiceProvider instance = new ServiceProvider(); 

	private ServiceProvider() {}
	
	private final UserService userService = new UserServiceImpl();
	private final PhotosService photosService = new PhotosServiceImpl();
	private final CommentsService commentsService = new CommentsServiceImpl();
	
	public static ServiceProvider getInstance() 
	{
		return instance;
	}

	public UserService getUserService() 
	{
		return userService;
	}
	
	public PhotosService getPhotosService()
	{
		return photosService;
	}
	
	public CommentsService getCommentsService()
	{
		return commentsService;
	}
}
