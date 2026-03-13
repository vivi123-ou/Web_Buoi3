package vn.edu.demo_spmvc.service;

import vn.edu.demo_spmvc.dto.AuthResponseDTO;
import vn.edu.demo_spmvc.dto.LoginDTO;
import vn.edu.demo_spmvc.dto.RegisterDTO;

public interface AuthService {
    AuthResponseDTO register(RegisterDTO dto);
    AuthResponseDTO login(LoginDTO dto);
}