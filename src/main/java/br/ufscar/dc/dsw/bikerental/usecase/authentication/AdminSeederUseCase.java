package br.ufscar.dc.dsw.bikerental.usecase.authentication;

import java.util.Optional;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.ufscar.dc.dsw.bikerental.entity.authentication.model.Role;
import br.ufscar.dc.dsw.bikerental.entity.authentication.model.RoleEnum;
import br.ufscar.dc.dsw.bikerental.entity.authentication.model.User;
import br.ufscar.dc.dsw.bikerental.infrastructure.config.db.repository.RoleRepository;
import br.ufscar.dc.dsw.bikerental.infrastructure.config.db.repository.UserAuthenticationRepository;
import br.ufscar.dc.dsw.bikerental.usecase.authentication.dto.RegisterUserDto;

@Component
public class AdminSeederUseCase implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepository roleRepository;
    private final UserAuthenticationRepository userAuthenticationRepository;

    private final PasswordEncoder passwordEncoder;


    public AdminSeederUseCase(
        RoleRepository roleRepository,
        UserAuthenticationRepository  userAuthenticationRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.roleRepository = roleRepository;
        this.userAuthenticationRepository = userAuthenticationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.createSuperAdministrator();
    }

    private void createSuperAdministrator() {
        RegisterUserDto userDto = new RegisterUserDto();
        userDto.setFullName("Super Admin").setEmail("super.admin@email.com").setPassword("123456");

        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.SUPER_ADMIN);
        Optional<User> optionalUser = userAuthenticationRepository.findByEmail(userDto.getEmail());

        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            return;
        }

        var user = new User()
            .setFullName(userDto.getFullName())
            .setEmail(userDto.getEmail())
            .setPassword(passwordEncoder.encode(userDto.getPassword()))
            .setRole(optionalRole.get());

            userAuthenticationRepository.save(user);
    }
}




