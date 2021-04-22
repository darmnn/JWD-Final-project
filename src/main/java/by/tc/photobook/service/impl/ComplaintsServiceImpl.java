package by.tc.photobook.service.impl;

import java.util.List;

import by.tc.photobook.bean.Comment;
import by.tc.photobook.bean.Complaint;
import by.tc.photobook.dao.ComplaintsDAO;
import by.tc.photobook.dao.DAOException;
import by.tc.photobook.dao.DAOProvider;
import by.tc.photobook.service.ComplaintsService;
import by.tc.photobook.service.ServiceException;

/**
 * The implementation of operations with reports
 * 
 * @author Darya Minina
 */
public class ComplaintsServiceImpl implements ComplaintsService
{
	/**
	 * Leaves new complaint to a photo or comment
	 * 
	 * @param newComplaint {@link Complaint} information about new complaint
	 * @return true if the operation was successful
	 * @throws ServiceException
	 */
	@Override
	public boolean addComplaint(Complaint newComplaint) throws ServiceException 
	{
		DAOProvider daoProvider = DAOProvider.getInstance();
		ComplaintsDAO complaintsDAO = daoProvider.getComplaintsDAO();
		
		try 
		{
			complaintsDAO.addComplaint(newComplaint);
		} 
		catch (DAOException e) 
		{
			throw new ServiceException(e.getDescription(), e);
		}
		
		return true;
	}
	
	/**
	 * Get all reports
	 * 
	 * @return {@link Comment} list of comments
	 * @throws ServiceException
	 */
	public List<Complaint> getAllComplaints() throws ServiceException
	{
		DAOProvider daoProvider = DAOProvider.getInstance();
		ComplaintsDAO complaintsDAO = daoProvider.getComplaintsDAO();
		List<Complaint> allComplaints;
		
		try 
		{
			allComplaints = complaintsDAO.getAllComplaints();
		} 
		catch (DAOException e) 
		{
			throw new ServiceException(e.getDescription(), e);
		}
		
		return allComplaints;
	}
	
	/**
	 * Marks one report as viewed by the administrator
	 * 
	 * @param complaintId the id of the report to mark
	 * @return true if the operation was successful
	 * @throws ServiceException
	 */
	public void viewComplaint(int complaintId) throws ServiceException
	{
		DAOProvider daoProvider = DAOProvider.getInstance();
		ComplaintsDAO complaintsDAO = daoProvider.getComplaintsDAO();
		
		try
		{
			complaintsDAO.viewComplaint(complaintId);
		}
		catch (DAOException e) 
		{
			throw new ServiceException(e.getDescription(), e);
		}
	}
}
