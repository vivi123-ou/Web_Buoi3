package vn.edu.demo_spmvc.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.edu.demo_spmvc.dto.ProductDTO;
import vn.edu.demo_spmvc.entity.Product;
import vn.edu.demo_spmvc.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;

    @PostMapping
    public ProductDTO create(@Valid @RequestBody ProductDTO dto){
        return service.create(dto);
    }

    @GetMapping
    public List<ProductDTO> getAll(){
        return service.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }


}
