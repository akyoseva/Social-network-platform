package com.bulpros.javaknights.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "forums")
@Getter
@Setter
@NoArgsConstructor
public class Forum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Length(min = 5, max = 200, message = "Title is required")
    @Column(name = "title")
    private String title;

    @Length(min = 5, max = 200, message = "Description is required")
    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "community_id")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Community community;

    @OneToMany(mappedBy = "forum")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Post> posts;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Forum)) return false;
        Forum forum = (Forum) o;
        return getId() == forum.getId() &&
                getTitle().equals(forum.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle());
    }
}
