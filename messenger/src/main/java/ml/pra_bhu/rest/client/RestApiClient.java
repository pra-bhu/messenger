package ml.pra_bhu.rest.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import ml.pra_bhu.messenger.model.Message;

public class RestApiClient {

	public static void main(String[] args) {
		
		Client client = ClientBuilder.newClient();
		
		Response response = client.target("http://localhost:8080/messenger/webapi/messages/1").request().get();
		
		Message message = response.readEntity(Message.class);
		
		System.out.println("Message: " + message.getMessage());
	}

}
