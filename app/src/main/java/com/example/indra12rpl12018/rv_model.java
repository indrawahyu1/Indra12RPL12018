package com.example.indra12rpl12018;

import android.os.Parcel;
import android.os.Parcelable;

public class rv_model implements Parcelable {

    protected rv_model(Parcel in) {
        id = in.readString();
        nama = in.readString();
        Nohp = in.readString();
        email = in.readString();
    }

    public static final Creator<rv_model> CREATOR = new Creator<rv_model>() {
        @Override
        public rv_model createFromParcel(Parcel in) {
            return new rv_model(in);
        }

        @Override
        public rv_model[] newArray(int size) {
            return new rv_model[size];
        }
    };

    public rv_model() {

    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String nama;
    private String email;
    private String id;
    private String Noktp;

    public static Creator<rv_model> getCREATOR() {
        return CREATOR;
    }

    public String getNoktp() {
        return Noktp;
    }

    public void setNoktp(String noktp) {
        Noktp = noktp;
    }

    public String getAlamt() {
        return alamat;
    }

    public void setAlamt(String alamt) {
        this.alamat = alamt;
    }

    private String alamat;


    public String getNohp() {
        return Nohp;
    }

    public void setNohp(String nohp) {
        Nohp = nohp;
    }

    private String Nohp;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(nama);
        parcel.writeString(Nohp);
        parcel.writeString(email);
        parcel.writeString(Noktp);
        parcel.writeString(alamat);

    }
}