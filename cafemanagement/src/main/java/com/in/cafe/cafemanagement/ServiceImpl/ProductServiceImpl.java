package com.in.cafe.cafemanagement.ServiceImpl;


import com.in.cafe.cafemanagement.JWT.JwtFilter;
import com.in.cafe.cafemanagement.Model.Category;
import com.in.cafe.cafemanagement.Model.Product;
import com.in.cafe.cafemanagement.constants.CafeConstants;
import com.in.cafe.cafemanagement.dao.ProductDao;
import com.in.cafe.cafemanagement.service.ProductService;
import com.in.cafe.cafemanagement.utils.CafeUtils;
import com.in.cafe.cafemanagement.wrapper.ProductWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;
    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {

        try {

            if (jwtFilter.isAdmin()) {

                if (validateProductMap(requestMap, false)) {
                    productDao.save(getProdutctFromMap(requestMap, false));


                    return CafeUtils.getResponceEntity("Product Added Successfully ", HttpStatus.OK);
                }
                return CafeUtils.getResponceEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);


            } else {
                return CafeUtils.getResponceEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponceEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private boolean validateProductMap(Map<String, String> requestMap, boolean validateId) {

        if (requestMap.containsKey("name")) {
            if (requestMap.containsKey("id") && validateId) {
                return true;
            } else if (!validateId) {
                return true;

            }
        }
        return false;
    }

    private Product getProdutctFromMap(Map<String, String> requestMap, boolean isAdd) {
        Category category = new Category();
        category.setId(Integer.parseInt(requestMap.get("categoryId")));
        Product product = new Product();

        if (isAdd) {
            product.setId(Integer.parseInt(requestMap.get("id")));
        } else {
            product.setStatus("true");
        }
        product.setCategory(category);
        product.setName(requestMap.get("name"));
        product.setDescription(requestMap.get("description"));
        product.setPrice(Integer.parseInt(requestMap.get("price")));
        return product;

    }


    @Override
    public ResponseEntity<List<ProductWrapper>> getAllProduct() {

        try {
            return new ResponseEntity<>(productDao.getAllProduct(), HttpStatus.OK);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {

                if (validateProductMap(requestMap, true)) {


                    Optional<Product> optional = productDao.findById(Integer.parseInt(requestMap.get("id")));
                    if (!optional.isEmpty()) {
                        Product product = getProdutctFromMap(requestMap, true);
                        product.setStatus("true");
                        productDao.save(product);

                        return CafeUtils.getResponceEntity("Product  Updated Successfully", HttpStatus.OK);


                    } else {
                        return CafeUtils.getResponceEntity("Product id Doesn't exist", HttpStatus.OK);

                    }


                } else {
                    return CafeUtils.getResponceEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
                }
            } else {

                return CafeUtils.getResponceEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponceEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deeleteProduct(Integer id) {
        try {

            if (jwtFilter.isAdmin()) {

                Optional optional = productDao.findById(id);
                if (!optional.isEmpty()) {

                    productDao.deleteById(id);
                    return CafeUtils.getResponceEntity("Product Deelted Successfully", HttpStatus.OK);

                }
                return CafeUtils.getResponceEntity("Product id  doesn't exist", HttpStatus.OK);


            } else {
                return CafeUtils.getResponceEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return CafeUtils.getResponceEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {

                Optional<Product> optional = productDao.findById(Integer.parseInt(requestMap.get("id")));

                if (!optional.isEmpty()) {
                    productDao.updateProductStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
                    return CafeUtils.getResponceEntity("Product Status Updated Successfully", HttpStatus.OK);
                }
                return CafeUtils.getResponceEntity("Product id Doesn't exist", HttpStatus.OK);
            } else {
                return CafeUtils.getResponceEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return CafeUtils.getResponceEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getCategory(Integer id) {
        try {
           return new ResponseEntity<>(productDao.getProductByCategory(id),HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<ProductWrapper> getProductById(Integer id) {
        try {
            return new ResponseEntity<>(productDao.getProductById(id),HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ProductWrapper(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

}
