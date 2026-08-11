package com.hyperion.dto;

import jakarta.validation.constraints.*;

public class RegistrationForm {

    @NotBlank(message = "{validation.lastName.required}")
    @Size(max = 50)
    private String lastName;

    @NotBlank(message = "{validation.firstName.required}")
    @Size(max = 50)
    private String firstName;

    @NotBlank(message = "{validation.address.required}")
    private String deliveryAddress;

    @NotBlank(message = "{validation.email.required}")
    @Email(message = "{validation.email.invalid}")
    private String email;

    @NotBlank(message = "{validation.phone.required}")
    @Size(min = 8, max = 20, message = "{validation.phone.size}")
    private String phone;

    @NotBlank(message = "{validation.username.required}")
    @Size(max = 50)
    private String username;

    @NotBlank(message = "{validation.password.required}")
    private String password;

    @NotBlank(message = "{validation.passwordConfirmation.required}")
    private String passwordConfirmation;

    private String secondaryPhone;

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getPasswordConfirmation() { return passwordConfirmation; }
    public void setPasswordConfirmation(String passwordConfirmation) { this.passwordConfirmation = passwordConfirmation; }
    public String getSecondaryPhone() { return secondaryPhone; }
    public void setSecondaryPhone(String secondaryPhone) { this.secondaryPhone = secondaryPhone; }
}