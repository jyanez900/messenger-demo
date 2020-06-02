package coop.nisc.jacobdemo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Page controller for the main application dashboard.
 */
@Controller
public class DashboardController {

    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }

}
