package com.in.cafe.cafemanagement.restImpl;

import com.in.cafe.cafemanagement.constants.CafeConstants;
import com.in.cafe.cafemanagement.rest.UserRest;
import com.in.cafe.cafemanagement.service.UserService;
import com.in.cafe.cafemanagement.utils.CafeUtils;
import com.in.cafe.cafemanagement.wrapper.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class UserRestImpl implements UserRest {

    @Autowired
    UserService userService;
    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        try {

            return userService.signup(requestMap);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return CafeUtils.getResponceEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        try{
            return  userService.login(requestMap);
        }
        catch ( Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponceEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {

        try
        {
            return userService.getAllUser();
        }
        catch (Exception ex){
            ex.printStackTrace();

        }
        return  new ResponseEntity<List<UserWrapper>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestmap) {

        try
        {
            return userService.update(requestmap);
        }
        catch (Exception ex){
            ex.printStackTrace();

        }
        return CafeUtils.getResponceEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> checkToken() {
        try {
            return userService.checkToken();
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return CafeUtils.getResponceEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);



    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try {
            return userService.changePassword(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return CafeUtils.getResponceEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
        try{
            return userService.forgotPassword(requestMap);

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return CafeUtils.getResponceEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
