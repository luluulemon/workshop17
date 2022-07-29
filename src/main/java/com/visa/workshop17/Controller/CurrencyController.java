package com.visa.workshop17.Controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.visa.workshop17.Model.Currency;
import com.visa.workshop17.Service.CurrencySvc;

@Controller
public class CurrencyController {
    private Logger logger = LoggerFactory.getLogger(Currency.class);

    @Autowired
    CurrencySvc service;

    @RequestMapping
    public String appPage(Model model){
        Currency currency = service.getCurrencyList();
        //logger.info("controller log >>>" + currency.getCurrencyList().toString());
        model.addAttribute("Currency", currency);
        return "index";
    }


    @RequestMapping("/conversion")
    public String Convert(@ModelAttribute Currency currency, Model model){

        logger.info("I was here COntroller >>>>>>>>" + currency.getAmount());

        Optional<Currency> conversion = service.getExchange(currency);
        logger.info("controller log >>> " + conversion.get().getConvertedAmount());
        model.addAttribute("Currency", conversion.get());
        return "dummy";
    }

}
