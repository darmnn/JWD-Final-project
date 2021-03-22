package by.tc.photobook.dao;

import java.util.List;

import by.tc.photobook.bean.Comment;

public interface CommentsDAO 
{
	public List<Comment> takePhotoComments(int photoId) throws DAOException;
	public boolean loadNewComment(Comment comment, int photoId, int authorId) throws DAOException;
}
