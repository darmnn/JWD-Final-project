package by.tc.photobook.dao;

import java.util.List;

import by.tc.photobook.bean.Complaint;

public interface ComplaintsDAO 
{
	public boolean addComplaint(Complaint newComplaint) throws DAOException;
	public List<Complaint> getAllComplaints() throws DAOException;
	public void viewComplaint(int complaintId) throws DAOException;
}
