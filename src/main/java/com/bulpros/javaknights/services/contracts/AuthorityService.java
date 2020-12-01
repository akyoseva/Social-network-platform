package com.bulpros.javaknights.services.contracts;

import com.bulpros.javaknights.models.Authority;

import java.util.List;
import java.util.Set;

public interface AuthorityService {
    boolean isAdmin(String username);
    Authority getAuthority(String authority);
    Authority findById(long id);
}
