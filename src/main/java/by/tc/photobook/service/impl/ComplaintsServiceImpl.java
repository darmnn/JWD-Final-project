package by.tc.photobook.service.impl;

import java.util.List;

import by.tc.photobook.bean.Complaint;
import by.tc.photobook.dao.ComplaintsDAO;
import by.tc.photobook.dao.DAOException;
import by.tc.photobook.dao.DAOProvider;
import by.tc.photobook.service.ComplaintsService;
import by.tc.photobook.service.ServiceException;

public class ComplaintsServiceImpl implements ComplaintsService
{
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
