package by.tc.photobook.service;

import java.util.List;

import by.tc.photobook.bean.Complaint;

public interface ComplaintsService 
{
	public boolean addComplaint(Complaint newComplaint) throws ServiceException;
	public List<Complaint> getAllComplaints() throws ServiceException;
	public void viewComplaint(int complaintId) throws ServiceException;
}
