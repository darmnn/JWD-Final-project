package by.tc.photobook.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;

import by.tc.photobook.bean.Comment;
import by.tc.photobook.dao.connection.ConnectionException;
import by.tc.photobook.dao.connection.ConnectionPool;

public class SQLCommentsDAOTest 
{
	private static final String ADD_NEW_COMMENT = "INSERT INTO comments(comment_text, photo_id, author_id, date_time, author_pic) "
			+ "VALUES(?, ?, ?, ?, ?)";
	private static final String DELETE_COMMENT= "DELETE FROM comments WHERE comment_id=?";
	
	@Test
	public void loadNewCommentTest() throws ConnectionException, SQLException
	{
		final ConnectionPool connectionPool = ConnectionPool.getInstance();
		connectionPool.init();
		Comment comment = new Comment("some comment text", "existingUser", LocalDate.now(), null);
		int photoId = 40;
		int authorId = 1;
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_COMMENT);
		preparedStatement.setString(1, comment.getText());
		preparedStatement.setInt(2, photoId);
		preparedStatement.setInt(3, authorId);
		preparedStatement.setDate(4, Date.valueOf(comment.getDate()));
		preparedStatement.setString(5, comment.getAuthorPic());
		
		
		Assert.assertTrue(preparedStatement.executeUpdate() == 1);
		connectionPool.destroy();
	}
	
	@Test
	public void deleteComment() throws ConnectionException, SQLException
	{
		final ConnectionPool connectionPool = ConnectionPool.getInstance();
		connectionPool.init();
		int commentId = 3;
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(DELETE_COMMENT);
		preparedStatement.setInt(1, commentId);
		
		Assert.assertTrue(preparedStatement.executeUpdate() == 1);
		connectionPool.destroy();
	}
}
