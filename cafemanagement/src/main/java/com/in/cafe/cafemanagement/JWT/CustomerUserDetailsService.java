package com.in.cafe.cafemanagement.JWT;

import com.in.cafe.cafemanagement.Model.User;
import com.in.cafe.cafemanagement.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Objects;
@Service
@Slf4j
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    UserDao userDao;


    private com.in.cafe.cafemanagement.Model.User userDetails;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("Inside  loadUserByUsername {}", username);
       userDetails = userDao.findByEmailId(username);

        if (!Objects.isNull(userDetails)) {
            return new org.springframework.security.core.userdetails.User(userDetails.getEmail(), userDetails.getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found");
        }


    }

    public   com.in.cafe.cafemanagement.Model.User getUserDetails() {


        return userDetails;
    }
}
