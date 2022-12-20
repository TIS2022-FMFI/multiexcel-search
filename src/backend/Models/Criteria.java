package backend.Models;

import java.util.List;

public class Criteria {

    private List<String> partsName;
    private Triple<Short, Short, Integer> rubber = null;
    private Triple<Double, Double, Integer> diameter_AT = null;
    private Triple<Double, Double, Integer> length_L_AT = null;
    private Triple<Double, Double, Integer> diameter_IT = null;
    private Triple<Double, Double, Integer> length_L_IT = null;
    private Triple<Double, Double, Integer> diameter_ZT = null;
    private Triple<Double, Double, Integer> length_L_ZT = null;
    private Triple<Integer, Integer, Integer> cr_steg = null;
    private Triple<Short, Short, Integer> cr_niere = null;
    private Triple<Short, Short, Integer> ca = null;
    private Triple<Double, Double, Integer> ct = null;
    private Triple<Double, Double, Integer> ck = null;

    public List<String> getPartsName() {
        return partsName;
    }

    public void setPartsName(List<String> partsName) {
        this.partsName = partsName;
    }

    public Triple<Short, Short, Integer> getRubber() {
        return rubber;
    }

    public void setRubber(Triple<Short, Short, Integer> rubber) {
        this.rubber = rubber;
    }

    public Triple<Double, Double, Integer> getDiameter_AT() {
        return diameter_AT;
    }

    public void setDiameter_AT(Triple<Double, Double, Integer> diameter_AT) {
        this.diameter_AT = diameter_AT;
    }

    public Triple<Double, Double, Integer> getLength_L_AT() {
        return length_L_AT;
    }

    public void setLength_L_AT(Triple<Double, Double, Integer> length_L_AT) {
        this.length_L_AT = length_L_AT;
    }

    public Triple<Double, Double, Integer> getDiameter_IT() {
        return diameter_IT;
    }

    public void setDiameter_IT(Triple<Double, Double, Integer> diameter_IT) {
        this.diameter_IT = diameter_IT;
    }

    public Triple<Double, Double, Integer> getLength_L_IT() {
        return length_L_IT;
    }

    public void setLength_L_IT(Triple<Double, Double, Integer> length_L_IT) {
        this.length_L_IT = length_L_IT;
    }

    public Triple<Double, Double, Integer> getDiameter_ZT() {
        return diameter_ZT;
    }

    public void setDiameter_ZT(Triple<Double, Double, Integer> diameter_ZT) {
        this.diameter_ZT = diameter_ZT;
    }

    public Triple<Double, Double, Integer> getLength_L_ZT() {
        return length_L_ZT;
    }

    public void setLength_L_ZT(Triple<Double, Double, Integer> length_L_ZT) {
        this.length_L_ZT = length_L_ZT;
    }

    public Triple<Integer, Integer, Integer> getCr_steg() {
        return cr_steg;
    }

    public void setCr_steg(Triple<Integer, Integer, Integer> cr_steg) {
        this.cr_steg = cr_steg;
    }

    public Triple<Short, Short, Integer> getCr_niere() {
        return cr_niere;
    }

    public void setCr_niere(Triple<Short, Short, Integer> cr_niere) {
        this.cr_niere = cr_niere;
    }

    public Triple<Short, Short, Integer> getCa() {
        return ca;
    }

    public void setCa(Triple<Short, Short, Integer> ca) {
        this.ca = ca;
    }

    public Triple<Double, Double, Integer> getCt() {
        return ct;
    }

    public void setCt(Triple<Double, Double, Integer> ct) {
        this.ct = ct;
    }

    public Triple<Double, Double, Integer> getCk() {
        return ck;
    }

    public void setCk(Triple<Double, Double, Integer> ck) {
        this.ck = ck;
    }
}