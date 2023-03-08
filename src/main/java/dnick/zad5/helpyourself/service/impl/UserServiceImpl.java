package dnick.zad5.helpyourself.service.impl;

import dnick.zad5.helpyourself.model.User;
import dnick.zad5.helpyourself.model.enumerations.Role;
import dnick.zad5.helpyourself.model.exceptions.InvalidUserIdException;
import dnick.zad5.helpyourself.model.exceptions.InvalidUsernameException;
import dnick.zad5.helpyourself.repository.UserRepository;
import dnick.zad5.helpyourself.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(InvalidUserIdException::new);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(InvalidUsernameException::new);
    }

    @Override
    public List<User> listAll() {
        return userRepository.findAll();
    }

    @Override
    public User create(String username, String password, String role) {
        return userRepository.save(new User(username, passwordEncoder.encode(password), Role.valueOf(role)));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws InvalidUsernameException{
        User user= userRepository.findByUsername(username).orElseThrow(InvalidUsernameException::new);
        UserDetails userDetails= new org.springframework.security.core.userdetails.User(
                user.getUsername(),user.getPassword(), Stream.of(new SimpleGrantedAuthority(user.getRole().toString())).collect(Collectors.toList())
        );
        return userDetails;
    }
}
