package com.cash2loan.services;

import com.cash2loan.domain.AppUser;
import com.cash2loan.domain.Role;

import java.util.List;

public interface AppUserService {
    AppUser saveUser(AppUser appUser);
    Role saveRole(Role role);
    void addRoleToUser(String email, String roleName);
    AppUser getUser(String email);
    List<AppUser> getUsers();   // not very useful
}
