package by.tc.photobook.dao;


public class DriverLoader 
{
	private static final String DRIVER_CLASSNAME = "com.mysql.cj.jdbc.Driver";
	private static final DriverLoader instance = new DriverLoader();

	static {
		try {
			Class.forName(DRIVER_CLASSNAME);
		} catch (ClassNotFoundException e) {
			System.out.print(e);
		}
			
	}
	
	private DriverLoader() {}
	
	public static DriverLoader getInstance() {
		return instance;
	}
}
