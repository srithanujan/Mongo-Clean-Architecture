package br.ufscar.dc.dsw.bikerental.infrastructure.config.db.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.ufscar.dc.dsw.bikerental.entity.authentication.model.Role;
import br.ufscar.dc.dsw.bikerental.entity.authentication.model.RoleEnum;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Optional<Role> findByName(RoleEnum name);
}
