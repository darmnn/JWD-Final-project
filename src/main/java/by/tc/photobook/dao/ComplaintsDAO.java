package by.tc.photobook.dao;

import java.util.List;

import by.tc.photobook.bean.Comment;
import by.tc.photobook.bean.Complaint;

/**
 * The interface for working with database table that stored all reports
 * 
 * @author Darya Minina
 */
public interface ComplaintsDAO 
{
	/**
	 * Leaves new complaint to a photo or comment
	 * 
	 * @param newComplaint {@link Complaint} information about new complaint
	 * @return true if the operation was successful
	 * @throws DAOException
	 */
	public boolean addComplaint(Complaint newComplaint) throws DAOException;
	
	/**
	 * Get all reports
	 * 
	 * @return {@link Comment} list of comments
	 * @throws DAOException
	 */
	public List<Complaint> getAllComplaints() throws DAOException;
	
	/**
	 * Marks one report as viewed by the administrator
	 * 
	 * @param complaintId the id of the report to mark
	 * @return true if the operation was successful
	 * @throws DAOException
	 */
	public void viewComplaint(int complaintId) throws DAOException;
}
