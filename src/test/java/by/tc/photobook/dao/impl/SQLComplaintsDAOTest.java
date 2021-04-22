package by.tc.photobook.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;

import by.tc.photobook.bean.Complaint;
import by.tc.photobook.bean.Photo;
import by.tc.photobook.dao.connection.ConnectionException;
import by.tc.photobook.dao.connection.ConnectionPool;

public class SQLComplaintsDAOTest 
{
	private static final String ADD_COMMENT_COMPLAINT = "INSERT INTO complaints(complaint_text, user, comment_id, photo_id) VALUES(?, ?, ?, ?)";
	private static final String ADD_COMPLAINT = "INSERT INTO complaints(complaint_text, user, photo_id) VALUES(?, ?, ?)";
	private static final String MARK_AS_VIEWED = "UPDATE complaints SET state=2 WHERE id=?";
	
	@Test
	public void addComplaint() throws ConnectionException, SQLException
	{
		final ConnectionPool connectionPool = ConnectionPool.getInstance();
		connectionPool.init();
		
		Photo photo = new Photo(40, "existingUser", LocalDate.now(), "images/3.jpg", 0);
		Complaint newComplaint = new Complaint("new complaint text", "existingUser", null,  photo);
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = null;
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
		
		Assert.assertTrue(preparedStatement.executeUpdate() == 1);
		connectionPool.destroy();
	}
	
	@Test
	public void viewComplaint() throws ConnectionException, SQLException
	{
		final ConnectionPool connectionPool = ConnectionPool.getInstance();
		connectionPool.init();
		
		int complaintId = 1;
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(MARK_AS_VIEWED);
		preparedStatement.setInt(1, complaintId);
		
		Assert.assertTrue(preparedStatement.executeUpdate() == 1);
		connectionPool.destroy();
	}
}
