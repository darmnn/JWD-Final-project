package by.tc.photobook.dao;

import java.util.List;

import by.tc.photobook.bean.Comment;

/**
 * The interface for working with database table that stored all comments
 * 
 * @author Darya Minina
 */
public interface CommentsDAO 
{
	/**
	 * Get all comments to a photo
	 * 
	 * @param photoId  id of a photo
	 * @return {@link Comment} list of comments
	 * @throws DAOException
	 */
	public List<Comment> takePhotoComments(int photoId) throws DAOException;
	
	/**
	 * Leaves new comment to a photo
	 * 
	 * @param comment new comment
	 * @param photoId id of a photo
	 * @param authorId - user who left the comment
	 * @return true if the operation was successful
	 * @throws DAOException
	 */
	public boolean loadNewComment(Comment comment, int photoId, int authorId) throws DAOException;
	
	/**
	 * Deletes one comment specified by its id
	 * 
	 * @param commentId id of a comment to delete
	 * @return true if the operation was successful
	 * @throws DAOException
	 */
	public boolean deleteComment(int commentId) throws DAOException;
}
