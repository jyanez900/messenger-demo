package coop.nisc.jacobdemo.web;

import feign.Feign;
import feign.Target;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RequestMapping(path = "test/contacts/{smarthubID}/{domain}")
public class VariableTarget<T> extends Target.HardCodedTarget<T> {
    private String url;
    private ThreadLocal<String> domain;

    public VariableTarget(Class<T> clas){
        super(clas, "Messenger");
        this.domain = new ThreadLocal<>();
    }

    @Override
    public String url(){
       return "https://"+ domain.get() +".arcus.coop/courier/services";
    }

    public void setTarget(String domain){
        this.domain.set(domain);
    }

    public void clear(){
        domain.remove();
    }
}
