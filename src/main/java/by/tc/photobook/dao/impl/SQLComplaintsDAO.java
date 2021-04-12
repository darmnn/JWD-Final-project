package by.tc.photobook.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import by.tc.photobook.bean.Complaint;
import by.tc.photobook.bean.Photo;
import by.tc.photobook.dao.ComplaintsDAO;
import by.tc.photobook.dao.DAOException;
import by.tc.photobook.dao.connection.ConnectionException;
import by.tc.photobook.dao.connection.ConnectionPool;

public class SQLComplaintsDAO implements ComplaintsDAO
{
	ConnectionPool connectionPool = ConnectionPool.getInstance();
	private static final Logger log = Logger.getLogger(SQLComplaintsDAO.class);
	
	private static final String ERROR_WHILE_CLOSING_STATEMENT = "Error while closing statement: ";
	private static final String ERROR_WHILE_CLOSING_RESULTSET = "Error while closing result set: ";
	
	private static final String SERVER_ERROR = "message.server_error";
	private static final String REPORTING_ERROR = "message.add_complaint_error";
	private static final String VIEW_COMPLAINT_ERROR = "message.view_complaint_error";
	
	private static final String ADD_COMPLAINT = "INSERT INTO complaints(complaint_text, user, photo_id) VALUES(?, ?, ?)";
	private static final String ADD_COMMENT_COMPLAINT = "INSERT INTO complaints(complaint_text, user, comment_id, photo_id) VALUES(?, ?, ?, ?)";
	private static final String GET_COMMENTS_COMPLAINTS = "SELECT complaints.id, complaints.complaint_text, "
			+ "complaints.user, complaints.comment_id, comments.comment_text, photos.photo_id, users.username, "
			+ "photos.date_time, photos.image, photos.rating, complaints.state FROM complaints JOIN comments ON "
			+ "complaints.comment_id = comments.comment_id JOIN photos ON complaints.photo_id = photos.photo_id  "
			+ "JOIN users ON users.id_user = photos.photographer";
	private static final String GET_ALL_COMPLAINTS = "SELECT complaints.id, complaints.complaint_text, complaints.user,  photos.photo_id, users.username, photos.date_time, photos.image, photos.rating, complaints.state FROM complaints JOIN photos ON complaints.photo_id = photos.photo_id  JOIN users ON users.id_user = photos.photographer";
	private static final String MARK_AS_VIEWED = "UPDATE complaints SET state=2 WHERE id=?";
	
	@Override
	public boolean addComplaint(Complaint newComplaint) throws DAOException 
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
			if(newComplaint.getCommentId() == null)
			{
				preparedStatement = connection.prepareStatement(ADD_COMPLAINT);
				preparedStatement.setString(1, newComplaint.getText());
				preparedStatement.setString(2, newComplaint.getUser());
				preparedStatement.setInt(3, newComplaint.getPhoto().getId());
			}
			else
			{
				preparedStatement = connection.prepareStatement(ADD_COMMENT_COMPLAINT);
				preparedStatement.setString(1, newComplaint.getText());
				preparedStatement.setString(2, newComplaint.getUser());
				preparedStatement.setInt(3, newComplaint.getCommentId());
				preparedStatement.setInt(4, newComplaint.getPhoto().getId());
			}

			
			if(preparedStatement.executeUpdate() < 1)
			{
				throw new DAOException(REPORTING_ERROR);
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

	public List<Complaint> getAllComplaints() throws DAOException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try
		{
			 connection = connectionPool.getConnection();
		}
		catch (ConnectionException e) 
		{
			throw new DAOException(SERVER_ERROR, e);
		}
		
		List<Complaint> allComplaints = new ArrayList<Complaint>();
		
		try
		{
			preparedStatement = connection.prepareStatement(GET_COMMENTS_COMPLAINTS);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next())
			{
				Photo photo = new Photo(resultSet.getInt(6), resultSet.getString(7), resultSet.getDate(8).toLocalDate(), 
						resultSet.getString(9), resultSet.getInt(10));
				Complaint complaint = new Complaint(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), 
						resultSet.getInt(4), resultSet.getString(5), photo, resultSet.getInt(11));
				
				allComplaints.add(complaint);
			}
			
			preparedStatement = connection.prepareStatement(GET_ALL_COMPLAINTS);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next())
			{
				Photo photo = new Photo(resultSet.getInt(4), resultSet.getString(5), resultSet.getDate(6).toLocalDate(), 
						resultSet.getString(7), resultSet.getInt(8));
				Complaint complaint = new Complaint(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), 
						photo, resultSet.getInt(9));
				if(!allComplaints.contains(complaint))
				{
					allComplaints.add(complaint);
				}
			}
			
			
		}
		catch (SQLException e) 
		{
			log.error(e);
			throw new DAOException(SERVER_ERROR, e);
		}
		finally
		{
			closeAll(resultSet, preparedStatement, connection);
		}
		
		return allComplaints;
	}
	
	public void viewComplaint(int complaintId) throws DAOException
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
			preparedStatement = connection.prepareStatement(MARK_AS_VIEWED);
			preparedStatement.setInt(1, complaintId);
			
			if(preparedStatement.executeUpdate() < 1)
			{
				throw new DAOException(VIEW_COMPLAINT_ERROR);
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
