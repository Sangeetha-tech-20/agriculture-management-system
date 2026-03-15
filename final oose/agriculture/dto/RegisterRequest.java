package com.agriculture.dto;

public record RegisterRequest(
    String firstName,
    String lastName,
    String email,
    String password,
    String phone,
    Double farmSize
) {}