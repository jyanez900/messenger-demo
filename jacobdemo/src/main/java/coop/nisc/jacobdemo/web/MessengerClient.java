package coop.nisc.jacobdemo.web;

        import coop.nisc.courier.pojos.v3.RegisteredContact;
        import coop.nisc.courier.v3.MessengerPaths;
        import coop.nisc.courier.v3.subcription.MessengerSubscriberId;
        import org.springframework.web.bind.annotation.PostMapping;

        import javax.ws.rs.core.MediaType;
        import java.util.List;



public interface MessengerClient {
    String MAIN_PATH = MessengerPaths.REGISTERED_CONTACTS_PATH;

    @PostMapping(path= MAIN_PATH + MessengerPaths.REGISTERED_CONTACT_GET_BY_SUBSCRIBER_ID,consumes=MediaType.APPLICATION_JSON,produces=MediaType.APPLICATION_JSON)
    public List<RegisteredContact> getList(MessengerSubscriberId subscriberID);

    @PostMapping(path= MAIN_PATH +  "/add",consumes=MediaType.APPLICATION_JSON)
    public void addContact(RegisteredContact registeredContact);

    @PostMapping(path= MAIN_PATH + MessengerPaths.REGISTERED_CONTACT_DELETE,consumes=MediaType.APPLICATION_JSON)
    public void deleteContact(RegisteredContact registeredContact);
}
