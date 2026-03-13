package vn.edu.demo_spmvc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.demo_spmvc.dto.VoucherDTO;
import vn.edu.demo_spmvc.entity.Voucher;
import vn.edu.demo_spmvc.repository.VoucherRepository;
import vn.edu.demo_spmvc.service.VoucherService;

@Service
@RequiredArgsConstructor
@Transactional
public class VoucherServiceImpl implements VoucherService {

    private final VoucherRepository voucherRepository;

    @Override
    public VoucherDTO create(VoucherDTO dto) {
        Voucher voucher = Voucher.builder()
                .code(dto.getCode())
                .type(dto.getType())
                .value(dto.getValue())
                .expiryDate(dto.getExpiryDate())
                .used(false)
                .build();
        Voucher saved = voucherRepository.save(voucher);
        return new VoucherDTO(saved.getId(), saved.getCode(),
                saved.getType(), saved.getValue(), saved.getExpiryDate());
    }
}