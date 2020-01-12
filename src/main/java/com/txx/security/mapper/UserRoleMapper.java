package com.txx.security.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author labvi
 * @version 1.0.0
 */
@Repository
public interface UserRoleMapper {
    List<String> roles();
}
