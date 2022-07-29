package com.visa.workshop17.Model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Currency {
    private Logger logger = LoggerFactory.getLogger(Currency.class);

    private String currency;
    private String toCurrency;
    private String amount;
    private String rate;
    private Date date;
    private List<String> currencyList;
    private String convertedAmount;


    public String getConvertedAmount() {
        return convertedAmount;
    }
    public void setConvertedAmount(String convertedAmount) {
        this.convertedAmount = convertedAmount;
    }
    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }

    public List<String> getCurrencyList() {
        return currencyList;
    }
    public void setCurrencyList(List<String> List) {
        this.currencyList = List;
    }
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public String getToCurrency() {
        return toCurrency;
    }
    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }
    public String getRate() {
        return rate;
    }
    public void setRate(String rate) {
        this.rate = rate;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }


    public Currency getList(String json){
        Currency c = new Currency();
        List<String> fullCurrencyList = new LinkedList<>();

        try(InputStream is = new ByteArrayInputStream(json.getBytes())){
            JsonReader reader = Json.createReader(is);
            JsonObject body = reader.readObject();
            
            Set<String> CurrencySet = body.getJsonObject("symbols").keySet();            
            
            for (String symbol: CurrencySet){
                fullCurrencyList.add(symbol);
            }
        }catch (Exception e)
        {   logger.info(e.getMessage());    }

        c.setCurrencyList(fullCurrencyList);
        //logger.info("log for currency list >>> " + c.getCurrencyList().toString());

        return c;
    }

}
