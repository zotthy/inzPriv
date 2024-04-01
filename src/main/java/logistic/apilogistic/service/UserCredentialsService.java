package logistic.apilogistic.service;

import logistic.apilogistic.Dtos.UserCredentialsDto;
import logistic.apilogistic.dtoMapper.UserCredentialsDtoMapper;
import logistic.apilogistic.dtoMapper.UserRegisterDtoMapper;
import logistic.apilogistic.authRequest.RegisterRequest;
import logistic.apilogistic.config.JwtService;
import logistic.apilogistic.entity.Address;
import logistic.apilogistic.entity.User;
import logistic.apilogistic.entity.UserRole;
import logistic.apilogistic.exceptions.ExistsException;
import logistic.apilogistic.repository.AddressRepository;
import logistic.apilogistic.repository.UserRepository;
import logistic.apilogistic.repository.UserRoleRepozitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserCredentialsService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepozitory userRoleRepozitory;
    private final UserRegisterDtoMapper userRegisterDtoMapper;
    private final JwtService jwtService;
    private final AddressRepository addressRepository;


    @Autowired
    public UserCredentialsService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserRoleRepozitory userRoleRepozitory,
                                  UserRegisterDtoMapper userRegisterDtoMapper, JwtService jwtService, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepozitory = userRoleRepozitory;
        this.userRegisterDtoMapper = userRegisterDtoMapper;
        this.jwtService = jwtService;
        this.addressRepository = addressRepository;
    }

    public Optional<UserCredentialsDto> findCredentialsByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserCredentialsDtoMapper::map);
    }

    @Transactional
    public void register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new ExistsException("User is exists");
        }

        User user = UserRegisterDtoMapper.mapDtoToEntity(registerRequest);
        String password = passwordEncoder.encode(registerRequest.getPassword());
        user.setPassword(password);
        UserRole userRole = userRoleRepozitory.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Not found"));
        user.getRoles().add(userRole);
        userRepository.save(user);
    }

    @Transactional
    public void createAndAssignAddressToUser(String token, Address newAddress) {
        String userEmail = jwtService.getEmailFromToken(token);
        User user = userRepository.getUserByEmail(userEmail);

        if (user.getAddress() != null) {
            throw new IllegalArgumentException("This user already has an address registered.");
        }

        Address savedAddress = createAddress(newAddress);
        user.setAddress(savedAddress);
        userRepository.save(user);
    }

    private Address createAddress(Address address) {
        return addressRepository.save(address);
    }

}
