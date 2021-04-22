package by.tc.photobook.view;

import java.io.IOException;


import org.apache.log4j.Logger;

import jakarta.servlet.jsp.JspTagException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;

/**
 * Custom jstl tag
 * 
 * @author Darya Minina
 */
public class WelcomeTag extends TagSupport
{
	private static final Logger log = Logger.getLogger(WelcomeTag.class);
	private static final long serialVersionUID = 1L;
	
	private static final String WELCOME_MESSAGE = "Welcome to photobook! ";
	private static final String P_TAG_START = "<p>";
	private static final String P_TAG_END = "</p>";
	
	 @Override
	 public int doStartTag() throws JspTagException 
	 {
	        try 
	        {
	        	JspWriter out = pageContext.getOut();
	            out.write(P_TAG_START);
	            out.write(WELCOME_MESSAGE);
	            

	        } catch (IOException e) {
	        	log.error(e);
	            throw new JspTagException(e);
	        }
	        return EVAL_BODY_INCLUDE;
	    }


	    @Override
	    public int doEndTag() throws JspTagException 
	    {
	        try 
	        {
	        	JspWriter out = pageContext.getOut();
	        	out.write(P_TAG_END);
	        } 
	        catch (IOException e) 
	        {
	        	log.error(e);
	            throw new JspTagException(e);
	        }
	        return EVAL_PAGE;
	    }
}
