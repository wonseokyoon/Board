package Board.Category;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Transactional
@RequestMapping("category")
public class CategoryController {
    // 추가,삭제,읽기, 즐겨찾기 한 사람 목록, 인기순 정렬, 게시글 몇개있는지, 관리자만 제어 가능, 중복 불가

    private final CategoryService categoryService;

    // 추가
    @PostMapping
    ResponseEntity<?> createCategory(@RequestBody Category category) {
        categoryService.create(category);
        return ResponseEntity.ok(category);
    }

}
