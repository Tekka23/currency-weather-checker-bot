package com.tekka.myfirstbot.service.currency;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tekka.myfirstbot.config.curency.CurrencyConfig;
import com.tekka.myfirstbot.model.currency.CurrencyCourseList;
import com.tekka.myfirstbot.model.currency.CurrencyItem;
import com.tekka.myfirstbot.service.HttpWrapper;
import com.tekka.myfirstbot.service.ResponseBodyParser;
import com.tekka.myfirstbot.service.exceptions.GeneralException;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Objects;
import java.util.Optional;

@Component
@Slf4j
public class CurrencyDataService {
    private final CurrencyConfig currencyConfig;
    private final HttpWrapper httpWrapper;
    private final ResponseBodyParser responseBodyParser;


    @Autowired
    public CurrencyDataService(CurrencyConfig currencyConfig, HttpWrapper httpWrapper, ResponseBodyParser responseBodyParser) {
        this.currencyConfig = currencyConfig;
        this.httpWrapper = httpWrapper;
        this.responseBodyParser = responseBodyParser;
    }

    public String getMessage(){
            Optional<CurrencyCourseList> currencyList = getCurrencyCourseList(ZonedDateTime.now());
            if (currencyList.isEmpty()) return "Unable to get data from server, sorry :(";
        return String.format(EmojiParser.parseToUnicode(
                """
                :sparkles: Money, money comes to mommy :sparkles:
                Today is %s, currency rates for today is:
            
                  :ru: %s  - 1 USD :us:
                  :ru: %s  - 1 EUR :eu:
                  
                  Thank you for using me! :cat:
                """),
                currencyList.get().getDate(),
                getCurrencyValue(currencyList.get(), "USD"),
                getCurrencyValue(currencyList.get(), "EUR"));
    }

    public Optional<CurrencyCourseList> getCurrencyCourseList(ZonedDateTime zonedDateTime){
        try {
            return Optional.of(responseBodyParser.parse(Objects.requireNonNull(httpWrapper).getFromUrl(
                    Objects.requireNonNull(currencyConfig).getCBRUrl() + zonedDateTime.format(
                            new DateTimeFormatterBuilder()
                                    .appendPattern("dd.MM.yyyy")
                                    .toFormatter()), "", ""), CurrencyCourseList.class));
        }catch (JsonProcessingException e){
            log.error("Unable to parse response body to currency item"+ e.getMessage());
        }
        return Optional.empty();
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

}
