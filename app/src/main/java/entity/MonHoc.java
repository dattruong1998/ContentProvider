package entity;

import java.io.Serializable;

public class MonHoc implements Serializable {
    private int mamonhoc;
    private String tenmonhoc;
    private int sochi;

    public MonHoc() {
    }

    public MonHoc(int mamonhoc, String tenmonhoc, int sochi) {
        this.mamonhoc = mamonhoc;
        this.tenmonhoc = tenmonhoc;
        this.sochi = sochi;
    }

    public int getMamonhoc() {
        return mamonhoc;
    }

    public void setMamonhoc(int mamonhoc) {
        this.mamonhoc = mamonhoc;
    }

    public String getTenmonhoc() {
        return tenmonhoc;
    }

    public void setTenmonhoc(String tenmonhoc) {
        this.tenmonhoc = tenmonhoc;
    }

    public int getSochi() {
        return sochi;
    }

    public void setSochi(int sochi) {
        this.sochi = sochi;
    }

    @Override
    public String toString() {
        return "mamonhoc=" + mamonhoc +
                ", tenmonhoc='" + tenmonhoc + '\'' +
                ", sochi=" + sochi;
    }
}
