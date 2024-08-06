package br.ufscar.dc.dsw.bikerental.infrastructure.authentication.controllers;
import br.ufscar.dc.dsw.bikerental.entity.authentication.model.User;
import br.ufscar.dc.dsw.bikerental.infrastructure.config.responses.LoginResponse;
import br.ufscar.dc.dsw.bikerental.usecase.authentication.AuthenticationUseCase;
import br.ufscar.dc.dsw.bikerental.usecase.authentication.JwtService;
import br.ufscar.dc.dsw.bikerental.usecase.authentication.dto.LoginUserDto;
import br.ufscar.dc.dsw.bikerental.usecase.authentication.dto.RegisterUserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationUseCase authenticationUseCase;

    public AuthenticationController(JwtService jwtService, AuthenticationUseCase authenticationUseCase) {
        this.jwtService = jwtService;
        this.authenticationUseCase = authenticationUseCase;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationUseCase.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationUseCase.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}