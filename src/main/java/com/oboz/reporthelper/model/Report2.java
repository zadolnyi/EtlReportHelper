package com.oboz.reporthelper.model;

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
@Table(name="report2", schema="target_schema")
public class Report2 implements ReportLine {
    @Id
    private Long id;
    @Column(name = "column_3")
    private String column3;
    @Column(name = "column_4")
    private String column4;
    @Column(name = "column_5")
    private Integer column5;

    @Override
    public ReportLine mapEntry(ResultSet rs) throws SQLException {
        setId(rs.getLong("id"));
        this.setColumn3(rs.getString("column_3"));
        this.setColumn4(rs.getString("column_4"));
        this.setColumn5(rs.getInt("column_5"));
        return this;
    }

    @Override
    public ParameterizedPreparedStatementSetter getParamSetter() {
        return new ParameterizedPreparedStatementSetter<Report2>() {
            @Override
            public void setValues(PreparedStatement ps, Report2 report) throws SQLException {
                ps.setLong(1, report.getId());
                ps.setString(2, report.getColumn3());
                ps.setString(3, report.getColumn4());
                ps.setInt(4, report.getColumn5());
            }
        };
    }
}
