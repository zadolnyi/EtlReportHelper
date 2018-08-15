package com.oboz.reporthelper.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;

import javax.persistence.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@Entity
@Table(name="report3", schema="public")
public class Report3 implements ReportLine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String num;
    @Column(name = "origin_order_num")
    private String originOrderNum;
    @Column(name = "order_date")
    private String orderDate;
    private String cost;
    @Column(name = "return_of_documents")
    private String returnOfDocuments;
    @Column(name = "payment_status")
    private String paymentStatus;
    @Column(name = "client_id")
    private Long clientId;
    @Column(name = "client_name")
    private String clientName;

    @Override
    public ReportLine mapEntry(ResultSet rs) throws SQLException {
        //setId(rs.getLong("id"));
        this.setNum(rs.getString("num"));
        setOriginOrderNum(rs.getString("origin_order_num"));
        setOrderDate(rs.getString("order_date"));
        setCost(rs.getString("cost"));
        setReturnOfDocuments(rs.getString("return_of_documents"));
        setPaymentStatus(rs.getString("payment_status"));
        setClientId(rs.getLong("client_id"));
        setClientName(rs.getString("client_name"));
        return this;
    }

    @Override
    public ParameterizedPreparedStatementSetter getParamSetter() {
        return new ParameterizedPreparedStatementSetter<Report3>() {
            @Override
            public void setValues(PreparedStatement ps, Report3 report) throws SQLException {
                //ps.setLong(1, report.getId());
                ps.setString(1, report.getNum());
                ps.setString(2, report.getOriginOrderNum());
                ps.setString(3, report.getOrderDate());
                ps.setString(4, report.getCost());
                ps.setString(5, report.getReturnOfDocuments());
                ps.setString(6, report.getPaymentStatus());
                ps.setLong(7, report.getClientId());
                ps.setString(8, report.getClientName());
            }
        };
    }
}
