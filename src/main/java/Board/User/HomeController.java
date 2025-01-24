package Board.User;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homePage(Authentication authentication, Model model) {
        if (authentication != null) {
            String username = authentication.getName(); // 인증된 사용자 이름 가져오기
            model.addAttribute("username", username);
        }
        return "home"; // "home.html"로 리턴
    }
}