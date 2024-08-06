package br.ufscar.dc.dsw.bikerental.infrastructure.authentication.controllers;



import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufscar.dc.dsw.bikerental.entity.authentication.model.User;
import br.ufscar.dc.dsw.bikerental.usecase.authentication.AuthenticationUseCase;
import br.ufscar.dc.dsw.bikerental.usecase.authentication.dto.RegisterUserDto;

@RequestMapping("/admins")
@RestController
public class AdminController {
    private final AuthenticationUseCase authenticationUseCase;

    public AdminController(AuthenticationUseCase authenticationUseCase) {
        this.authenticationUseCase = authenticationUseCase;
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<User> createAdministrator(@RequestBody RegisterUserDto registerUserDto) {
        User createdAdmin = authenticationUseCase.createAdministrator(registerUserDto);

        return ResponseEntity.ok(createdAdmin);
    }
}