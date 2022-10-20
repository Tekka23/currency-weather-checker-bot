package com.tekka.myfirstbot.model.currency;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName ="Valute")
public class CurrencyItem {

    @JsonProperty("NumCode")
    String numCode;
    @JsonProperty("CharCode")
    String charCode;
    @JsonProperty("Nominal")
    int nominal;
    @JsonProperty("Name")
    String name;
    @JsonProperty("Value")
    String value;

    public CurrencyItem() {
    }

    public CurrencyItem(String numCode, String charCode, int nominal, String name, String value) {
        this.numCode = numCode;
        this.charCode = charCode;
        this.nominal = nominal;
        this.name = name;
        this.value = value;
    }

    public String getNumCode() {
        return numCode;
    }

    public void setNumCode(String numCode) {
        this.numCode = numCode;
    }

    public String getCharCode() {
        return charCode;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
