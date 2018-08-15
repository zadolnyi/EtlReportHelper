package com.oboz.reporthelper.model;

import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface ReportLine extends Serializable {
    ReportLine mapEntry(ResultSet rs) throws SQLException;
    ParameterizedPreparedStatementSetter getParamSetter();
    Long getId();
}
