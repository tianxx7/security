package com.txx.security.service;

import com.txx.security.entity.User;
import com.txx.security.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author labvi
 * @version 1.0.0
 */
@Service
public class CustomerUserDetailsService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.getUser(username);
        if (null == user){
            throw new UsernameNotFoundException("用户名不存在");
        }
        return user;
    }
}
