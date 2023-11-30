package com.in.cafe.cafemanagement.ServiceImpl;

import com.in.cafe.cafemanagement.dao.BillDao;
import com.in.cafe.cafemanagement.dao.CategoryDao;
import com.in.cafe.cafemanagement.dao.ProductDao;
import com.in.cafe.cafemanagement.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    CategoryDao categoryDao;
    @Autowired
    ProductDao productDao;
    @Autowired
    BillDao billDao;

    @Override
    public ResponseEntity<Map<String, Object>> getCount() {
        Map<String,Object> map= new HashMap<>();
        map.put("category",categoryDao.count());
        map.put("product",productDao.count());
        map.put("Bill",billDao.count());

        return new ResponseEntity<>(map, HttpStatus.OK);


    }
}
