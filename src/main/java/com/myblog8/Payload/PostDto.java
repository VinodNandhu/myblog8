package com.myblog8.Payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto { //dto go to service and service will convert dto to entity and saves it . snd it will return postDto
    private Long id;


    @NotEmpty
    @Size(min = 2, message = "post title should be at least 2 characters " )
    private String title;

    @NotEmpty
    @Size(min = 4, message = "post description should be at least 4 characters " )
    private String description;

    @NotEmpty
    @Size(min = 5, message = "post content should be at least 5 characters " )
    private String content;
}
