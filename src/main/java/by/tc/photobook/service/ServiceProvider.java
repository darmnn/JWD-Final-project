package by.tc.photobook.service;

import by.tc.photobook.service.impl.CommentsServiceImpl;
import by.tc.photobook.service.impl.OrdersServiceImpl;
import by.tc.photobook.service.impl.PhotosServiceImpl;
import by.tc.photobook.service.impl.PhotoshootOptionsServiceImpl;
import by.tc.photobook.service.impl.PhotoshootTypesServiceImpl;
import by.tc.photobook.service.impl.UserServiceImpl;

public class ServiceProvider 
{
	private static final ServiceProvider instance = new ServiceProvider(); 

	private ServiceProvider() {}
	
	private final UserService userService = new UserServiceImpl();
	private final PhotosService photosService = new PhotosServiceImpl();
	private final CommentsService commentsService = new CommentsServiceImpl();
	private final PhotoshootOptionsService phOptionsService = new PhotoshootOptionsServiceImpl();
	private final PhotoshootTypesService phTypesService = new PhotoshootTypesServiceImpl();
	private final OrdersService ordersService = new OrdersServiceImpl();
	
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
	
	public PhotoshootOptionsService getPhotoshootOptionsService()
	{
		return phOptionsService;
	}
	
	public PhotoshootTypesService getPhotoshootTypesService() 
	{
		return phTypesService;
	}
	
	public OrdersService getOrdersService()
	{
		return ordersService;
	}
}
