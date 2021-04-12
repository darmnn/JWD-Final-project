package by.tc.photobook.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import by.tc.photobook.bean.Comment;
import by.tc.photobook.dao.CommentsDAO;
import by.tc.photobook.dao.DAOException;
import by.tc.photobook.dao.connection.ConnectionException;
import by.tc.photobook.dao.connection.ConnectionPool;

public class SQLCommentsDAO implements CommentsDAO
{
	private static final String GET_COMMENTS_TO_PHOTO = "SELECT comments.comment_id, comments.comment_text, users.username, comments.date_time, "
			+ "comments.author_pic FROM comments JOIN users ON users.id_user = comments.author_id WHERE "
			+ "comments.photo_id = ?";
	private static final String ADD_NEW_COMMENT = "INSERT INTO comments(comment_text, photo_id, author_id, date_time, author_pic) "
			+ "VALUES(?, ?, ?, ?, ?)";
	private static final String DELETE_COMMENT= "DELETE FROM comments WHERE comment_id=?";
	
	private static final String GET_COMMENTS_ERROR = "Error while loading comments";
	private static final String ERROR_WHILE_CLOSING_STATEMENT = "Error while closing statement: ";
	private static final String ERROR_WHILE_CLOSING_RESULTSET = "Error while closing result set: ";
	
	private static final String ERROR_DELETING_COMMENT = "message.delete_comment_error";
	private static final String SERVER_ERROR = "message.server_error";
	private static final String ADD_COMMENT_ERROR = "message.add_comment_error";
	
	ConnectionPool connectionPool = ConnectionPool.getInstance();
	private static final Logger log = Logger.getLogger(SQLCommentsDAO.class);
	
	public List<Comment> takePhotoComments(int photoId) throws DAOException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		List<Comment> comments = new ArrayList<Comment>();
		
		try 
		{
			connection = connectionPool.getConnection();
		} 
		catch (ConnectionException e) 
		{
			throw new DAOException(SERVER_ERROR, e);
		}
			
		try
		{
			preparedStatement = connection.prepareStatement(GET_COMMENTS_TO_PHOTO);
			preparedStatement.setInt(1, photoId);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next())
			{
				comments.add(new Comment(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getDate(4).toLocalDate(), 
						resultSet.getString(5)));
			}
		} 
		catch (SQLException e) 
		{
			log.error(GET_COMMENTS_ERROR+e);
			throw new DAOException(SERVER_ERROR, e);
		}
		finally
		{
			closeAll(resultSet, preparedStatement, connection);
		}	
		
		return comments;
	}
	
	public boolean loadNewComment(Comment comment, int photoId, int authorId) throws DAOException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try
		{
			 connection = connectionPool.getConnection();
		}
		catch (ConnectionException e) 
		{
			throw new DAOException(SERVER_ERROR, e);
		}
		
		try 
		{
			preparedStatement = connection.prepareStatement(ADD_NEW_COMMENT);
			preparedStatement.setString(1, comment.getText());
			preparedStatement.setInt(2, photoId);
			preparedStatement.setInt(3, authorId);
			preparedStatement.setDate(4, Date.valueOf(comment.getDate()));
			preparedStatement.setString(5, comment.getAuthorPic());
			
			if(preparedStatement.executeUpdate() < 1)
			{
				throw new DAOException(ADD_COMMENT_ERROR);
			}
		} 
		catch (SQLException e) 
		{
			log.error(e);
			throw new DAOException(SERVER_ERROR, e);
		}
		finally
		{
			closeAll(null, preparedStatement, connection);
		}
		
		return true;
	}
	
	public boolean deleteComment(int commentId) throws DAOException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try
		{
			 connection = connectionPool.getConnection();
		}
		catch (ConnectionException e) 
		{
			throw new DAOException(SERVER_ERROR, e);
		}
		
		try
		{
			preparedStatement = connection.prepareStatement(DELETE_COMMENT);
			preparedStatement.setInt(1, commentId);
			
			if(preparedStatement.executeUpdate() < 1)
			{
				throw new DAOException(ERROR_DELETING_COMMENT);
			}
		}
		catch (SQLException e) 
		{
			log.error(e);
			throw new DAOException(SERVER_ERROR, e);
		}
		finally
		{
			closeAll(null, preparedStatement, connection);
		}
		return true;
	}
	
	private void closeAll(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection)
	{
		if(resultSet != null)
		{
			try 
			{
				resultSet.close();
			} 
			catch (SQLException e) 
			{
				log.error(ERROR_WHILE_CLOSING_RESULTSET + e.getMessage());
			}
		}
		
		if(preparedStatement != null)
		{
			try 
			{
				preparedStatement.close();
			} 
			catch (SQLException e) 
			{
				log.error(ERROR_WHILE_CLOSING_STATEMENT + e.getMessage());
			}
		}
		
		if(connection != null)
		{
			connectionPool.releaseConnection(connection);
		}
	}
}
