package com.oboz.reporthelper.service;

import com.oboz.reporthelper.AppException;
import com.oboz.reporthelper.model.Reports;
import com.oboz.reporthelper.model.ReportLine;
import com.oboz.reporthelper.repository.JdbcRepository;
import com.oboz.reporthelper.repository.ReportsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class ReporterService {

    @Autowired
    ReportsRepository reportRepository;

    @Autowired
    JdbcRepository jdbcRepository;

    @Autowired
    RepositoryService repositoryService;

    public void start() {
        log.info("ReporterService start");
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        List<Reports> reports = new ArrayList<>();
        reportRepository.findAll().forEach(reports::add);
        log.info("Reports count: {}", reports.size());

        reports.forEach(theReport -> {
            Runnable task = () -> {
                try {
                    System.out.println("theReport="+theReport);
                    createReport(theReport);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
            executor.scheduleWithFixedDelay(task, 0, theReport.getPeriod(), TimeUnit.SECONDS);
        });
    }

    @Transactional
    void createReport(Reports report) throws SQLException {
        boolean isReportTableExists = reportRepository.findReportTable(report.getReportTableName()).size() >= 1;
        if (!isReportTableExists) {
            jdbcRepository.createReportTable(report.getSqlCreateTable());
            log.info("Report: {} was created", report.getClassName());

        }
        List<ReportLine> freshEntries = jdbcRepository.getNewReportData(report);

        JpaRepository repJpaRepository = repositoryService.getJpaRepository(report.getClassName());
        List<ReportLine> oldEntries;
        if (report.isRecreate()) {
            oldEntries = repJpaRepository.findAll();
        } else {
            oldEntries = repJpaRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
        }
        log.info("Report: {} было: {} стало: {}", report.getClassName(), oldEntries.size(), freshEntries.size());
        if (report.isRecreate()) {
            recreateReport(freshEntries, report, repJpaRepository);
        } else {
            if (isReportTableExists && oldEntries.size() != 0) {
                try {
                    updateReport(oldEntries, freshEntries, repJpaRepository);
                    if (oldEntries.size() < freshEntries.size()) {
                        List<ReportLine> entriesNew = freshEntries.subList(oldEntries.size(), freshEntries.size());
                        addToReport(entriesNew, report, repJpaRepository);
                    }
                } catch (AppException e) {
                    log.info(e.getMessage());
                    log.info("Full recreated report: {}", report.getClassName());
                    recreateReport(freshEntries, report, repJpaRepository);
                }
            } else {
                addToReport(freshEntries, report, repJpaRepository);
            }
        }
        repJpaRepository.flush();
    }

    private void recreateReport(List<ReportLine> entriesToAdd, Reports report, JpaRepository repJpaRepository) {
        repJpaRepository.deleteAllInBatch();
        addToReport(entriesToAdd, report, repJpaRepository);
    }

    private void addToReport(List<ReportLine> entriesToAdd, Reports report, JpaRepository repJpaRepository) {
        log.info("Report: {} to add: {}", report.getClassName(), entriesToAdd.size());
        String sqlInsert = report.getSqlInsert();
        if (sqlInsert == null || sqlInsert.length() < 20) {
            repJpaRepository.saveAll(entriesToAdd);
        } else {
            jdbcRepository.insertAll(report, entriesToAdd);
        }
        log.info("Report: {} was added: {}", report.getClassName(), entriesToAdd.size());
    }

    private void updateReport(List<ReportLine> oldEntries, List<ReportLine> freshEntries, JpaRepository repJpaRepository) throws AppException {
        if (oldEntries.size() > freshEntries.size()) {
            throw new AppException("AppException: Количество записей в отчёте уменьшилось. Было:"+oldEntries.size()+" стало:"+ freshEntries.size());
        }
        int newIndex = 0;
        int oldIndex = 0;
        int updatedCount = 0;
        for (ReportLine oldLine : oldEntries) {
            ReportLine newLine = freshEntries.get(newIndex);
            long newId = newLine.getId();
            long oldId = newLine.getId();
            if (newId == oldId) {
                if (!newLine.equals(oldLine)) {
                    log.info("Перед обновлением: {}", oldLine);
                    log.info("После обновления : {}", newLine);
                    repJpaRepository.save(newLine);
                    updatedCount++;
                }
            } else if (oldId < newId) {
                throw new AppException("AppException: Пропала запись отчёта." + " oldId < newId newIndex="+newIndex+ " newId= " +newId+" oldIndex="+oldIndex+ " oldId= " + oldId );

            } else if (oldId > newId) {
                throw new AppException("AppException: Вставилась запись отчёта. newId="+newId);
            }
            newIndex++;
            oldIndex++;
        }
        log.info("was updated: {}", updatedCount);
    }
}
