package com.Test1RorApplication.RORApplicationTesting.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class WardFamilyCounter {

    @Id
    @GeneratedValue
    private Long id;

    // The ward number (1 to 99)
    @Column(nullable = false, unique = true)
    private int wardNumber;

    //Number of families registered in this ward
    @Column(nullable = false)
    private int familyCount;
}
