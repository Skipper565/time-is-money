package cz.uhk.ppro.semestralniprojekt.service;

public interface SecurityService {

    String findLoggedInUsername();

    void autologin(String username, String password);

}
