package com.in.cafe.cafemanagement.ServiceImpl;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.in.cafe.cafemanagement.JWT.JwtFilter;
import com.in.cafe.cafemanagement.Model.Bill;
import com.in.cafe.cafemanagement.constants.CafeConstants;
import com.in.cafe.cafemanagement.dao.BillDao;
import com.in.cafe.cafemanagement.service.BillService;
import com.in.cafe.cafemanagement.utils.CafeUtils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.description.method.MethodDescription;
import org.apache.pdfbox.io.IOUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;


import java.io.*;
import java.util.*;

import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
public class BillServiceImpl implements BillService {

    @Autowired
    JwtFilter jwtFilter;
    @Autowired
    BillDao billDao;

    @Override
    public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {
        log.info("inside generate report method");
        try {
            String filename;
            if (ValidateRequestMap(requestMap)) {
                if (requestMap.containsKey("isGenerate") && !(Boolean) requestMap.get("isGenerate")) {
                    filename = (String) requestMap.get("uuid");
                } else {
                    filename = CafeUtils.getUUID(); // Assuming CafeUtils.getUUID() returns a String
                    requestMap.put("uuid", filename);
                    insertBill(requestMap);
                }
                String data = "Name:" + requestMap.get("name") + "\n" + "contactNumber :" + requestMap.get("contactNumber")
                        + "\n" + "Email:+" + requestMap.get("email") + "\n" + "paymentMethod:" + requestMap.get("paymentMethod") + "\n";

                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(CafeConstants.STORE_LOCATION + "\\" + filename + ".pdf"));

                document.open();
                setRectanglePdf(document);
                Paragraph chunk = new Paragraph("Cafe Management System", getFont("Header"));
                chunk.setAlignment(Element.ALIGN_CENTER);
                document.add(chunk);
                Paragraph paragraph = new Paragraph(data + "\n", getFont("Data"));
                document.add(paragraph);


                PdfPTable table = new PdfPTable(5);
                table.setWidthPercentage(100);
                addTableHeader(table);

                JSONArray jsonArray = CafeUtils.getJsonArrayFromString((String) requestMap.get("productDetails"));

                for (int i = 0; i < jsonArray.length(); i++) {
                    addRow(table, CafeUtils.getMapFromJson(jsonArray.getString(i)));
                }
                document.add(table);

                Paragraph footer = new Paragraph("Total:" + requestMap.get("totalAmount") + "\n"
                        + "Thank you for visiting ,please visit again!!", getFont("Data"));

                document.add(footer);
                document.close();
                return new ResponseEntity<>("{\"uuid\":\"" + filename + "\"}", HttpStatus.OK);

            }
            return CafeUtils.getResponceEntity("Required data not found", HttpStatus.BAD_REQUEST);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponceEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private void addRow(PdfPTable table, Map<String, Object> data) {
        log.info("Inside add Row");

        table.addCell((String) data.get("name"));
        table.addCell((String) data.get("category"));
        table.addCell((String) data.get("quantity"));
        table.addCell(Double.toString((Double) data.get("price")));
        table.addCell(Double.toString((Double) data.get("total")));
    }


    private void addTableHeader(PdfPTable table) {

        log.info("inside addTableHeader");
        Stream.of("Name", "Category", "Quantity", "Price", "Sub Total")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    header.setBackgroundColor(BaseColor.YELLOW);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setVerticalAlignment(Element.ALIGN_CENTER);
                    table.addCell(header);
                });

    }


    private Font getFont(String type) {
        log.info("Inside getFont");
        switch (type) {
            case "Header":
                Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 18, BaseColor.BLACK);
                headerFont.setStyle(Font.BOLD);
                return headerFont;

            case "Data":
                Font dataFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, BaseColor.BLACK);
                dataFont.setStyle(Font.BOLD);
                return dataFont;
            default:
                return new Font();


        }
    }

    private void setRectanglePdf(Document document) throws DocumentException {
        log.info("inside SetRectanglepdf");
        Rectangle rectangle = new Rectangle(577, 825, 18, 15);
        rectangle.enableBorderSide(1);
        rectangle.enableBorderSide(2);
        rectangle.enableBorderSide(4);
        rectangle.enableBorderSide(8);
        rectangle.setBorderColor(BaseColor.BLACK);
        rectangle.setBorderWidth(1);
        document.add(rectangle);

    }

    private void insertBill(Map<String, Object> requestMap) {
        try {
            Bill bill = new Bill();
            bill.setUuid(requestMap.get("uuid").toString());
            bill.setName(requestMap.get("name").toString());
            bill.setEmail(requestMap.get("email").toString());
            bill.setContactNumber(requestMap.get("contactNumber").toString());
            bill.setPaymentmethod(requestMap.get("paymentMethod").toString());
            bill.setTotal(Integer.parseInt(requestMap.get("totalAmount").toString()));
            bill.setProductDetails(requestMap.get("productDetails").toString());
            bill.setCreatedBy(jwtFilter.getCurrentUser());

            billDao.save(bill);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private boolean ValidateRequestMap(Map<String, Object> requestMap) {
        return requestMap.containsKey("name") &&
                requestMap.containsKey("contactNumber") &&
                requestMap.containsKey("email") &&
                requestMap.containsKey("paymentMethod") &&
                requestMap.containsKey("productDetails") &&
                requestMap.containsKey("totalAmount");
    }

    @Override
    public ResponseEntity<List<Bill>> getBills() {
        List<Bill> list = new ArrayList<>();
        if (jwtFilter.isAdmin()) {

            list = billDao.getAllBills();

        } else {

            list = billDao.getBillByUserName(jwtFilter.getCurrentUser());
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap) {
        log.info("Inside get pdf requestMap:{}" + requestMap);

        try {

            byte[] byteArray = new byte[0];

            if (!requestMap.containsKey("uuid") && ValidateRequestMap(requestMap)) {

                return new ResponseEntity<>(byteArray, HttpStatus.BAD_REQUEST);
            }
            String filePath = CafeConstants.STORE_LOCATION + "\\" + (String) requestMap.get("uuid") + ".pdf";

            if (CafeUtils.isFileExists(filePath)) {
                byteArray = getByteArray(filePath);
                return new ResponseEntity<>(byteArray, HttpStatus.OK);

            } else {
                requestMap.put("isGenerate", false);
                generateReport(requestMap);
                byteArray = getByteArray(filePath);
                return new ResponseEntity<>(byteArray, HttpStatus.OK);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    private byte[] getByteArray(String filePath) throws IOException {

        File intialFile = new File(filePath);
        InputStream targetStream = new FileInputStream(intialFile);
        byte[] byteArray = IOUtils.toByteArray(targetStream);
        targetStream.close();
        return byteArray;
    }

    @Override
    public ResponseEntity<String> deleteBill(Integer id) {
        try {
            Optional optional=billDao.findById(id);
            if(!optional.isEmpty()){
                billDao.deleteById(id);
                return  CafeUtils.getResponceEntity("Bill deleted Sucessfully",HttpStatus.OK);
            }
            return  CafeUtils.getResponceEntity("Bill id Doesnot exits",HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return CafeUtils.getResponceEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
