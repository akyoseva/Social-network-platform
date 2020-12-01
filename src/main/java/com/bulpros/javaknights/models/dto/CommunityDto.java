package com.bulpros.javaknights.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class CommunityDto {
    @Length(min = 5, max = 200, message = "Title is required")
    private String title;

    @Length(min = 5, max = 200, message = "Description is required")
    private String description;
}
