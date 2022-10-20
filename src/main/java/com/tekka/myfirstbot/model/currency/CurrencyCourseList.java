package com.tekka.myfirstbot.model.currency;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName ="ValCurs")
public class CurrencyCourseList {

    @JacksonXmlProperty(isAttribute = true)
    private String Date;
    @JacksonXmlProperty(isAttribute = true)
    private String name;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "Valute")
    private List<CurrencyItem> valCurse;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CurrencyItem> getValCurse() {
        return valCurse;
    }

    public void setValCurse(List<CurrencyItem> valCurse) {
        this.valCurse = valCurse;
    }
}