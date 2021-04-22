package by.tc.photobook.service;

import java.util.List;


import by.tc.photobook.bean.Comment;

/**
 * The interface to operate with comments
 * 
 * @author Darya Minina
 */
public interface CommentsService 
{
	/**
	 * Get all comments to a photo
	 * 
	 * @param photoId  id of a photo
	 * @return {@link Comment} list of comments
	 * @throws ServiceException
	 */
	public List<Comment> takePhotoComments(int photoId) throws ServiceException;
	
	/**
	 * Leaves new comment to a photo
	 * 
	 * @param comment new comment
	 * @param photoId id of a photo
	 * @param authorId  user who left the comment
	 * @return true if the operation was successful
	 * @throws ServiceException
	 */
	public boolean loadNewComment(Comment comment, int photoId, int authorId) throws ServiceException;
	
	/**
	 * Deletes one comment specified by its id
	 * 
	 * @param commentId id of a comment to delete
	 * @return true if the operation was successful
	 * @throws ServiceException
	 */
	public boolean deleteComment(int commentId) throws ServiceException;
}
