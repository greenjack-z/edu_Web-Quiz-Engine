package engine.services;

import engine.dto.UserDTO;
import engine.entities.User;
import engine.exceptions.UserExistsException;
import engine.exceptions.UserNotFoundException;
import engine.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDBService {

    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UserDBService.class);

    @Autowired
    public UserDBService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByUsername(String username) {
        logger.debug("search user: {}", username);
        return userRepository.findUserByEmail(username).orElseThrow(UserNotFoundException::new);
    }

    public void addUser(UserDTO userDTO) {
        String email = userDTO.email();
        logger.debug("check if email exists: {}", email);
        if (userRepository.existsUserByEmail(email)) {
            throw new UserExistsException();
        }
        logger.debug("adding user with mail: {}", email);
        User user = new User();
        user.setEmail(userDTO.email());
        user.setPassword(userDTO.password());
        user.setRole("ROLE_USER");
        userRepository.save(user);
    }

}
