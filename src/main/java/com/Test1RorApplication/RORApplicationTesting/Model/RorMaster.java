package com.Test1RorApplication.RORApplicationTesting.Model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class RorMaster {

    @Id
    @GeneratedValue
    private UUID rorMasterId;

    @Column(nullable = false)
    private String oldRorId;


    private String ERorId;

    @Column(nullable = false, unique = true)
    private String rationCardNumber;

    @Column(nullable = true)
    private int familyIncome;

    @Builder.Default()
    private String createdByUser="System";

    @org.springframework.data.annotation.CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;
 
    private String lastModifiedByUser;

    @org.springframework.data.annotation.LastModifiedDate
    private LocalDateTime lastModifiedDate;


}
