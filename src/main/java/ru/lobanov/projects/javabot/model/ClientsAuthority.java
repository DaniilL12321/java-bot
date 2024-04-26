package ru.lobanov.projects.javabot.model;

import org.springframework.security.core.GrantedAuthority;

public enum ClientsAuthority implements GrantedAuthority {
    VIEW_JOKES,
    PLACE_JOKES,
    FULL;

    @Override
    public String getAuthority() {
        return this.name();
    }
}