package de.lubowiecki.securedemo.validator;

import de.lubowiecki.securedemo.annotation.PasswordMatch;

@PasswordMatch
public interface WithConfirmedPassword {

    String getPassword();

    String getPasswordConfirmation();
}
