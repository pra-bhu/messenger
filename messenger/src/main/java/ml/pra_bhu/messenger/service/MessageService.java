package ml.pra_bhu.messenger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import ml.pra_bhu.messenger.database.DatabaseClass;
import ml.pra_bhu.messenger.exception.DataNotFoundException;
import ml.pra_bhu.messenger.model.Message;

public class MessageService {

	private Map<Long , Message> messages = DatabaseClass.getMessages();
	
	public MessageService() {
		messages.put(1L, new Message(1L, "Hello World!", "Prashant"));
		messages.put(2L, new Message(2L, "Hello Prashant!", "Prashant"));
		
		messages.put(3L, new Message(3L, "Hello Bhushan!", "Bhushan"));
	}
	
	
	public List<Message> getAllMessages(){
		return new ArrayList<Message>(messages.values());
	}
	
	public List<Message> getAllMessagesByYear(int year){
		List<Message> messageByYear = new ArrayList<Message>();
		Calendar calendar= Calendar.getInstance();
		for(Message message: messages.values()) {
			calendar.setTime(message.getCreated());
			if(calendar.get(Calendar.YEAR) == year) {
				messageByYear.add(message);
			}
		}
		
		return messageByYear;
	}
	
	public List<Message> getAllMessagesPaginated(int start, int size){
		ArrayList<Message> list = new ArrayList<Message>(messages.values());
		
		return list.subList(start, size);
	}
	
	
	public Message getMessage(long id) {
		Message message = messages.get(id);
		if(message==null) {
			throw new DataNotFoundException("Message with ID: " + id + " not found");
		}
		return messages.get(id);
	}
	
	public Message addMessage(Message message) {
		message.setId(messages.size()+1);
		messages.put(message.getId(), message);
		return message;
	}
	
	public Message updateMessage(Message message) {
		if(message.getId()<=0) {
			return null;
		}
		messages.put(message.getId(), message);
		return message;
	}
	
	public Message removeMessage(long id) {
		return messages.remove(id);
	}
}
