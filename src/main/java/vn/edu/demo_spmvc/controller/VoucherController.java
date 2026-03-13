package vn.edu.demo_spmvc.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.demo_spmvc.dto.VoucherDTO;
import vn.edu.demo_spmvc.service.VoucherService;

@RestController
@RequestMapping("/api/vouchers")
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public VoucherDTO create(@Valid @RequestBody VoucherDTO dto) {
        return voucherService.create(dto);
    }
}