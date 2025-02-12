package ca.jrvs.apps.trading.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(produces = MediaType.APPLICATION_JSON_VALUE)
@Controller
@ResponseBody
public class AppController {

  @ApiOperation(value = "Display greeting")
  @GetMapping("/")
  public String greeting(){
    return "Welcome to the Trading App";
  }

}
