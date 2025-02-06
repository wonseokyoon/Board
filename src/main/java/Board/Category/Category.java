package Board.Category;

import Board.Post.Post;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Category {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "category")
    private Set<Post> postSet = new HashSet<>();

}
