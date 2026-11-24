package com.hyperion.dto;

import com.hyperion.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ProfileForm {

    @NotBlank(message = "{validation.username.required}")
    @Size(min = 3, max = 50, message = "{validation.username.size}")
    private String login;

    @NotBlank(message = "{validation.lastName.required}")
    @Size(min = 2, max = 50, message = "{validation.lastName.size}")
    private String lastName;

    @NotBlank(message = "{validation.firstName.required}")
    @Size(min = 2, max = 50, message = "{validation.firstName.size}")
    private String firstName;

    @NotBlank(message = "{validation.address.required}")
    @Size(min = 5, max = 150, message = "{validation.address.size}")
    private String deliveryAddress;

    @NotBlank(message = "{validation.email.required}")
    @Email(message = "{validation.email.invalid}")
    @Size(max = 100, message = "{validation.email.size}")
    private String email;

    @NotBlank(message = "{validation.phone.required}")
    @Pattern(
            regexp = "\\d{8,20}",
            message = "{validation.phone.format}"
    )
    private String phone;

    @Pattern(
            regexp = "^$|\\d{8,20}",
            message = "{validation.secondaryPhone.format}"
    )
    private String secondaryPhone;

    public static ProfileForm from(User user) {
        ProfileForm form = new ProfileForm();
        form.setLogin(user.getLogin());
        form.setLastName(user.getLastName());
        form.setFirstName(user.getFirstName());
        form.setDeliveryAddress(user.getDeliveryAddress());
        form.setEmail(user.getEmail());
        form.setPhone(user.getPhone());
        form.setSecondaryPhone(user.getSecondaryPhone());
        return form;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSecondaryPhone() {
        return secondaryPhone;
    }

    public void setSecondaryPhone(String secondaryPhone) {
        this.secondaryPhone = secondaryPhone;
    }
}
