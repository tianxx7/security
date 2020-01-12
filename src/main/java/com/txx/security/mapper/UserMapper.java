package com.txx.security.mapper;

import com.txx.security.entity.User;
import org.springframework.stereotype.Repository;

/**
 * @author labvi
 * @version 1.0.0
 */
@Repository
public interface UserMapper {

    User getUser(String phone);
}
