package backend.Entities;

import backend.Managers.DrawingManager;
import backend.Sessions.DBS;
import backend.Sessions.PartCountSession;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

import static backend.Models.Constants.WITHOUT_CATEGORY_ID;

public class Part {

    private String part_number;
    private BigInteger customer_id;
    private BigInteger part_name_id;
    private BigInteger category_id = WITHOUT_CATEGORY_ID;
    private BigInteger drawing_id;
    private Short rubber = null;
    private String rubber_string;
    private Double diameter_AT = null;
    private String diameter_AT_tol;
    private Double length_L_AT = null;
    private String length_L_AT_tol;
    private Double diameter_IT = null;
    private String diameter_IT_tol;
    private Double length_L_IT = null;
    private String length_L_IT_tol;
    private Double diameter_ZT = null;
    private String diameter_ZT_tol;
    private Double length_L_ZT = null;
    private String length_L_ZT_tol;
    private Integer cr_steg = null;
    private Short cr_niere = null;
    private Short ca = null;
    private Double ct = null;
    private Double ck = null;
    private Integer rating = 0;
    private Integer internal_rating;

    public String getPart_number() {
        return part_number;
    }

    public void setPart_number(String part_number) {
        this.part_number = part_number;
    }

    public BigInteger getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(BigInteger customer_id) {
        this.customer_id = customer_id;
    }

    public BigInteger getPart_name_id() {
        return part_name_id;
    }

    public void setPart_name_id(BigInteger part_name_id) {
        this.part_name_id = part_name_id;
    }

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

    public String getRubber_string() {
        return rubber_string;
    }

    public void setRubber_string(String rubber_string) {
        this.rubber_string = rubber_string;
    }

    public Double getDiameter_AT() {
        return diameter_AT;
    }

    public void setDiameter_AT(Double diameter_AT) {
        this.diameter_AT = diameter_AT;
    }

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

    public Double getCt() {
        return ct;
    }

    public void setCt(Double ct) {
        this.ct = ct;
    }

    public Double getCk() {
        return ck;
    }

    public void setCk(Double ck) {
        this.ck = ck;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getInternal_rating() {
        return internal_rating;
    }

    public void setInternal_rating(Integer internal_rating) {
        this.internal_rating = internal_rating;
    }

    public ImageView getImage() throws IOException {

        if (drawing_id == null) {
            ImageView imageView = new ImageView(SwingFXUtils.toFXImage(new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB), null));

            imageView.setFitHeight(200);
            imageView.setFitWidth(200);
            imageView.setPreserveRatio(true);

            return imageView;
        }

        Drawing image = DrawingManager.getDrawing(drawing_id);
        ByteArrayInputStream inStreambj = new ByteArrayInputStream(image.getDrawing());

        ImageView imageView = new ImageView(SwingFXUtils.toFXImage(ImageIO.read(inStreambj), null));
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);

        return imageView;
    }

    public void insert() throws SQLException {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("INSERT INTO parts (customer_id, part_name_id, category_id, drawing_id, rubber, rubber_string, diameter_AT, diameter_AT_tol, length_L_AT, length_L_AT_tol, diameter_IT, diameter_IT_tol, length_L_IT, length_L_IT_tol, diameter_ZT, diameter_ZT_tol, length_L_ZT, length_L_ZT_tol, cr_steg, cr_niere, ca, ct, ck, rating, internal_rating, part_number) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            setVariables(s);
            s.executeUpdate();
            PartCountSession.incrememntPartCount();
        }
    }

    private void setVariables(PreparedStatement s) throws SQLException {
        s.setObject(1, customer_id);
        s.setObject(2, part_name_id);
        s.setObject(3, category_id);
        s.setObject(4, drawing_id);
        s.setObject(5, rubber);
        s.setObject(6, rubber_string);
        s.setObject(7, diameter_AT);
        s.setObject(8, diameter_AT_tol);
        s.setObject(9, length_L_AT);
        s.setObject(10, length_L_AT_tol);
        s.setObject(11, diameter_IT);
        s.setObject(12, diameter_IT_tol);
        s.setObject(13, length_L_IT);
        s.setObject(14, length_L_IT_tol);
        s.setObject(15, diameter_ZT);
        s.setObject(16, diameter_ZT_tol);
        s.setObject(17, length_L_ZT);
        s.setObject(18, length_L_ZT_tol);
        s.setObject(19, cr_steg);
        s.setObject(20, cr_niere);
        s.setObject(21, ca);
        s.setObject(22, ct);
        s.setObject(23, ck);
        s.setObject(24, rating);
        s.setObject(25, internal_rating);
        s.setObject(26, part_number);
    }

    public void update() throws SQLException {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("UPDATE parts SET customer_id = ?, part_name_id = ?, category_id = ?, drawing_id = ?, rubber = ?, rubber_string = ?, diameter_AT = ?, diameter_AT_tol = ?, length_L_AT = ?, length_L_AT_tol = ?, diameter_IT = ?, diameter_IT_tol = ?, length_L_IT = ?, length_L_IT_tol = ?, diameter_ZT = ?, diameter_ZT_tol = ?, length_L_ZT = ?, length_L_ZT_tol = ?, cr_steg = ?, cr_niere = ?, ca = ?, ct = ?, ck = ?, rating = ?, internal_rating = ? WHERE part_number = ?")) {
            setVariables(s);

            s.executeUpdate();
        }
    }

    public boolean upsert() throws SQLException {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("SELECT part_number, internal_rating from parts WHERE part_number = ?", Statement.RETURN_GENERATED_KEYS)) {
            s.setString(1, part_number);

            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                internal_rating = rs.getInt("internal_rating");
                update();
                return true;
            } else {
                internal_rating = PartCountSession.getPartCount();
                insert();
                return false;
            }
        }
    }

    public void delete() throws SQLException {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("DELETE FROM parts WHERE part_number = ?")) {
            s.setString(1, part_number);

            s.executeUpdate();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Part part = (Part) o;
        return Objects.equals(part_number, part.part_number) && Objects.equals(customer_id, part.customer_id) && Objects.equals(part_name_id, part.part_name_id) && Objects.equals(category_id, part.category_id) && Objects.equals(drawing_id, part.drawing_id) && Objects.equals(rubber, part.rubber) && Objects.equals(diameter_AT, part.diameter_AT) && Objects.equals(diameter_AT_tol, part.diameter_AT_tol) && Objects.equals(length_L_AT, part.length_L_AT) && Objects.equals(length_L_AT_tol, part.length_L_AT_tol) && Objects.equals(diameter_IT, part.diameter_IT) && Objects.equals(diameter_IT_tol, part.diameter_IT_tol) && Objects.equals(length_L_IT, part.length_L_IT) && Objects.equals(length_L_IT_tol, part.length_L_IT_tol) && Objects.equals(diameter_ZT, part.diameter_ZT) && Objects.equals(diameter_ZT_tol, part.diameter_ZT_tol) && Objects.equals(length_L_ZT, part.length_L_ZT) && Objects.equals(length_L_ZT_tol, part.length_L_ZT_tol) && Objects.equals(cr_steg, part.cr_steg) && Objects.equals(cr_niere, part.cr_niere) && Objects.equals(ca, part.ca) && Objects.equals(ct, part.ct) && Objects.equals(ck, part.ck);
    }

    public String getRubberString() {
        if (getRubber() == null) return "";
        return getRubber() + " ShA";
    }

    public String getDiameterATString() {
        if (getDiameter_AT() == null) return "";
        return "Ø " + getDiameter_AT();
    }

    public String getLengthLATString() {
        if (getLength_L_AT() == null) return "";
        return "Ø " + getLength_L_AT();
    }

    public String getDiameterITString() {
        if (getDiameter_IT() == null) return "";
        return "Ø " + getDiameter_IT();
    }

    public String getLengthLITString() {
        if (getLength_L_IT() == null) return "";
        return "Ø " + getLength_L_IT();
    }

    public String getDiameterZTString() {
        if (getDiameter_ZT() == null) return "";
        return "Ø " + getDiameter_ZT();
    }

    public String getLengthLZTString() {
        if (getLength_L_ZT() == null) return "";
        return "Ø " + getLength_L_ZT();
    }

    public String getCrStegString() {
        if (getCr_steg() == null) return "";
        return getCr_steg() + " N/mm";
    }

    public String getCrNiereString() {
        if (getCr_niere() == null) return "";
        return getCr_niere() + " N/mm";
    }

    public String getCaValueString() {
        if (getCa() == null) return "";
        return getCa() + " N/mm";
    }

    public String getCtValueString() {
        if (getCt() == null) return "";
        return getCt() + " Nm/°";
    }

    public String getCkValueString() {
        if (getCk() == null) return "";
        return getCk() + " Nm/°";
    }


}