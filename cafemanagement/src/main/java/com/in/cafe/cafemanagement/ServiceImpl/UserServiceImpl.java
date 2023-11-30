package com.in.cafe.cafemanagement.ServiceImpl;

import com.google.common.base.Strings;

import com.in.cafe.cafemanagement.JWT.CustomerUserDetailsService;
import com.in.cafe.cafemanagement.JWT.JwtFilter;
import com.in.cafe.cafemanagement.JWT.jwtUtils;
import com.in.cafe.cafemanagement.Model.User;
import com.in.cafe.cafemanagement.constants.CafeConstants;
import com.in.cafe.cafemanagement.dao.UserDao;
import com.in.cafe.cafemanagement.service.UserService;
import com.in.cafe.cafemanagement.utils.CafeUtils;
import com.in.cafe.cafemanagement.utils.EmailUtis;
import com.in.cafe.cafemanagement.wrapper.UserWrapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomerUserDetailsService customerUserDetailsService;
    @Autowired
    jwtUtils jwtUtil;


    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    EmailUtis emailUtis;


    @Override
    public ResponseEntity<String> signup(Map<String, String> requestMap) {
        log.info("Inside signup {}", requestMap);
        try {
            if (validatesignUpMap(requestMap)) {

                User user = userDao.findByEmailId(requestMap.get("email"));

                if (Objects.isNull(user)) {
                    userDao.save(getUserFromMap(requestMap));
                    return CafeUtils.getResponceEntity("Succesfully signed up", HttpStatus.OK);
                } else {
                    return CafeUtils.getResponceEntity("email already exists", HttpStatus.BAD_REQUEST);
                }
            } else {
                return CafeUtils.getResponceEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponceEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }



    private boolean validatesignUpMap(Map<String, String> requestMap) {
        if (requestMap.containsKey("name") && requestMap.containsKey("contactNumber") &&
                requestMap.containsKey("email") && requestMap.containsKey("password")) {

            return true;

        } else

            return false;
    }


    private User getUserFromMap(Map<String, String> requestmap) {
        User user = new User();
        user.setName(requestmap.get("name"));
        user.setContactNumber(requestmap.get("contactNumber"));
        user.setEmail(requestmap.get("email"));
        user.setPassword(requestmap.get("password"));
        user.setStatus("false");
        user.setRole("user");
        return user;
    }


    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {

        log.info("Inside Login");

        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password")));


            if (auth.isAuthenticated()) {
                if (customerUserDetailsService.getUserDetails().getStatus().equalsIgnoreCase("True")) {
                    return new ResponseEntity<String>("{\"token\":\"" + jwtUtil.generateToken(customerUserDetailsService.getUserDetails().getEmail(), customerUserDetailsService.getUserDetails().getRole()) + "\"}", HttpStatus.OK);
                } else {
                    return new ResponseEntity<String>("{\"message\":\"" + "wait for Admin Approval ." + "\"}", HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception ex) {
            log.error("{}", ex);
        }
        return new ResponseEntity<String>("{\"message\":\"" + "Bad Credentials ." + "\"}", HttpStatus.BAD_REQUEST);
    }


    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {

        try {

            if (jwtFilter.isAdmin()) {

                return new ResponseEntity<>(userDao.getAllUser(), HttpStatus.OK);


            } else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestmap) {

        try {
            if (jwtFilter.isAdmin()) {
                Optional<User> optional = userDao.findById(Integer.parseInt(requestmap.get("id")));

                if (!optional.isEmpty()) {
                    userDao.updateStatus(requestmap.get("status"), Integer.parseInt(requestmap.get("id")));
                    sendMailToAllAdmin(requestmap.get("status"), optional.get().getEmail(), userDao.getAllAdmin());

                    return CafeUtils.getResponceEntity("User status updated sucessfully", HttpStatus.OK);
                } else {
                    CafeUtils.getResponceEntity("User id doesn't exist", HttpStatus.OK);
                }

            } else {
                return CafeUtils.getResponceEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception ex) {
            ex.printStackTrace();

        }

        return CafeUtils.getResponceEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }


    private void sendMailToAllAdmin(String status, String user, List<String> allAdmin) {


        allAdmin.remove(jwtFilter.getCurrentUser());

        if (status != null && status.equalsIgnoreCase("true")) {

            emailUtis.sendSimpleMessage(jwtFilter.getCurrentUser(),
                    "Account approved", "USER:- " + user + "\n is Approved by \nADMIN: -" + jwtFilter.getCurrentUser(), allAdmin);

        } else {


            emailUtis.sendSimpleMessage(jwtFilter.getCurrentUser(),
                    "Account Disabled", "USER:- " + user + "\n is Disabled by \nADMIN: -" + jwtFilter.getCurrentUser(), allAdmin);

        }


    }


    @Override
    public ResponseEntity<String> checkToken() {
        return CafeUtils.getResponceEntity("true", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try {

            User userObj = userDao.findByEmail(jwtFilter.getCurrentUser());
            if (!userObj.equals(null)) {
                if (userObj.getPassword().equals(requestMap.get("oldPassword"))) {
                    userObj.setPassword(requestMap.get("newPassword"));
                    userDao.save(userObj);
                    return  CafeUtils.getResponceEntity("Password Upated Sucessfully",HttpStatus.OK);

                }
                return CafeUtils.getResponceEntity("Incorrect Old Password", HttpStatus.BAD_REQUEST);
            }
            return CafeUtils.getResponceEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);


        } catch (Exception ex) {
            ex.printStackTrace();

        }

        return CafeUtils.getResponceEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
        try{
            User user=userDao.findByEmail(requestMap.get("email"));
            if(!Objects.isNull(user)&& !Strings.isNullOrEmpty(user.getEmail())){
                emailUtis.forgotMail(user.getEmail(), "Credential by Cafe Management  System",user.getPassword());

            }
            return CafeUtils.getResponceEntity("Check Your  mail for  Credentials",HttpStatus.OK);

        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return CafeUtils.getResponceEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

}