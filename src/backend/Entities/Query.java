package backend.Entities;

import DBS;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Query {

    private Integer query_id;
    private Integer user_id;
    private Short rubber;
    private Double diameter_AT;
    private Double length_L_AT;
    private Double diameter_IT;
    private Double length_L_IT;
    private Double diameter_ZT;
    private Double length_L_ZT;
    private Integer cr_steg;
    private Short cr_niere;
    private Short ca;
    private Double ct;
    private Double ck;
    private Date date;

    public Integer getQuery_id() {return query_id;}
    public void setQuery_id(Integer query_id) {this.query_id = query_id;}

    public Integer getUser_id() {return user_id;}
    public void setUser_id(Integer user_id) {this.user_id = user_id;}

    public Short getRubber() {return rubber;}
    public void setRubber(Short rubber) {this.rubber = rubber;}

    public Double getDiameter_AT() {return diameter_AT;}
    public void setDiameter_AT(Double diameter_AT) {this.diameter_AT = diameter_AT;}

    public Double getLength_L_AT() {return length_L_AT;}
    public void setLength_L_AT(Double length_L_AT) {this.length_L_AT = length_L_AT;}

    public Double getDiameter_IT() {return diameter_IT;}
    public void setDiameter_IT(Double diameter_IT) {this.diameter_IT = diameter_IT;}

    public Double getLength_L_IT() {return length_L_IT;}
    public void setLength_L_IT(Double length_L_IT) {this.length_L_IT = length_L_IT;}

    public Double getDiameter_ZT() {return diameter_ZT;}
    public void setDiameter_ZT(Double diameter_ZT) {this.diameter_ZT = diameter_ZT;}

    public Double getLength_L_ZT() {return length_L_ZT;}
    public void setLength_L_ZT(Double length_L_ZT) {this.length_L_ZT = length_L_ZT;}

    public Integer getCr_steg() {return cr_steg;}
    public void setCr_steg(Integer cr_steg) {this.cr_steg = cr_steg;}

    public Short getCr_niere() {return cr_niere;}
    public void setCr_niere(Short cr_niere) {this.cr_niere = cr_niere;}

    public Short getCa() {return ca;}
    public void setCa(Short ca) {this.ca = ca;}

    public Double getCt() {return ct;}
    public void setCt(Double ct) {this.ct = ct;}

    public Double getCk() {return ck;}
    public void setCk(Double ck) {this.ck = ck;}

    public Date getDate() {return date;}
    public void setDate(Date date) {this.date = date;}

    public void insert() throws SQLException {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("INSERT INTO queries (user_id, rubber, diameter_AT, length_L_AT, diameter_IT, length_L_IT, diameter_ZT, length_L_ZT, cr_steg, cr_niere, ca, ct, ck, date) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            s.setInteger(1, user_id);
            s.setShort(2, rubber);
            s.setDouble(3, diameter_AT);
            s.setDouble(4, length_L_AT);
            s.setDouble(5, diameter_IT);
            s.setDouble(6, length_L_IT);
            s.setDouble(7, diameter_ZT);
            s.setDouble(8, length_L_ZT);
            s.setInteger(9, cr_steg);
            s.setShort(10, cr_niere);
            s.setShort(11, ca);
            s.setDouble(12, ct);
            s.setDouble(13, ck);
            s.setDate(14, date);
            s.executeUpdate();

            try (ResultSet r = s.getGeneratedKeys()) {
                r.next();
                query_id = r.getInt(1);
            }
        }
    }

    public void update() throws SQLException {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("UPDATE queries SET user_id = ?, rubber = ?, diameter_AT = ?, length_L_AT = ?, diameter_IT = ?, length_L_IT = ?, diameter_ZT = ?, length_L_ZT = ?, cr_steg = ?, cr_niere = ?, ca = ?, ct = ?, ck = ?, date = ? WHERE query_id = ?")) {
            s.setInteger(1, user_id);
            s.setShort(2, rubber);
            s.setDouble(3, diameter_AT);
            s.setDouble(4, length_L_AT);
            s.setDouble(5, diameter_IT);
            s.setDouble(6, length_L_IT);
            s.setDouble(7, diameter_ZT);
            s.setDouble(8, length_L_ZT);
            s.setInteger(9, cr_steg);
            s.setShort(10, cr_niere);
            s.setShort(11, ca);
            s.setDouble(12, ct);
            s.setDouble(13, ck);
            s.setDate(14, date);
            s.setInt(15, query_id);

            s.executeUpdate();
        }
    }

    public void delete() throws SQLException {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("DELETE FROM queries WHERE query_id = ?")) {
            s.setInt(1, query_id);

            s.executeUpdate();
        }
    }
}