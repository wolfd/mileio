package io.mile.mileio;

/**
 * Created by DHZ_Bill on 10/20/16.
 */
public class FillOweAmount {
    private String name;
    private String amount;
    public FillOweAmount(String name, String amount){
        this.name = name;
        this.amount = amount;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public void setAmount (String amount){
        this.amount = amount;
    }
    public String getAmount(){
        return amount;
    }
}
