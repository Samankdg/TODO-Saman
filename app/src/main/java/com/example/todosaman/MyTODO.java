package com.example.todosaman;

public class MyTODO {

    String titleTODO, dateTODO, descTODO, keyTODO;

    public MyTODO() {
    }

    public MyTODO(String titleTODO, String dateTODO, String descTODO, String keyTODO) {
        this.titleTODO = titleTODO;
        this.dateTODO = dateTODO;
        this.descTODO = descTODO;
        this.keyTODO = keyTODO;
    }

    public String getKeyTODO() {
        return keyTODO;
    }

    public void setKeyTODO(String keyTODO) {
        this.keyTODO = keyTODO;
    }

    public String getTitleTODO() {

        return titleTODO;
    }

    public void getTitleTODO(String titleTODO) {

        this.titleTODO = titleTODO;
    }

    public String getDateTODO() {
        return dateTODO;
    }

    public void setDateTODO(String dateTODO) {

        this.dateTODO = dateTODO;
    }

    public String getDescTODO() {

        return descTODO;
    }

    public void setDescTODO(String descTODO) {
        this.descTODO = descTODO;
    }
}
