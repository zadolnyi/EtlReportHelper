package com.oboz.reporthelper.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="reports", schema="target_schema")
public class Reports {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String description;

    @Column(name = "class_name")
    private String className;

    @Column(name = "report_table_name")
    private String reportTableName;

    @Column(name = "sql_source_query")
    private String sqlSourceQuery;

    @Column(name = "sql_insert")
    private String sqlInsert;

    @Column(name = "sql_create_table")
    private String sqlCreateTable;

    private Integer period;

    @Column(name = "reset_rebuild_counter_date")
    private String resetRebuildCounterDate;

    @Column(name = "rebuild_count")
    private Integer RebuildCount;

    private boolean recreate;

    public Reports mapEntry(ResultSet rs) throws SQLException {
        setId(rs.getLong("id"));
        setName(rs.getString("name"));
        setDescription(rs.getString("description"));
        setClassName(rs.getString("class_name"));
        setReportTableName(rs.getString("report_table_name"));
        setSqlSourceQuery(rs.getString("sql_source_query"));
        setSqlInsert(rs.getString("sql_insert"));
        setSqlCreateTable(rs.getString("sql_create_table"));
        setPeriod(rs.getInt("period"));
        setResetRebuildCounterDate(rs.getString("reset_rebuild_counter_date"));
        setRebuildCount(rs.getInt("rebuild_count"));
        setRecreate(rs.getBoolean("recreate"));
        return this;
    }
}
