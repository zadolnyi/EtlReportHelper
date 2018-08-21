package com.zadolnyi.reporthelper.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;

import javax.persistence.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@NoArgsConstructor
@Entity
@Table(name="report3", schema="target_schema")
public class Report3 implements ReportLine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "column_2")
    private String column2;
    @Column(name = "column_5")
    private Integer column5;

    @Override
    public ReportLine mapEntry(ResultSet rs) throws SQLException {
        //setId(rs.getLong("id"));
        this.setColumn2(rs.getString("column_2"));
        this.setColumn5(rs.getInt("column_5"));
        return this;
    }

    @Override
    public ParameterizedPreparedStatementSetter getParamSetter() {
        return new ParameterizedPreparedStatementSetter<Report3>() {
            @Override
            public void setValues(PreparedStatement ps, Report3 report) throws SQLException {
                //ps.setLong(1, report.getId());
                ps.setString(1, report.getColumn2());
                ps.setInt(2, report.getColumn5());
            }
        };
    }
}
