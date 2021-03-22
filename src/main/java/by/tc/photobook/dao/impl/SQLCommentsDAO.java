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
	private static final String GET_COMMENTS_TO_PHOTO = "SELECT comments.comment_text, users.username, comments.date_time, "
			+ "comments.author_pic FROM comments JOIN users ON users.id_user = comments.author_id WHERE "
			+ "comments.photo_id = ?";
	private static final String ADD_NEW_COMMENT = "INSERT INTO comments(comment_text, photo_id, author_id, date_time, author_pic) "
			+ "VALUES(?, ?, ?, ?, ?)";
	
	private static final String GET_COMMENTS_ERROR = "Error while loading comments";
	private static final String ADD_COMMENT_ERROR = "Error while adding new comment";
	private static final String ERROR_WHILE_CLOSING_CONNECTION = "Error while closing connection: ";
	private static final String ERROR_WHILE_CLOSING_STATEMENT = "Error while closing statement: ";
	private static final String ERROR_WHILE_CLOSING_RESULTSET = "Error while closing result set: ";
	
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
			throw new DAOException(e);
		}
		
			
		try
		{
			preparedStatement = connection.prepareStatement(GET_COMMENTS_TO_PHOTO);
			preparedStatement.setInt(1, photoId);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next())
			{
				comments.add(new Comment(resultSet.getString(1), resultSet.getString(2), resultSet.getDate(3).toLocalDate(), 
						resultSet.getString(4)));
			}
		} 
		catch (SQLException e) 
		{
			log.error(GET_COMMENTS_ERROR);
			throw new DAOException(e);
		}
		finally
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
			throw new DAOException(e);
		}
		
		try 
		{
			preparedStatement = connection.prepareStatement(ADD_NEW_COMMENT);
			preparedStatement.setString(1, comment.getText());
			preparedStatement.setInt(2, photoId);
			System.out.println(authorId);
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
			log.error(ADD_COMMENT_ERROR + e.getMessage());
			throw new DAOException(e);
		}
		finally
		{
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
		
		return true;
	}
}
