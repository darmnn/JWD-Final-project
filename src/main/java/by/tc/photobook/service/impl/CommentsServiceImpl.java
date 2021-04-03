package by.tc.photobook.service.impl;

import java.util.List;

import by.tc.photobook.bean.Comment;
import by.tc.photobook.dao.CommentsDAO;
import by.tc.photobook.dao.DAOException;
import by.tc.photobook.dao.DAOProvider;
import by.tc.photobook.service.CommentsService;
import by.tc.photobook.service.ServiceException;

public class CommentsServiceImpl implements CommentsService
{
	
	public List<Comment> takePhotoComments(int photoId) throws ServiceException
	{
		DAOProvider daoProvider = DAOProvider.getInstance();
		CommentsDAO commentsDAO = daoProvider.getCommentsDAO();
		
		List<Comment> comments = null;
		
		try 
		{
			comments = commentsDAO.takePhotoComments(photoId);
		} 
		catch (DAOException e) 
		{
			throw new ServiceException(e.getDescription(), e);
		}
		
		return comments;
	}
	
	public boolean loadNewComment(Comment comment, int photoId, int authorId) throws ServiceException
	{
		DAOProvider daoProvider = DAOProvider.getInstance();
		CommentsDAO commentsDAO = daoProvider.getCommentsDAO();
		
		try 
		{
			commentsDAO.loadNewComment(comment, photoId, authorId);
		} 
		catch (DAOException e) 
		{
			throw new ServiceException(e.getDescription(), e);
		}
		
		return true;
	}
}
