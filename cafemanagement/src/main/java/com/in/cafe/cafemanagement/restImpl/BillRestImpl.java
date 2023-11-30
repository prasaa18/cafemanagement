package com.in.cafe.cafemanagement.restImpl;

import com.in.cafe.cafemanagement.Model.Bill;
import com.in.cafe.cafemanagement.constants.CafeConstants;
import com.in.cafe.cafemanagement.rest.BillRest;
import com.in.cafe.cafemanagement.service.BillService;
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
public class BillRestImpl implements BillRest {

    @Autowired
    BillService billService;

    @Override
    public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
        try{
            return  billService.generateReport(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponceEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Bill>> getBills() {
        try{
             return  billService.getBills();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return  new ResponseEntity<List<Bill>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap) {

        try{
            return  billService.getPdf(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return  null;

    }

    @Override
    public ResponseEntity<String> deleteBill(Integer id) {
         try{

             return  billService.deleteBill(id);
         }catch (Exception ex){
             ex.printStackTrace();
         }

        return CafeUtils.getResponceEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
