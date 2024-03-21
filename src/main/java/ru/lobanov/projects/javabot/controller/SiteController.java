package ru.lobanov.projects.javabot.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class SiteController {
    @GetMapping("/")
    public String getAllVisitors() {
        return "index";
    }
}
