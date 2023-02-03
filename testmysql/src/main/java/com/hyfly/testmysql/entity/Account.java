package com.hyfly.testmysql.entity;

import io.vertx.core.json.JsonObject;

public class Account {

    private String id;
    private String username;
    private String phone;
    private String email;
    private Long birthDate;

    public Account() {
    }

    public Account(JsonObject json) {
        Account account = json.mapTo(Account.class);

        this.id = account.getId();
        this.username = account.getUsername();
        this.phone = account.getPhone();
        this.email = account.getEmail();
        this.birthDate = account.getBirthDate();
    }

    public JsonObject toJson() {
        return JsonObject.mapFrom(this);
    }

    public String getId() {
        return id;
    }

    public Account setId(String id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public Account setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Account setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Account setEmail(String email) {
        this.email = email;
        return this;
    }

    public Long getBirthDate() {
        return birthDate;
    }

    public Account setBirthDate(Long birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    @Override
    public String toString() {
        return toJson().encodePrettily();
    }
}
