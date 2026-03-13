package vn.edu.demo_spmvc.entity;

import jakarta.persistence.*;
import lombok.*;
import vn.edu.demo_spmvc.enums.Role;

@Entity
@Table(name = "tblUsers")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.USER;
}