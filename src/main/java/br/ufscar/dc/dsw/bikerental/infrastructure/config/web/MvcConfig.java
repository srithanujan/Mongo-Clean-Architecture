package br.ufscar.dc.dsw.bikerental.infrastructure.config.web;
import br.ufscar.dc.dsw.bikerental.entity.customer.gateway.CustomerGateway;
import br.ufscar.dc.dsw.bikerental.entity.rental.gateway.RentalGateway;
import br.ufscar.dc.dsw.bikerental.entity.rentalCompany.gateway.RentalCompanyGateway;
import br.ufscar.dc.dsw.bikerental.infrastructure.customer.gateway.CustomerDatabaseGateway;
import br.ufscar.dc.dsw.bikerental.infrastructure.config.db.repository.CustomerRepository;
import br.ufscar.dc.dsw.bikerental.infrastructure.config.db.repository.RentalRepository;
import br.ufscar.dc.dsw.bikerental.infrastructure.config.db.repository.UserAuthenticationRepository;
import br.ufscar.dc.dsw.bikerental.infrastructure.config.db.repository.RentalCompanyRepository;
import br.ufscar.dc.dsw.bikerental.infrastructure.rental.gateway.RentalDatabaseGateway;
import br.ufscar.dc.dsw.bikerental.infrastructure.rentalCompany.gateway.RentalCompanyDatabaseGateway;
import br.ufscar.dc.dsw.bikerental.usecase.customer.*;
import br.ufscar.dc.dsw.bikerental.usecase.rental.*;
import br.ufscar.dc.dsw.bikerental.usecase.rentalCompany.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;

import java.util.ResourceBundle;

@Configuration
public class MvcConfig {
  private final UserAuthenticationRepository userAuthenticationRepository;


  public MvcConfig(UserAuthenticationRepository userAuthenticationRepository) {
    this.userAuthenticationRepository = userAuthenticationRepository;
}

  @Bean
  UserDetailsService userDetailsService() {
      return username -> userAuthenticationRepository.findByEmail(username)
              .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }

  @Bean
  BCryptPasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
      return config.getAuthenticationManager();
  }

  @Bean
  AuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

      authProvider.setUserDetailsService(userDetailsService());
      authProvider.setPasswordEncoder(passwordEncoder());

      return authProvider;
  }


  @Bean
  public ResourceBundle messageBundle() {
    return ResourceBundle.getBundle("ValidationMessages");
  }

  @Bean
  public LocaleResolver sessionLocaleResolver() {
    return new AcceptHeaderResolver();
  }

  @Bean
  public CreateCustomerUseCase createCustomerUseCase(CustomerRepository customerRepository) {
    CustomerGateway customerGateway = new CustomerDatabaseGateway(customerRepository);
    return new CreateCustomerUseCase(customerGateway);
  }

  @Bean
  public GetCustomerUseCase getCustomerUseCase(CustomerRepository customerRepository) {
    CustomerGateway customerGateway = new CustomerDatabaseGateway(customerRepository);
    return new GetCustomerUseCase(customerGateway);
  }

  @Bean
  public SearchCustomerUseCase searchCustomerUseCase(CustomerRepository customerRepository) {
    CustomerGateway customerGateway = new CustomerDatabaseGateway(customerRepository);
    return new SearchCustomerUseCase(customerGateway);
  }

  @Bean
  public UpdateCustomerUseCase updateCustomerUseCase(CustomerRepository customerRepository) {
    CustomerGateway customerGateway = new CustomerDatabaseGateway(customerRepository);
    return new UpdateCustomerUseCase(customerGateway);
  }

  @Bean
  public DeleteCustomerUseCase deleteCustomerUseCase(CustomerRepository customerRepository) {
    CustomerGateway customerGateway = new CustomerDatabaseGateway(customerRepository);
    return new DeleteCustomerUseCase(customerGateway);
  }

  @Bean
  public CreateRentalCompanyUseCase createRentalCompanyUseCase(RentalCompanyRepository rentalCompanyRepository) {
    RentalCompanyGateway rentalCompanyGateway = new RentalCompanyDatabaseGateway(rentalCompanyRepository);
    return new CreateRentalCompanyUseCase(rentalCompanyGateway);
  }

  @Bean
  public GetRentalCompanyUseCase getRentalCompanyUseCase(RentalCompanyRepository rentalCompanyRepository) {
    RentalCompanyGateway rentalCompanyGateway = new RentalCompanyDatabaseGateway(rentalCompanyRepository);
    return new GetRentalCompanyUseCase(rentalCompanyGateway);
  }

  @Bean
  public SearchRentalCompanyUseCase searchRentalCompanyUseCase(RentalCompanyRepository rentalCompanyRepository) {
    RentalCompanyGateway rentalCompanyGateway = new RentalCompanyDatabaseGateway(rentalCompanyRepository);
    return new SearchRentalCompanyUseCase(rentalCompanyGateway);
  }

  @Bean
  public UpdateRentalCompanyUseCase updateRentalCompanyUseCase(RentalCompanyRepository rentalCompanyRepository) {
    RentalCompanyGateway rentalCompanyGateway = new RentalCompanyDatabaseGateway(rentalCompanyRepository);
    return new UpdateRentalCompanyUseCase(rentalCompanyGateway);
  }

  @Bean
  public DeleteRentalCompanyUseCase deleteRentalCompanyUseCase(RentalCompanyRepository rentalCompanyRepository) {
    RentalCompanyGateway rentalCompanyGateway = new RentalCompanyDatabaseGateway(rentalCompanyRepository);
    return new DeleteRentalCompanyUseCase(rentalCompanyGateway);
  }

  @Bean
  public FindRentalCompaniesByCityUseCase findRentalCompaniesByCityUseCase(RentalCompanyRepository rentalCompanyRepository) {
    RentalCompanyGateway rentalCompanyGateway = new RentalCompanyDatabaseGateway(rentalCompanyRepository);
    return new FindRentalCompaniesByCityUseCase(rentalCompanyGateway);
  }

  @Bean
  public DeleteRentalUseCase deleteRentalUseCase(RentalRepository rentalRepository) {
    RentalGateway rentalGateway = new RentalDatabaseGateway(rentalRepository);
    return new DeleteRentalUseCase(rentalGateway);
  }

  @Bean
  public CreateRentalUseCase createRentalUseCase(RentalRepository rentalRepository, RentalCompanyRepository rentalCompanyRepository, CustomerRepository customerRepository) {
    RentalGateway rentalGateway = new RentalDatabaseGateway(rentalRepository);
    RentalCompanyGateway rentalCompanyGateway = new RentalCompanyDatabaseGateway(rentalCompanyRepository);
    CustomerGateway customerGateway = new CustomerDatabaseGateway(customerRepository);

    return new CreateRentalUseCase(rentalGateway, rentalCompanyGateway, customerGateway);
  }

  @Bean
  public GetRentalUseCase getRentalUseCase(RentalRepository rentalRepository) {
    RentalGateway rentalGateway = new RentalDatabaseGateway(rentalRepository);
    return new GetRentalUseCase(rentalGateway);
  }

  @Bean
  public ListRentalsByRentalCompanyUseCase listRentalsByRentalCompanyUseCase(RentalRepository rentalRepository, RentalCompanyRepository rentalCompanyRepository) {
    RentalGateway rentalGateway = new RentalDatabaseGateway(rentalRepository);
    RentalCompanyGateway rentalCompanyGateway = new RentalCompanyDatabaseGateway(rentalCompanyRepository);

    return new ListRentalsByRentalCompanyUseCase(rentalGateway, rentalCompanyGateway);
  }

  @Bean
  public ListRentalsByCustomerUseCase listRentalsByCustomerUseCase(RentalRepository rentalRepository, CustomerRepository customerRepository) {
    RentalGateway rentalGateway = new RentalDatabaseGateway(rentalRepository);
    CustomerGateway customerGateway = new CustomerDatabaseGateway(customerRepository);

    return new ListRentalsByCustomerUseCase(rentalGateway, customerGateway);
  }

  @Bean
  public SearchRentalsUseCase searchRentalsUseCase(RentalRepository rentalRepository) {
    RentalGateway rentalGateway = new RentalDatabaseGateway(rentalRepository);
    return new SearchRentalsUseCase(rentalGateway);
  }

}
