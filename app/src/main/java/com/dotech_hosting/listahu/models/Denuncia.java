package com.dotech_hosting.listahu.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by emilio on 10/24/15.
 */
public class Denuncia extends RealmObject {
    @PrimaryKey
    private int id;

    private String numero;
    private String tipo;
    private String screenshot;
    private String desc;
    private boolean check;
    private String added;
    private int votsi;
    private int votno;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getScreenshot() {
        return screenshot;
    }

    public void setScreenshot(String screenshot) {
        this.screenshot = screenshot;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getAdded() {
        return added;
    }

    public void setAdded(String added) {
        this.added = added;
    }

    public int getVotsi() {
        return votsi;
    }

    public void setVotsi(int votsi) {
        this.votsi = votsi;
    }

    public int getVotno() {
        return votno;
    }

    public void setVotno(int votno) {
        this.votno = votno;
    }
}
