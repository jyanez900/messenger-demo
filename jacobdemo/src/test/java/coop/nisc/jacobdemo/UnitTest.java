package coop.nisc.jacobdemo;

import coop.nisc.courier.pojos.RegisteredContactStatus;
import coop.nisc.courier.pojos.v3.RegisteredContact;
import coop.nisc.courier.v3.subcription.MessengerSubscriberId;
import coop.nisc.courier.v3.transport.EmailMessengerTransport;
import coop.nisc.courier.v3.transport.MessengerTransport;
import coop.nisc.courier.v3.transport.SMSMessengerTransport;
import coop.nisc.jacobdemo.web.Demo;
import coop.nisc.jacobdemo.web.MessengerClient;
import coop.nisc.jacobdemo.web.VariableTarget;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class UnitTest {
    @Mock
    private MessengerClient client;

    @Mock
    private VariableTarget<MessengerClient> target;

    private Demo demoController;

    private final String DOMAIN = "gotham";
    private final String SMARTHUB = "jacob.yanez@nisc.coop";

    private MessengerSubscriberId id = new MessengerSubscriberId(SMARTHUB, DOMAIN);


    @Before
    public void setUp(){
        initMocks(this);

        demoController = new Demo(client, target);
    }

    @Test
    public void basicTest() {
        when(client.getList(id)).thenReturn(Arrays.asList(RegisteredContact.builder().build()));
        List<RegisteredContact> result = demoController.getContacts("jacob.yanez@nisc.coop", "gotham");
        verify(target).setTarget(DOMAIN);
        verify(target).clear();
        assertEquals(Arrays.asList(RegisteredContact.builder().build()), result);
    }

    @Test
    public void phoneTest(){
        List<RegisteredContact> list = new ArrayList<RegisteredContact>();
        list.add(new RegisteredContact(
                     new SMSMessengerTransport(123,123,5481),
                     new MessengerSubscriberId("jacob.yanez@nisc.coop", "gotham"),
                     "20",
                     null,
                     "code")
        );
        when(client.getList(id)).thenReturn(list);
        String check = "5551235481";
        List<RegisteredContact> result = demoController.update("jacob.yanez@nisc.coop", "gotham");
        assertEquals(result.get(0).getTransport().getDeliveryAddress(), check);
    }

    @Test
    public void emailTest(){
        List<RegisteredContact> list = new ArrayList<RegisteredContact>();
        list.add(new RegisteredContact(
                new EmailMessengerTransport("jacob.yanez@nisc.coop"),
                new MessengerSubscriberId("jacob.yanez@nisc.coop", "gotham"),
                "20",
                null,
                "code")
        );
        when(client.getList(id)).thenReturn(list);
        String check = "jacob.yanez1@nisc.coop";
        List<RegisteredContact> result = demoController.update("jacob.yanez@nisc.coop", "gotham");
        assertEquals(result.get(0).getTransport().getDeliveryAddress(), check);
    }

    @Test
    public void  noSMSContacts() {
        List<RegisteredContact> list = new ArrayList<RegisteredContact>();
        RegisteredContact contact = new RegisteredContact(
                new EmailMessengerTransport("jacob.yanez@nisc.coop"),
                new MessengerSubscriberId("jacob.yanez@nisc.coop", "gotham"),
                "20",
                null,
                "code");
        list.add(contact);
        when(client.getList(id)).thenReturn(list);
        List<RegisteredContact> result = demoController.getContacts("jacob.yanez@nisc.coop", "gotham");
        assertEquals(result, list);
    }

    @Test
    public void  noEmailContacts() {
        List<RegisteredContact> list = new ArrayList<RegisteredContact>();
        RegisteredContact contact = new RegisteredContact(
                new SMSMessengerTransport(123,123,5481),
                new MessengerSubscriberId("jacob.yanez@nisc.coop", "gotham"),
                "20",
                null,
                "code");
        list.add(contact);
        when(client.getList(id)).thenReturn(list);
        List<RegisteredContact> result = demoController.getContacts("jacob.yanez@nisc.coop", "gotham");
        assertEquals(result, list);
    }

    @Test
    public void emptyList(){
        when(client.getList(id)).thenReturn(new ArrayList<RegisteredContact>());
        try{
            List<RegisteredContact> result = demoController.getContacts("jacob.yanez@nisc.coop", "gotham");
        } catch(IndexOutOfBoundsException e){}
        verify(target).clear();
    }

    @Test
    public void  nullTest() {
        when(client.getList(id)).thenThrow(NullPointerException.class);
        try{
            List<RegisteredContact> result = demoController.getContacts("jacob.yanez@nisc.coop", "gotham");
        } catch(NullPointerException e){}
        verify(target).clear();
    }

}
