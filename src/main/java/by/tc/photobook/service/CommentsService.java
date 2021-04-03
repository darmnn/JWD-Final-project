package by.tc.photobook.service;

import java.util.List;

import by.tc.photobook.bean.Comment;

public interface CommentsService 
{
	public List<Comment> takePhotoComments(int photoId) throws ServiceException;
	public boolean loadNewComment(Comment comment, int photoId, int authorId) throws ServiceException;
}
