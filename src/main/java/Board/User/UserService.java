package Board.User;

import Board.Exception.BaseException;
import Board.Exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String email, String password) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(user);
        return user;
    }

    public boolean existsByEmail(String email) {
        if(userRepository.findByEmail(email).isPresent()){
            return true;
        }else{
            return false;
        }
    }

    public SiteUser getUser(String username) throws BaseException {
        Optional<SiteUser> user=userRepository.findByUsername(username);
        if(user.isPresent()){
            return user.get();
        }else {
            throw new BaseException(ErrorCode.USER_NOT_FOUND);
        }
    }
}
