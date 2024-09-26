package com.Test1RorApplication.RORApplicationTesting.Service;

import com.Test1RorApplication.RORApplicationTesting.Model.RorMaster;
import com.Test1RorApplication.RORApplicationTesting.Repository.RorMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RORMasterCoreService {

    @Autowired
    RorMasterRepository rorMasterRepository;

    @Transactional
    public RorMaster saveRorMaster(RorMaster rorMaster) {
        return rorMasterRepository.save(rorMaster);
    }
}
