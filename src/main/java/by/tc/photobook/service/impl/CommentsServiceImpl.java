package by.tc.photobook.service.impl;

import java.util.List;

import by.tc.photobook.bean.Comment;
import by.tc.photobook.dao.CommentsDAO;
import by.tc.photobook.dao.DAOException;
import by.tc.photobook.dao.DAOProvider;
import by.tc.photobook.service.CommentsService;
import by.tc.photobook.service.ServiceException;

/**
 * The implementation of operations with comments
 * 
 * @author Darya Minina
 */
public class CommentsServiceImpl implements CommentsService
{
	/**
	 * Get all comments to a photo
	 * 
	 * @param photoId  id of a photo
	 * @return {@link Comment} list of comments
	 * @throws ServiceException
	 */
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
	
	/**
	 * Leaves new comment to a photo
	 * 
	 * @param comment new comment
	 * @param photoId id of a photo
	 * @param authorId  user who left the comment
	 * @return true if the operation was successful
	 * @throws ServiceException
	 */
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
	
	/**
	 * Deletes one comment specified by its id
	 * 
	 * @param commentId id of a comment to delete
	 * @return true if the operation was successful
	 * @throws ServiceException
	 */
	public boolean deleteComment(int commentId) throws ServiceException
	{
		DAOProvider daoProvider = DAOProvider.getInstance();
		CommentsDAO commentsDAO = daoProvider.getCommentsDAO();
		
		try 
		{
			commentsDAO.deleteComment(commentId);
		} 
		catch (DAOException e) 
		{
			throw new ServiceException(e.getDescription(), e);
		}
		
		return true;
	}
}
