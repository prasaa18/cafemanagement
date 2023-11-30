package com.in.cafe.cafemanagement.service;

import com.in.cafe.cafemanagement.Model.Bill;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface BillService {

    public ResponseEntity<String> generateReport(Map<String, Object> requestMap);

    ResponseEntity<List<Bill>> getBills();

    ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap);

    ResponseEntity<String> deleteBill(Integer id);
}
