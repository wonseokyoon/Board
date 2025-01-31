package Board.Post;

import Board.User.SiteUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostLikeService {

    @Autowired
    PostLikeRepository postLikeRepository;
    @Autowired
    PostDislikeRepository postDislikeRepository;

    public Post addLike(Post post,SiteUser user){
        // 이미 따봉 누름
        if(postLikeRepository.findByPostAndUser(post,user).isPresent()){
            return subLike(post,user);
        }
        // 싫어요 누른상태
        if(postDislikeRepository.findByPostAndUser(post, user).isPresent()){
            subDisLike(post,user);
        }
        PostLikes likes=new PostLikes(post,user);
        likes.setPost(post);
        likes.setUser(user);
        postLikeRepository.save(likes);
        return post;
    }
    public Post subLike(Post post,SiteUser user){
        Optional<PostLikes> likes=postLikeRepository.findByPostAndUser(post,user);
        if(likes.isPresent()){
            postLikeRepository.delete(likes.get());
        }
        return post;
    }

    public Post addDislike(Post post,SiteUser user){
        // 이미 싫어요
        if(postDislikeRepository.findByPostAndUser(post, user).isPresent()){
            return subDisLike(post,user);
        }
        // 따봉 누른상태
        if(postLikeRepository.findByPostAndUser(post,user).isPresent()) {
            subLike(post,user);
        }
        PostDisLikes dislikes= new PostDisLikes(post,user);
        dislikes.setPost(post);
        dislikes.setUser(user);
        postDislikeRepository.save(dislikes);
        return post;
    }
    public Post subDisLike(Post post,SiteUser user){
        Optional<PostDisLikes> dislikes=postDislikeRepository.findByPostAndUser(post,user);
        if(dislikes.isPresent()){
            postDislikeRepository.delete(dislikes.get());
        }
        return post;
    }

}
