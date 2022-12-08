package backend.Entities;

import DBS;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Part {

    private Integer part_number;
    private BigInteger customer_id;
    private BigInteger part_name_id;
    private BigInteger category_id;
    private BigInteger drawing_id;
    private Short rubber;
    private Double diameter_AT;
    private String diameter_AT_tol;
    private Double length_L_AT;
    private String length_L_AT_tol;
    private Double diameter_IT;
    private String diameter_IT_tol;
    private Double length_L_IT;
    private String length_L_IT_tol;
    private Double diameter_ZT;
    private String diameter_ZT_tol;
    private Double length_L_ZT;
    private String length_L_ZT_tol;
    private Integer cr_steg;
    private Short cr_niere;
    private Short ca;
    private Double ct;
    private Double ck;
    private Integer rating;

    public Integer getPart_number() {return part_number;}
    public void setPart_number(Integer part_number) {this.part_number = part_number;}

    public BigInteger getCustomer_id() {return customer_id;}
    public void setCustomer_id(BigInteger customer_id) {
        this.customer_id = customer_id;
    }

    public BigInteger getPart_name_id() {
        return part_name_id;
    }
    public void setPart_name_id(BigInteger part_name_id) {this.part_name_id = part_name_id;}

    public BigInteger getCategory_id() {
        return category_id;
    }
    public void setCategory_id(BigInteger category_id) {
        this.category_id = category_id;
    }

    public BigInteger getDrawing_id() {
        return drawing_id;
    }
    public void setDrawing_id(BigInteger drawing_id) {
        this.drawing_id = drawing_id;
    }

    public Short getRubber() {
        return rubber;
    }
    public void setRubber(Short rubber) {
        this.rubber = rubber;
    }

    public Double getDiameter_AT() {return diameter_AT;}
    public void setDiameter_AT(Double diameter_AT) {this.diameter_AT = diameter_AT;}

    public String getDiameter_AT_tol() {
        return diameter_AT_tol;
    }
    public void setDiameter_AT_tol(String diameter_AT_tol) {
        this.diameter_AT_tol = diameter_AT_tol;
    }

    public Double getLength_L_AT() {
        return length_L_AT;
    }
    public void setLength_L_AT(Double length_L_AT) {
        this.length_L_AT = length_L_AT;
    }

    public String getLength_L_AT_tol() {
        return length_L_AT_tol;
    }
    public void setLength_L_AT_tol(String length_L_AT_tol) {
        this.length_L_AT_tol = length_L_AT_tol;
    }

    public Double getDiameter_IT() {
        return diameter_IT;
    }
    public void setDiameter_IT(Double diameter_IT) {
        this.diameter_IT = diameter_IT;
    }

    public String getDiameter_IT_tol() {
        return diameter_IT_tol;
    }
    public void setDiameter_IT_tol(String diameter_IT_tol) {
        this.diameter_IT_tol = diameter_IT_tol;
    }

    public Double getLength_L_IT() {
        return length_L_IT;
    }
    public void setLength_L_IT(Double length_L_IT) {
        this.length_L_IT = length_L_IT;
    }

    public String getLength_L_IT_tol() {
        return length_L_IT_tol;
    }
    public void setLength_L_IT_tol(String length_L_IT_tol) {
        this.length_L_IT_tol = length_L_IT_tol;
    }

    public Double getDiameter_ZT() {
        return diameter_ZT;
    }
    public void setDiameter_ZT(Double diameter_ZT) {
        this.diameter_ZT = diameter_ZT;
    }

    public String getDiameter_ZT_tol() {
        return diameter_ZT_tol;
    }
    public void setDiameter_ZT_tol(String diameter_ZT_tol) {
        this.diameter_ZT_tol = diameter_ZT_tol;
    }

    public Double getLength_L_ZT() {
        return length_L_ZT;
    }
    public void setLength_L_ZT(Double length_L_ZT) {
        this.length_L_ZT = length_L_ZT;
    }

    public String getLength_L_ZT_tol() {
        return length_L_ZT_tol;
    }
    public void setLength_L_ZT_tol(String length_L_ZT_tol) {
        this.length_L_ZT_tol = length_L_ZT_tol;
    }

    public Integer getCr_steg() {
        return cr_steg;
    }
    public void setCr_steg(Integer cr_steg) {
        this.cr_steg = cr_steg;
    }

    public Short getCr_niere() {
        return cr_niere;
    }
    public void setCr_niere(Short cr_niere) {
        this.cr_niere = cr_niere;
    }

    public Short getCa() {
        return ca;
    }
    public void setCa(Short ca) {
        this.ca = ca;
    }

    public Double getCt() {return ct;}
    public void setCt(Double ct) {
        this.ct = ct;
    }

    public Double getCk() {return ck;}
    public void setCk(Double ck) {
        this.ck = ck;
    }

    public Integer getRating() {return rating;}
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public void insert() throws SQLException {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("INSERT INTO parts (customer_id, part_name_id, category_id, drawing_id, rubber, diameter_AT, diameter_AT_tol, length_L_AT, length_L_AT_tol, diameter_IT, diameter_IT_tol, length_L_IT, length_L_IT_tol, diameter_ZT, diameter_ZT_tol, length_L_ZT, length_L_ZT_tol, cr_steg, cr_niere, ca, ct, ck, rating) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            s.setBigInteger(1, customer_id);
            s.setBigInteger(2, part_name_id);
            s.setBigInteger(3, category_id);
            s.setBigInteger(4, drawing_id);
            s.setShort(5, rubber);
            s.setDouble(6, diameter_AT);
            s.setString(7, diameter_AT_tol);
            s.setDouble(8, length_L_AT);
            s.setString(9, length_L_AT_tol);
            s.setDouble(10, diameter_IT);
            s.setString(11, diameter_IT_tol);
            s.setDouble(12, length_L_IT);
            s.setString(13, length_L_IT_tol);
            s.setDouble(14, diameter_ZT);
            s.setString(15, diameter_ZT_tol);
            s.setDouble(16, length_L_ZT);
            s.setString(17, length_L_ZT_tol);
            s.setInteger(18, cr_steg);
            s.setShort(19, cr_niere);
            s.setShort(20, ca);
            s.setDouble(21, ct);
            s.setDouble(22, ck);
            s.setInteger(23, rating);
            s.executeUpdate();

            try (ResultSet r = s.getGeneratedKeys()) {
                r.next();
                part_number = r.getInt(1);
            }
        }
    }

    public void update() throws SQLException {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("UPDATE parts SET customer_id = ?, part_name_id = ?, category_id = ?, drawing_id = ?, rubber = ?, diameter_AT = ?, diameter_AT_tol = ?, length_L_AT = ?, length_L_AT_tol = ?, diameter_IT = ?, diameter_IT_tol = ?, length_L_IT = ?, length_L_IT_tol = ?, diameter_ZT = ?, diameter_ZT_tol = ?, length_L_ZT = ?, length_L_ZT_tol = ?, cr_steg = ?, cr_niere = ?, ca = ?, ct = ?, ck = ?, rating = ? WHERE part_number = ?")) {
            s.setBigInteger(1, customer_id);
            s.setBigInteger(2, part_name_id);
            s.setBigInteger(3, category_id);
            s.setBigInteger(4, drawing_id);
            s.setShort(5, rubber);
            s.setDouble(6, diameter_AT);
            s.setString(7, diameter_AT_tol);
            s.setDouble(8, length_L_AT);
            s.setString(9, length_L_AT_tol);
            s.setDouble(10, diameter_IT);
            s.setString(11, diameter_IT_tol);
            s.setDouble(12, length_L_IT);
            s.setString(13, length_L_IT_tol);
            s.setDouble(14, diameter_ZT);
            s.setString(15, diameter_ZT_tol);
            s.setDouble(16, length_L_ZT);
            s.setString(17, length_L_ZT_tol);
            s.setInteger(18, cr_steg);
            s.setShort(19, cr_niere);
            s.setShort(20, ca);
            s.setDouble(21, ct);
            s.setDouble(22, ck);
            s.setInteger(23, rating);
            s.setInt(24, part_number);

            s.executeUpdate();
        }
    }

    public void delete() throws SQLException {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("DELETE FROM parts WHERE part_number = ?")) {
            s.setInt(1, part_number);

            s.executeUpdate();
        }
    }
}