package ca.jrvs.apps.trading.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class AppController {
  @GetMapping("/")
  public String greeting(){
    return "Welcome to the Trading App";
  }
}
