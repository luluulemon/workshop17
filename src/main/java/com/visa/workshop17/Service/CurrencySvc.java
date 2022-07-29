package com.visa.workshop17.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.visa.workshop17.Model.Currency;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class CurrencySvc {
    private Logger logger = LoggerFactory.getLogger(CurrencySvc.class);
    
    String apikey = "8YzFoHzrqa8wgkpQqcA5JctLG22dgy4Z";
    String baseURL = "https://api.apilayer.com/fixer/convert";

    public Optional<Currency> getExchange(Currency currency){
    // eg. https://api.apilayer.com/fixer/convert?to=USD&from=SGD&amount=100

    logger.info("logger inside service >>>>>> " + currency.getCurrency());
        String fromCurrency = currency.getCurrency();
        String toCurrency = currency.getToCurrency();
        String amount = currency.getAmount();

        String url = UriComponentsBuilder .fromUriString(baseURL).queryParam("to", toCurrency)
                    .queryParam("from", fromCurrency)
                    .queryParam("amount", amount).toUriString(); 

        RequestEntity<Void> req = RequestEntity.get(url)
                    .header("apikey",apikey).build();

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req, String.class);

        try (InputStream is = new ByteArrayInputStream(resp.getBody().getBytes())) {
            JsonReader reader = Json.createReader(is);
            JsonObject data = reader.readObject();

            String rate = data.getJsonObject("info").get("rate").toString();
            String result = data.get("result").toString();

            currency.setRate(rate);
            currency.setConvertedAmount(result);

            return Optional.of(currency) ;
            }
        catch(IOException e)
        {   logger.info(e.getMessage());   }
        

        logger.info(">>>>>>>>>>>   return empty currency????");
        
        return Optional.empty();

    }


    public Currency getCurrencyList(){

        Currency currency = new Currency();
        String getListUrl = "https://api.apilayer.com/fixer/symbols";

        RestTemplate template = new RestTemplate();
        RequestEntity<Void> req = RequestEntity.get(getListUrl)
                .header("apikey", apikey).build();
        ResponseEntity<String> resp = template.exchange(req, String.class);

        return currency.getList(resp.getBody());


    }

}
