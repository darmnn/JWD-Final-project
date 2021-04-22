package by.tc.photobook.service;

import java.util.List;

import by.tc.photobook.bean.Comment;
import by.tc.photobook.bean.Complaint;

/**
 * The interface to operate with reports
 * 
 * @author Darya Minina
 */
public interface ComplaintsService 
{
	/**
	 * Leaves new complaint to a photo or comment
	 * 
	 * @param newComplaint {@link Complaint} information about new complaint
	 * @return true if the operation was successful
	 * @throws ServiceException
	 */
	public boolean addComplaint(Complaint newComplaint) throws ServiceException;
	
	/**
	 * Get all reports
	 * 
	 * @return {@link Comment} list of comments
	 * @throws ServiceException
	 */
	public List<Complaint> getAllComplaints() throws ServiceException;
	
	/**
	 * Marks one report as viewed by the administrator
	 * 
	 * @param complaintId the id of the report to mark
	 * @return true if the operation was successful
	 * @throws ServiceException
	 */
	public void viewComplaint(int complaintId) throws ServiceException;
}
