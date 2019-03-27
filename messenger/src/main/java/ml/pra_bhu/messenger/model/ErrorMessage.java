package ml.pra_bhu.messenger.model;

import javax.ws.rs.core.Response.Status;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ErrorMessage {

	private String errorMessage;
	private Status errorCode;
	private String documentation;
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public Status getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(Status errorCode) {
		this.errorCode = errorCode;
	}
	public String getDocumentation() {
		return documentation;
	}
	public void setDocumentation(String documentation) {
		this.documentation = documentation;
	}
	public ErrorMessage(String errorMessage, Status notFound, String documentation) {
		super();
		this.errorMessage = errorMessage;
		this.errorCode = notFound;
		this.documentation = documentation;
	}
	public ErrorMessage() {
		
	}
	
	
}
