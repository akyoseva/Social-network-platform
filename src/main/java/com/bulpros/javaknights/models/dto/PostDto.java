package com.bulpros.javaknights.models.dto;

import com.bulpros.javaknights.models.Comment;
import com.bulpros.javaknights.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
    @Length(min = 5, max = 200, message = "Title is required")
    private String title;

    @Length(min = 5, max = 200, message = "Text is required")
    private String text;

}
