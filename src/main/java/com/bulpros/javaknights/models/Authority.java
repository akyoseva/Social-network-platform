package com.bulpros.javaknights.models;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "authorities")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Authority implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NonNull
    @JoinColumn(name = "authority")
    private String authority;

    @ManyToMany(mappedBy = "authorities", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<User> users;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Authority)) return false;
        Authority authority1 = (Authority) o;
        return getId() == authority1.getId() &&
                getAuthority().equals(authority1.getAuthority());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAuthority());
    }
}
