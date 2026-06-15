package com.tenco.web_socket_step.polling;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PollingController {

    @GetMapping("/")
    public String index() {
        System.out.println(">>> Polling 페이지가 새로고침 되었습니다.");

        return "polling/index";
    }
}
