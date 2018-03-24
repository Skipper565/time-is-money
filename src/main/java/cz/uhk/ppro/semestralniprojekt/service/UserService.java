package cz.uhk.ppro.semestralniprojekt.service;

import cz.uhk.ppro.semestralniprojekt.model.user.User;

public interface UserService {

    void save(User user);

    User findByUsername(String username);

}
