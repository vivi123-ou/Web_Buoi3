package vn.edu.demo_spmvc.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import vn.edu.demo_spmvc.dto.CustomerDTO;
import vn.edu.demo_spmvc.service.CustomerService;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public CustomerDTO create(@Valid @RequestBody CustomerDTO dto) {
        return customerService.create(dto);
    }

    @GetMapping
    public List<CustomerDTO> getAll() {
        return customerService.getAll();
    }
}