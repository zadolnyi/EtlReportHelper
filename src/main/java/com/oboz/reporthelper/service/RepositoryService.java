package com.oboz.reporthelper.service;

import com.oboz.reporthelper.repository.Report1Repository;
import com.oboz.reporthelper.repository.Report2Repository;
import com.oboz.reporthelper.repository.Report3Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class RepositoryService {

    @Autowired
    private Report1Repository report1Repository;

    @Autowired
    private Report2Repository report2Repository;

    @Autowired
    private Report3Repository report3Repository;

    public JpaRepository getJpaRepository(String reportClassName) {
        JpaRepository repository = null;
        if (reportClassName.equals("Report1")) {
            repository = report1Repository;
        } else if (reportClassName.equals("Report2")) {
            repository = report2Repository;
        } else if (reportClassName.equals("Report3")) {
            repository = report3Repository;
        }
        return repository;
    }
}
