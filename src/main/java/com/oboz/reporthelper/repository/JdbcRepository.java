package com.oboz.reporthelper.repository;

import com.oboz.reporthelper.model.ReportFactory;
import com.oboz.reporthelper.model.ReportLine;
import com.oboz.reporthelper.model.Reports;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;

@Repository
public class JdbcRepository {

    @Autowired
    @Qualifier("sourceJdbcTemplate")
    private JdbcTemplate sourceJdbcTemplate;

    @Autowired
    @Qualifier("targetJdbcTemplate")
    private JdbcTemplate targetJdbcTemplate;

    public void createReportTable(String sqlCreateTable) {
        targetJdbcTemplate.execute(sqlCreateTable);
    }

    public List<ReportLine> getNewReportData(Reports report) {
        String sqlSelect = report.getSqlSourceQuery();
        List<ReportLine> entries = sourceJdbcTemplate.query(sqlSelect, (ResultSet rs, int rowNum) ->
                ReportFactory.getReport(report).mapEntry(rs));
        return entries;
    }

    public void insertAll(Reports report, List<ReportLine> entriesToAdd) {
        targetJdbcTemplate.batchUpdate(report.getSqlInsert(), entriesToAdd,
                entriesToAdd.size(),                 // or batchSize=10
                entriesToAdd.get(0).getParamSetter());
    }
}
