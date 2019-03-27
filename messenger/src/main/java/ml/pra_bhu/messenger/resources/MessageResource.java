package ml.pra_bhu.messenger.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.sun.org.apache.xml.internal.serializer.utils.Messages;

import ml.pra_bhu.messenger.model.Message;
import ml.pra_bhu.messenger.resources.beans.MessageFilterBean;
import ml.pra_bhu.messenger.service.MessageService;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON) 
@Produces(MediaType.APPLICATION_JSON) 
//@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
public class MessageResource {

	MessageService messageService =new MessageService();
	
	/*
	 * @GET public List<Message> getMessages(@QueryParam("year") int year,
	 * 
	 * @QueryParam("start") int start,
	 * 
	 * @QueryParam("size") int size) {
	 * 
	 * if(year>0) { return messageService.getAllMessagesByYear(year); }
	 * 
	 * if(start > 0 && size > 0) { return
	 * messageService.getAllMessagesPaginated(start, size); } return
	 * messageService.getAllMessages(); }
	 */
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getJsonMessages(@BeanParam MessageFilterBean messageFilterBean) {
		
		if(messageFilterBean.getYear()>0) {
			return messageService.getAllMessagesByYear(messageFilterBean.getYear());
		}
		
		if(messageFilterBean.getStart() > 0 && messageFilterBean.getSize()> 0) {
			return messageService.getAllMessagesPaginated(messageFilterBean.getStart(), messageFilterBean.getSize());
		}
		return messageService.getAllMessages();
	}
	
	
	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Message> getXmlMessages(@BeanParam MessageFilterBean messageFilterBean) {
		System.out.println("Output is TEXT_XML");
		if(messageFilterBean.getYear()>0) {
			return messageService.getAllMessagesByYear(messageFilterBean.getYear());
		}
		
		if(messageFilterBean.getStart() > 0 && messageFilterBean.getSize()> 0) {
			return messageService.getAllMessagesPaginated(messageFilterBean.getStart(), messageFilterBean.getSize());
		}
		return messageService.getAllMessages();
	}
	
	@GET
	@Path("/{messageId}")
	public Message getMessage(@PathParam("messageId") long messageId,
								@Context UriInfo uriInfo) {
		 Message msg = messageService.getMessage(messageId);
		 msg.addLink(getUriForSelf(uriInfo, msg), "self");
		 msg.addLink(getUriForProfile(uriInfo, msg), "profile");
		 msg.addLink(getUriForComments(uriInfo, msg), "comments");
		 return msg;
	}




	private String getUriForComments(UriInfo uriInfo, Message msg) {
		return uriInfo.getBaseUriBuilder()
						.path(MessageResource.class)
						.path(MessageResource.class , "loadCommentResouces")
						.path(CommentResource.class) 
						.resolveTemplate("messageId", msg.getId())
						.build()
						.toString();
	}




	private String getUriForProfile(UriInfo uriInfo, Message msg) {
		return uriInfo.getBaseUriBuilder()
					  .path(ProfileResource.class)
					  .path(msg.getAuthor())
					  .build()
					  .toString();
	}




	private String getUriForSelf(UriInfo uriInfo, Message msg) {
		return uriInfo.getBaseUriBuilder()
				 .path(MessageResource.class)
				 .path(Long.toString(msg.getId()))
				 .build()
				 .toString();
	}
	
	@POST	
	public Response addMessage(@Context UriInfo uriInfo , Message message) throws URISyntaxException {
		Message newMessage= messageService.addMessage(message); 
		/*
		 * return Response.status(Status.CREATED) .entity(newMessage) .build();
		 */
		String newUri = uriInfo.getAbsolutePath().toString() + newMessage.getId();
		URI location = new URI(newUri);
		return Response.created(location)
				.entity(newMessage)
				.build();
//		return messageService.addMessage(message); 
	}
	
	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") long messageId, Message message) {
		message.setId(messageId); 
		return messageService.updateMessage(message);
	}
	
	@DELETE
	@Path("/{messageId}")
	public String deleteMessage(@PathParam("messageId") long messageId) {
	
	if(messageService.removeMessage(messageId)!= null) {
		return "Success";
	}
	return "Failure";
	}
	
	//Sub-resource Comments
	@Path("/{messageId}/comments")
	public CommentResource loadCommentResouces() {
		return new CommentResource();
	}
}
