package by.tc.photobook.dao;


public class DriverLoader 
{
	private static final DriverLoader instance = new DriverLoader();

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.print(e);
		}
			
	}
	
	private DriverLoader() {}
	
	public static DriverLoader getInstance() {
		return instance;
	}
}
