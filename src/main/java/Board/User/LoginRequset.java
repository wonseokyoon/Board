package Board.User;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginRequset {
    @NotEmpty(message = "아이디 입력하시요")
    private String username;

    @NotEmpty(message = "비밀번호를 입력하세요")
    private String password;

}
