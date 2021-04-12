package by.tc.photobook.controller.parser;

import java.time.LocalDate;

import by.tc.photobook.bean.Photo;

public class PhotoInfoParser 
{
	private static final String ID = "id=";
	private static final String PH_NAME = "photographer=";
	private static final String IMAGE_PATH = "imagePath=";
	private static final String DATE = "date=";
	private static final String RATING = "rating=";
	private static final String COMMA = ",";
	private static final String CLOSING_BRACKET = ";";
	private static final String DATE_DELIMITER = "-";
	
	private static final PhotoInfoParser instance = new PhotoInfoParser();
	
	private PhotoInfoParser() {}
	
	public static PhotoInfoParser getInstance()
	{
		return instance;
	}
	
	private int getId(String photoInfo)
	{
		int start  = photoInfo.indexOf(ID);
        int end = photoInfo.indexOf(COMMA, start);
        int id = Integer.parseInt(photoInfo.substring(start + ID.length(), end));
        
        return id;
	}
	
	private String getPhotographer(String photoInfo)
	{
		int start  = photoInfo.indexOf(PH_NAME);
        int end = photoInfo.indexOf(COMMA, start);
        String photographer = photoInfo.substring(start + PH_NAME.length(), end);
        
        return photographer;
	}
	
	private String getImagePath(String photoInfo)
	{
		int start  = photoInfo.indexOf(IMAGE_PATH);
        int end = photoInfo.indexOf(COMMA, start);
        String imagePath = photoInfo.substring(start + IMAGE_PATH.length(), end);
        
        return imagePath;
	}
	
	private LocalDate getDate(String photoInfo)
	{
		int start  = photoInfo.indexOf(DATE);
        int end = photoInfo.indexOf(COMMA, start); 
        String[] dateStrings = photoInfo.substring(start + DATE.length(), end).split(DATE_DELIMITER);
        
        int year = Integer.parseInt(dateStrings[0]);
        int month = Integer.parseInt(dateStrings[1]);
        int day = Integer.parseInt(dateStrings[2]);
        
        LocalDate date = LocalDate.of(year, month, day);
        return date;
	}
	
	private int getRating(String photoInfo)
	{
		int start  = photoInfo.indexOf(RATING);
        int end = photoInfo.indexOf(CLOSING_BRACKET, start);
        int rating = Integer.parseInt(photoInfo.substring(start + RATING.length(), end));
        
        return rating;
	}
	
	public Photo getPhotoFromString(String photoInfo)
	{
		int id = this.getId(photoInfo);
		String photographer = this.getPhotographer(photoInfo);
		LocalDate date = this.getDate(photoInfo);
		String imagePath = this.getImagePath(photoInfo);
		int rating = this.getRating(photoInfo);
		
		return new Photo(id, photographer, date, imagePath, rating);
	}
}
