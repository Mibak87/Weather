package service;

import dto.RegistrationDto;
import model.User;

public class RegistrationService {
    public void registration (RegistrationDto registrationDto) {
        User user = new User(registrationDto.getLogin(), registrationDto.getPassword());

    }
}
