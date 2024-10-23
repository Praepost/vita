package org.service.security;

import lombok.RequiredArgsConstructor;
import org.service.web.task.exception.EntityNotFoundException;
import org.service.web.user.entity.User;
import org.service.web.user.entity.repository.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepo userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }
        return new CustomUserDetails(user);
    }
}
