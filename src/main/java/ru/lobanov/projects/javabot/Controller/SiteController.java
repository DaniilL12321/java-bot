package ru.lobanov.projects.javabot.Controller;

import org.springframework.web.bind.annotation.GetMapping;

public class SiteController {
    @GetMapping("/")
    public String getAllVisitors() {
        return "index";
    }
}
