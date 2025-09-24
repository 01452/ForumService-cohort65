package cohort_65.java.forumservice.post.service;

import cohort_65.java.forumservice.post.dao.PostRepository;
import cohort_65.java.forumservice.post.dto.CommentDto;
import cohort_65.java.forumservice.post.dto.NewPostDto;
import cohort_65.java.forumservice.post.dto.PostDto;
import cohort_65.java.forumservice.post.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    final PostRepository postRepository;

    @Override
    public PostDto addNewPost(NewPostDto newPostDto, String author) {
        Post post = new Post(newPostDto.getTitle(),
                newPostDto.getContent(), author, newPostDto.getTags());
        post = postRepository.save(post);
        List<CommentDto> lists = post.getComments().stream().map(
                c -> new CommentDto(c.getUser(),c.getMessage(),
                        c.getLikes(),c.getDateCreated())
        ).toList();
        return new PostDto(post.getId(),post.getTitle(),
                post.getContent(),post.getAuthor(),
                post.getTags(),post.getLikes(),post.getDateCreated(),
                lists);
    }
}
