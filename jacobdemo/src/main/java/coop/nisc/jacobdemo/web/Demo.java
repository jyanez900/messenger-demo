package coop.nisc.jacobdemo.web;

import coop.nisc.courier.pojos.v3.RegisteredContact;
import coop.nisc.courier.v3.subcription.MessengerSubscriberId;
import coop.nisc.courier.v3.transport.EmailMessengerTransport;
import coop.nisc.courier.v3.transport.MessengerTransport;
import coop.nisc.courier.v3.transport.SMSMessengerTransport;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.ws.rs.GET;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("test")
@RestController
public class Demo {
    MessengerClient client;
    String domain;
    String smarthubID;
    VariableTarget<MessengerClient> target;

    @Autowired
    public Demo(MessengerClient client, VariableTarget<MessengerClient> target) {
        this.client = client;
        this.domain = "";
        this.target = target;
    }

    @GetMapping
    public String print() {
        return "Hello World";
    }

    @PostMapping
    public String print2() {
        return "Hello World";
    }

    @GetMapping(value = "contacts/{smarthubID}/{domain}")
    public List<RegisteredContact> getContacts(@PathVariable("smarthubID") String smarthubID, @PathVariable("domain") String domain) {
        this.smarthubID = smarthubID;
        this.domain = domain;

        String message = "Finished";

        try {
            this.target.setTarget(domain);
            MessengerSubscriberId id = new MessengerSubscriberId(smarthubID, domain);
            List<RegisteredContact> contacts = client.getList(id);
            return contacts;
        }
          finally {
            target.clear();
        }
    }

    @PostMapping(value = "contacts/{smarthubID}/{domain}")
    public List<RegisteredContact> update(@PathVariable("smarthubID") String smarthubID, @PathVariable("domain") String domain) {
        this.smarthubID = smarthubID;
        this.domain = domain;

        String message = "Finished";

        try {
            this.target.setTarget(domain);
            MessengerSubscriberId id = new MessengerSubscriberId(smarthubID, domain);
            List<RegisteredContact> contacts = client.getList(id);
            if (contacts.get(0).getRegisteredContactId() != null)
                contacts = updateContacts(contacts);
            return contacts;
        }
        finally {
            target.clear();
        }
    }


    public List<RegisteredContact> updateContacts(List<RegisteredContact> contacts) {
        int emailNum  = 1;
        for (int i = 0; i < contacts.size(); i++) {
            RegisteredContact c = contacts.get(i);
            MessengerTransport transport = c.getTransport();
            if (transport.getType().getDisplayName().equals("SMS")) {
                SMSMessengerTransport newTransport = new SMSMessengerTransport(555, 123, Integer.parseInt(transport.getDeliveryAddress().substring(6)));
                RegisteredContact newContact = new RegisteredContact(newTransport, c.getSubscriberId(), c.getRegisteredContactId(), c.getRegisteredContactStatus(), c.getValidationCode());
                client.deleteContact(c);
                client.addContact(newContact);
                contacts.set(i, newContact);
            }
            else if (transport.getType().getDisplayName().equals("E-Mail")) {
                EmailMessengerTransport newTransport = new EmailMessengerTransport(smarthubID.substring(0, smarthubID.indexOf('@')) + emailNum + smarthubID.substring(smarthubID.indexOf('@')));
                RegisteredContact newContact = new RegisteredContact(newTransport, c.getSubscriberId(), c.getRegisteredContactId(), c.getRegisteredContactStatus(), c.getValidationCode());
                client.deleteContact(c);
                client.addContact(newContact);
                contacts.set(i, newContact);
                emailNum++;
            }
        }
        return contacts;
    }
}
