package model;

public class Account {

    private long identificationNumber;
    private long ownerId;
    private String type;
    private float sumOfMoney;
    private long dateCreation;


    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public long getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(long identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getSumOfMoney() {
        return sumOfMoney;
    }

    public void setSumOfMoney(float sumOfMoney) {
        this.sumOfMoney = sumOfMoney;
    }

    public long getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(long dateCreation) {
        this.dateCreation = dateCreation;
    }
}
