package cohort_65.java.forumservice.post.dto;

import lombok.*;

import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {
    String id;
    String title;
    String content;
    @Singular
    Set<String> tags;
}
