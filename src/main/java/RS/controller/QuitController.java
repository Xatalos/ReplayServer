package RS.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class QuitController {

    // This file is here to easier closing of WebApp in OS X where closing the app is sometimes impossible

    // If you are running this app outside of dev environment you should remove this file completely
    // You have been warned

    @RequestMapping("/quit")
    public void helloWorld() {
        System.exit(1);
    }
}
