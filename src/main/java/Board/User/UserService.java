package Board.User;

import Board.Exception.BaseException;
import Board.Exception.ErrorCode;
import Board.Jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    JwtUtil jwtUtil;


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

    public LoginResponse validateUser(LoginRequset loginRequset) {
        // 사용자 찾기
        SiteUser user = userRepository.findByUsername(loginRequset.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + loginRequset.getUsername()));

        // 비밀번호 확인
        if (passwordEncoder.matches(loginRequset.getPassword(), user.getPassword())) {
            // JWT 생성
            String accessToken = jwtUtil.generateAccessToken(user.getUsername());
            String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());
            return new LoginResponse(accessToken, refreshToken); // 두 토큰을 포함한 Response 반환
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }
}
