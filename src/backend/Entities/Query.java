package backend.Entities;

import backend.Sessions.DBS;

import java.sql.*;

public class Query {
    private Integer query_id;
    private Integer user_id;

    private Short rubber_from;
    private Short rubber_to;

    private Double diameter_AT_from;
    private Double diameter_AT_to;

    private Double length_L_AT_from;
    private Double length_L_AT_to;

    private Double diameter_IT_from;
    private Double diameter_IT_to;

    private Double length_L_IT_from;
    private Double length_L_IT_to;

    private Double diameter_ZT_from;
    private Double diameter_ZT_to;

    private Double length_L_ZT_from;
    private Double length_L_ZT_to;

    private Integer cr_steg_from;
    private Integer cr_steg_to;

    private Short cr_niere_from;
    private Short cr_niere_to;

    private Short ca_from;
    private Short ca_to;

    private Double ct_from;
    private Double ct_to;

    private Double ck_from;
    private Double ck_to;
    private Timestamp date;

    public Integer getQuery_id() {
        return query_id;
    }

    public void setQuery_id(Integer query_id) {
        this.query_id = query_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Short getRubber_from() {
        return rubber_from;
    }

    public void setRubber_from(Short rubber_from) {
        this.rubber_from = rubber_from;
    }

    public Double getDiameter_AT_from() {
        return diameter_AT_from;
    }

    public void setDiameter_AT_from(Double diameter_AT_from) {
        this.diameter_AT_from = diameter_AT_from;
    }

    public Double getDiameter_AT_to() {
        return diameter_AT_to;
    }

    public void setDiameter_AT_to(Double diameter_AT_to) {
        this.diameter_AT_to = diameter_AT_to;
    }


    public Short getRubber_to() {
        return rubber_to;
    }

    public void setRubber_to(Short rubber_to) {
        this.rubber_to = rubber_to;
    }

    public Double getLength_L_AT_from() {
        return length_L_AT_from;
    }

    public void setLength_L_AT_from(Double length_L_AT_from) {
        this.length_L_AT_from = length_L_AT_from;
    }

    public Double getLength_L_AT_to() {
        return length_L_AT_to;
    }

    public void setLength_L_AT_to(Double length_L_AT_to) {
        this.length_L_AT_to = length_L_AT_to;
    }

    public Double getDiameter_IT_from() {
        return diameter_IT_from;
    }

    public void setDiameter_IT_from(Double diameter_IT_from) {
        this.diameter_IT_from = diameter_IT_from;
    }

    public Double getDiameter_IT_to() {
        return diameter_IT_to;
    }

    public void setDiameter_IT_to(Double diameter_IT_to) {
        this.diameter_IT_to = diameter_IT_to;
    }

    public Double getLength_L_IT_from() {
        return length_L_IT_from;
    }

    public void setLength_L_IT_from(Double length_L_IT_from) {
        this.length_L_IT_from = length_L_IT_from;
    }

    public Double getLength_L_IT_to() {
        return length_L_IT_to;
    }

    public void setLength_L_IT_to(Double length_L_IT_to) {
        this.length_L_IT_to = length_L_IT_to;
    }

    public Double getDiameter_ZT_from() {
        return diameter_ZT_from;
    }

    public void setDiameter_ZT_from(Double diameter_ZT_from) {
        this.diameter_ZT_from = diameter_ZT_from;
    }

    public Double getDiameter_ZT_to() {
        return diameter_ZT_to;
    }

    public void setDiameter_ZT_to(Double diameter_ZT_to) {
        this.diameter_ZT_to = diameter_ZT_to;
    }

    public Double getLength_L_ZT_from() {
        return length_L_ZT_from;
    }

    public void setLength_L_ZT_from(Double length_L_ZT_from) {
        this.length_L_ZT_from = length_L_ZT_from;
    }

    public Double getLength_L_ZT_to() {
        return length_L_ZT_to;
    }

    public void setLength_L_ZT_to(Double length_L_ZT_to) {
        this.length_L_ZT_to = length_L_ZT_to;
    }

    public Integer getCr_steg_from() {
        return cr_steg_from;
    }

    public void setCr_steg_from(Integer cr_steg_from) {
        this.cr_steg_from = cr_steg_from;
    }

    public Integer getCr_steg_to() {
        return cr_steg_to;
    }

    public void setCr_steg_to(Integer cr_steg_to) {
        this.cr_steg_to = cr_steg_to;
    }

    public Short getCr_niere_from() {
        return cr_niere_from;
    }

    public void setCr_niere_from(Short cr_niere_from) {
        this.cr_niere_from = cr_niere_from;
    }

    public Short getCr_niere_to() {
        return cr_niere_to;
    }

    public void setCr_niere_to(Short cr_niere_to) {
        this.cr_niere_to = cr_niere_to;
    }

    public Short getCa_from() {
        return ca_from;
    }

    public void setCa_from(Short ca_from) {
        this.ca_from = ca_from;
    }

    public Short getCa_to() {
        return ca_to;
    }

    public void setCa_to(Short ca_to) {
        this.ca_to = ca_to;
    }

    public Double getCk_from() {
        return ck_from;
    }

    public void setCk_from(Double ck_from) {
        this.ck_from = ck_from;
    }

    public Double getCk_to() {
        return ck_to;
    }

    public void setCk_to(Double ck_to) {
        this.ck_to = ck_to;
    }

    public Double getCt_from() {
        return ct_from;
    }

    public void setCt_from(Double ct_from) {
        this.ct_from = ct_from;
    }

    public Double getCt_to() {
        return ct_to;
    }

    public void setCt_to(Double ct_to) {
        this.ct_to = ct_to;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public void insert() throws SQLException {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("INSERT INTO queries (user_id, rubber_from, rubber_to, diameter_AT_from, diameter_AT_to, length_L_AT_from, length_L_AT_to, diameter_IT_from, diameter_IT_to, length_L_IT_from, length_L_IT_to, diameter_ZT_from, diameter_ZT_to, length_L_ZT_from, length_L_ZT_to, cr_steg_from, cr_steg_to, cr_niere_from, cr_niere_to, ca_from, ca_to, ct_from, ct_to, ck_from, ck_to, date) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            setVariables(s);
            s.setTimestamp(26, date);
            s.executeUpdate();

            try (ResultSet r = s.getGeneratedKeys()) {
                r.next();
                query_id = r.getInt(1);
            }
        }
    }

    public void update() throws SQLException {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("UPDATE queries SET user_id = ?, rubber_from = ?, rubber_to = ?, diameter_AT_from = ?, diameter_AT_to = ?, length_L_AT_from = ?, length_L_AT_to = ?, diameter_IT_from = ?, diameter_IT_to = ?, length_L_IT_from = ?, length_L_IT_to = ?, diameter_ZT_from = ?, diameter_ZT_to = ?, length_L_ZT_from = ?, length_L_ZT_to = ?, cr_steg_from = ?, cr_steg_to = ?, cr_niere_from = ?, cr_niere_to = ?, ca_from = ?, ca_to = ?, ct_from = ?, ct_to = ?, ck_from = ?, ck_to = ?, date = ? WHERE query_id = ?")) {
            setVariables(s);
            s.setInt(26, query_id);

            s.executeUpdate();
        }
    }

    private void setVariables(PreparedStatement s) throws SQLException {
        s.setObject(1, user_id);
        s.setObject(2, rubber_from);
        s.setObject(3, rubber_to);
        s.setObject(4, diameter_AT_from);
        s.setObject(5, diameter_AT_to);
        s.setObject(6, length_L_AT_from);
        s.setObject(7, length_L_AT_to);
        s.setObject(8, diameter_IT_from);
        s.setObject(9, diameter_IT_to);
        s.setObject(10, length_L_IT_from);
        s.setObject(11, length_L_IT_to);
        s.setObject(12, diameter_ZT_from);
        s.setObject(13, diameter_ZT_to);
        s.setObject(14, length_L_ZT_from);
        s.setObject(15, length_L_ZT_to);
        s.setObject(16, cr_steg_from);
        s.setObject(17, cr_steg_to);
        s.setObject(18, cr_niere_from);
        s.setObject(19, cr_niere_to);
        s.setObject(20, ca_from);
        s.setObject(21, ca_to);
        s.setObject(22, ct_from);
        s.setObject(23, ct_to);
        s.setObject(24, ck_from);
        s.setObject(25, ck_to);
    }

    public void delete() throws SQLException {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("DELETE FROM queries WHERE query_id = ?")) {
            s.setInt(1, query_id);

            s.executeUpdate();
        }
    }
}