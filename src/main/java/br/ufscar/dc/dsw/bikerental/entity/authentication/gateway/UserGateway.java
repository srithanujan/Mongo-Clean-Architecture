package br.ufscar.dc.dsw.bikerental.entity.authentication.gateway;

import br.ufscar.dc.dsw.bikerental.entity.authentication.model.User;

import java.util.Optional;

public interface UserGateway {
    User save(User user);
    Optional<User> findByEmail(String email);
    Iterable<User> findAll();
}