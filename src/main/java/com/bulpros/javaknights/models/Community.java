package com.bulpros.javaknights.models;

import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "communities")
@Getter
@Setter
@RequiredArgsConstructor
public class Community {
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

    @ManyToMany(mappedBy = "communities")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<User> members;

    @OneToMany(mappedBy = "community",fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Forum> forums;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Community)) return false;
        Community community = (Community) o;
        return getId() == community.getId() &&
                getTitle().equals(community.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle());
    }
}
