package br.ufscar.dc.dsw.bikerental.usecase.authentication;
import br.ufscar.dc.dsw.bikerental.entity.authentication.model.Role;
import br.ufscar.dc.dsw.bikerental.entity.authentication.model.RoleEnum;
import br.ufscar.dc.dsw.bikerental.entity.authentication.model.User;
import br.ufscar.dc.dsw.bikerental.infrastructure.config.db.repository.RoleRepository;
import br.ufscar.dc.dsw.bikerental.infrastructure.config.db.repository.UserAuthenticationRepository;
import br.ufscar.dc.dsw.bikerental.usecase.authentication.dto.LoginUserDto;
import br.ufscar.dc.dsw.bikerental.usecase.authentication.dto.RegisterUserDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class AuthenticationUseCase {
    private final UserAuthenticationRepository userAuthenticationRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationUseCase(
        UserAuthenticationRepository userAuthenticationRepository,
        RoleRepository roleRepository,
        AuthenticationManager authenticationManager,
        PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userAuthenticationRepository = userAuthenticationRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(RegisterUserDto input) {
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);

        if (optionalRole.isEmpty()) {
            return null;
        }

        var user = new User()
            .setFullName(input.getFullName())
            .setEmail(input.getEmail())
            .setPassword(passwordEncoder.encode(input.getPassword()))
            .setRole(optionalRole.get());

        return userAuthenticationRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                input.getEmail(),
                input.getPassword()
            )
        );

        return userAuthenticationRepository.findByEmail(input.getEmail()).orElseThrow();
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userAuthenticationRepository.findAll().forEach(users::add);

        return users;
    }

    public User createAdministrator(RegisterUserDto input) {
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.ADMIN);

        if (optionalRole.isEmpty()) {
            return null;
        }

        var user = new User()
                .setFullName(input.getFullName())
                .setEmail(input.getEmail())
                .setPassword(passwordEncoder.encode(input.getPassword()))
                .setRole(optionalRole.get());

        return userAuthenticationRepository.save(user);
    }
}