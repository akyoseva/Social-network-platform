package com.bulpros.javaknights.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto {
    @Length(min = 2, max = 100, message = "Text is required")
    private String text;
}
