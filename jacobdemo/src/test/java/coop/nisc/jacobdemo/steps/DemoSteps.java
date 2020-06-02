package coop.nisc.jacobdemo.steps;

import coop.nisc.courier.pojos.RegisteredContactStatus;
import coop.nisc.courier.pojos.v3.RegisteredContact;
import coop.nisc.courier.utils.MessengerGson;
import coop.nisc.courier.v3.subcription.MessengerSubscriberId;
import coop.nisc.courier.v3.transport.EmailMessengerTransport;
import coop.nisc.courier.v3.transport.SMSMessengerTransport;
import coop.nisc.jacobdemo.util.TestClient;
import coop.nisc.jacobdemo.web.MessengerClient;
import feign.Feign;
import feign.FeignException;
import feign.auth.BasicAuthRequestInterceptor;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.cloud.openfeign.support.SpringMvcContract;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DemoSteps {

    RegisteredContact contact1 = new RegisteredContact(
            new EmailMessengerTransport("victor.delgado1@nisc.coop"),
            new MessengerSubscriberId("victor.delgado@nisc.coop", "gotham"),
            "c5ba4350-d571-11e9-9d7f-eee3677750f2",
             RegisteredContactStatus.ACTIVE,
            null);

    RegisteredContact contact2 = new RegisteredContact(
            new SMSMessengerTransport(555,123,5481),
            new MessengerSubscriberId("victor.delgado@nisc.coop", "gotham"),
            "1fed6b00-a4c0-11e9-8be5-0a580a020d78",
            RegisteredContactStatus.ACTIVE,
            null);

    List<RegisteredContact> victorList = new ArrayList<RegisteredContact>(Arrays.asList(contact1, contact2));

    List<RegisteredContact> result;

    boolean error = false;


    TestClient client = Feign.builder()
            .contract(new SpringMvcContract())
            .decoder(new GsonDecoder(MessengerGson.GSON))
            .encoder(new GsonEncoder())
            .requestInterceptor(new BasicAuthRequestInterceptor("username@nisc.coop", "password"))
            .target(TestClient.class, "http://localhost:8080");

    @When("a user's contacts list is requested")
    public void contactListRequest(){
        result = client.getContacts("victor.delgado@nisc.coop", "gotham");
    }

    @When("^a user's contacts list is requested and they have no contacts$")
    public void noContactsGetRequest(){
        result = client.getContacts("jacob.yanez@nisc.coop", "gotham");
    }

    @When("^get contacts for user on nonexistent domain$")
    public void nonexistentDomain(){
        try{
            result = client.getContacts("victor.delgado@nisc.coop", "garbage");
        }catch(FeignException e){
            error = true;
        }
    }

    @When("an update request is made")
    public void updateRequest(){
        result = client.update("victor.delgado@nisc.coop", "gotham");
    }

    @When("an update request is made for non-existing contacts")
    public void noContactsUpdateRequest(){
        try{
            result = client.update("jacob.yanez@nisc.coop", "gotham");
        }catch(FeignException e){
            error = true;
        }
    }
//---------------------------

    @Then("^display contacts list$")
    public void displayList(){
        assertEquals(victorList, result);
    }

    @Then("^no contacts are shown$")
    public void showNone(){
        assertEquals(new ArrayList<RegisteredContact>(), result);
    }

    @Then("^display an error$")
    public void displayError(){
         assertEquals(true, error);
    }

    @Then("^update all phone #s and email addresses$")
    public void checkUpdate(){
        assertEquals(victorList, result);

    }



}