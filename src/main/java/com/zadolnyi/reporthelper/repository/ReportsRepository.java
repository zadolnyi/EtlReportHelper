package com.zadolnyi.reporthelper.repository;

import com.zadolnyi.reporthelper.model.Reports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface ReportsRepository extends JpaRepository<Reports, Long> {

    @Query(value = "SELECT * FROM pg_tables p WHERE p.tablename= :reportTableName", nativeQuery = true)
    Collection<Object> findReportTable(@Param("reportTableName") String reportTableName);
}
