package coop.nisc.jacobdemo.util;

import coop.nisc.courier.pojos.v3.RegisteredContact;
import coop.nisc.courier.v3.MessengerPaths;
import coop.nisc.courier.v3.subcription.MessengerSubscriberId;
import coop.nisc.jacobdemo.web.VariableTarget;
import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.GET;
import javax.ws.rs.core.MediaType;
import java.util.List;

public interface TestClient {

    String PATH = "test/contacts/{smarthubID}/{domain}";

    @GetMapping(path = PATH)
    public List<RegisteredContact> getContacts(@PathVariable("smarthubID") String smarthubID, @PathVariable("domain") String domain);

    @PostMapping(path = PATH)
    public List<RegisteredContact> update(@PathVariable("smarthubID") String smarthubID, @PathVariable("domain") String domain);


}
