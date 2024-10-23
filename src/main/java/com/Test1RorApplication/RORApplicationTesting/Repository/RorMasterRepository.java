package com.Test1RorApplication.RORApplicationTesting.Repository;

import com.Test1RorApplication.RORApplicationTesting.DTO.RorDetailsDTO;
import com.Test1RorApplication.RORApplicationTesting.Model.FamilyMembers;
import com.Test1RorApplication.RORApplicationTesting.Model.RorMaster;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RorMasterRepository extends JpaRepository<RorMaster, UUID> {
    Optional<RorMaster> findByRorMasterId(UUID rorMasterId);

    @Query("""
        SELECT new com.Test1RorApplication.RORApplicationTesting.DTO.RorDetailsDTO(
            rm.rationCardNumber,
            ri.rorId,
            CONCAT(
                COALESCE(fm.firstName, ''),
                CASE WHEN fm.middleName IS NOT NULL THEN CONCAT(' ', fm.middleName) ELSE '' END,
                ' ',
                fm.lastName
            )
        )
        FROM RorMaster rm
        LEFT JOIN RorId ri ON ri.rorMasterId = rm.rorMasterId
        LEFT JOIN FamilyMembers fm ON fm.rorMasterId = rm.rorMasterId
        WHERE rm.createdByUser = :createdByUser
        AND LOWER(fm.relationWithHOF) IN ('self', 'SELF', 'Self')
        AND fm.headOfFamily = true
        ORDER BY rm.createdDate DESC
        """)
    List<RorDetailsDTO> findRorDetailsByCreatedUser(@Param("createdByUser") String createdByUser, Pageable pageable);


    @Query("""
       SELECT COUNT(rm.rorMasterId)
       FROM RorMaster rm
       WHERE rm.createdByUser = :createdByUser
       """)
    Long countRorDetailsByCreatedUser(@Param("createdByUser") String createdByUser);
}
