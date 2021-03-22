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
	private static final String LOADING_COMMENTS_ERROR = "Error while loading comments!";
	private static final String ADDING_COMMENT_ERROR = "Error while adding new comment!";
	
	public List<Comment> takePhotoComments(int photoId) throws ServiceException
	{
		DAOProvider daoProvider = DAOProvider.getInstance();
		CommentsDAO commentsDAO = daoProvider.getCommentsDAO();
		
		List<Comment> comments = null;
		
		try 
		{
			comments = commentsDAO.takePhotoComments(photoId);
			if(comments == null)
			{
				throw new ServiceException(LOADING_COMMENTS_ERROR);
			}
		} 
		catch (DAOException e) 
		{
			throw new ServiceException(e);
		}
		
		return comments;
	}
	
	public boolean loadNewComment(Comment comment, int photoId, int authorId) throws ServiceException
	{
		DAOProvider daoProvider = DAOProvider.getInstance();
		CommentsDAO commentsDAO = daoProvider.getCommentsDAO();
		
		boolean commentAdded = false;
		
		try 
		{
			commentAdded = commentsDAO.loadNewComment(comment, photoId, authorId);
			if(!commentAdded)
			{
				throw new ServiceException(ADDING_COMMENT_ERROR);
			}
		} 
		catch (DAOException e) 
		{
			throw new ServiceException(e);
		}
		
		return commentAdded;
	}
}
