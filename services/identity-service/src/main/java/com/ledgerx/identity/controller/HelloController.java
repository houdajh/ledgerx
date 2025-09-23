package com.ledgerx.identity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
  @GetMapping("/public/hello")
  public String publicHello() { return "hello (public)"; }

  @PreAuthorize("hasAuthority('ROLE_read')")
  @GetMapping("/secure/hello")
  public String secureHello() { return "hello (secure)"; }
}
