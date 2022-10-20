package com.tekka.myfirstbot.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.tekka.myfirstbot.config.curency.CurrencyConfig;
import com.tekka.myfirstbot.model.currency.CurrencyCourseList;
import com.tekka.myfirstbot.model.currency.CurrencyItem;
import com.tekka.myfirstbot.service.exceptions.GeneralException;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Objects;
import java.util.Optional;

@Component
public class CurrencyDataService {
    private final CurrencyConfig currencyConfig;
    private final HttpWrapper httpWrapper;

    @Autowired
    public CurrencyDataService(CurrencyConfig currencyConfig) {
        this.currencyConfig = currencyConfig;
        XmlMapper mapper = new XmlMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.httpWrapper = new HttpWrapper(mapper);
    }

    public CurrencyCourseList getCurrencyCourseList(ZonedDateTime zonedDateTime){
        return Objects.requireNonNull(httpWrapper).getFromUrl(
                Objects.requireNonNull(currencyConfig).getCBRUrl()
                        + zonedDateTime.format(new DateTimeFormatterBuilder().appendPattern("dd.MM.yyyy").toFormatter()),
                CurrencyCourseList.class, "", "");
    }


    public String getCurrencyValue(CurrencyCourseList list, String ticker){
       Optional<CurrencyItem> opt = list.getValCurse().parallelStream()
               .filter(currencyItem -> currencyItem.getCharCode().equals(ticker))
               .findFirst();
       if (opt.isPresent()){
           return opt.get().getValue();
       }
       else throw new GeneralException("Can not find this ticker" + ticker);
    }
    public String getMessage(){
        CurrencyCourseList currencyList = getCurrencyCourseList(ZonedDateTime.now());
        return String.format(EmojiParser.parseToUnicode(
                """
                :sparkles: Manny, manny comes to mommy :sparkles:
                Today is %s, currency rates for today is:
            
                  :ru: %s  - 1 USD :us:
                  :ru: %s  - 1 EUR :eu:
                  
                  Thank you for using me! :cat:
                """),
                currencyList.getDate(),
                getCurrencyValue(currencyList, "USD"),
                getCurrencyValue(currencyList, "EUR"));
    }

}
