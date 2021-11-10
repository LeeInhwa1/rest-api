package com.example.employee.management.test.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Locale;

@Component
public class MessageUtils {

    private static String reasonMsgReturn = "return.msg.reason.";
    private static String reasonMsgExchange = "exchange.msg.reason.";
    private static String deliveryName = "delivery.name.";

    @Autowired
    private MessageSource messageSource;

    private MessageSourceAccessor accessor;

    @PostConstruct
    private void init() {
        accessor = new MessageSourceAccessor(messageSource, Locale.getDefault());
    }

    /**
     * KEY에 해당하는 메세지 반환
     *
     * @param key
     * @return
     */
    public String getMessage(String key) {
        return accessor.getMessage(key, Locale.getDefault());
    }

    /**
     * KEY에 해당하는 메세지 반환
     *
     * @param key
     * @return
     */
    public String getMessage(String key, Object[] objs) {
        return accessor.getMessage(key, objs, Locale.getDefault());
    }

    /**
     * get code name & values
     *
     * @param key
     * @return
     * @throws Exception
     */
    public String[][] getCodeValues(String key) {
        String[][] codeVals = null;

        String[] vals = getMessage(key + ".val").split(";");
        codeVals = new String[vals.length][2];
        for (int i = 0; i < vals.length; i++) {
            codeVals[i][0] = vals[i];
            codeVals[i][1] = getMessage(key + "." + vals[i]);
        }

        return codeVals;
    }

    public String[] getCodeNames(String key) {
        String[] codeVals = null;

        String[] vals = getMessage(key + ".val").split(";");
        codeVals = new String[vals.length];
        for (int i = 0; i < vals.length; i++) {
            codeVals[i] = getMessage(key + "." + vals[i]);
        }

        return codeVals;
    }

    public String[] getCodeKeys(String key) {
        String[] keys = getMessage(key + ".val").split(";");
        return keys;
    }

    public String getReturnReasonMsg(String code) {
        return accessor.getMessage(reasonMsgReturn + code, Locale.getDefault());
    }

    public String getExchangeReasonMsg(String code) {
        return accessor.getMessage(reasonMsgExchange + code, Locale.getDefault());
    }

    public String getDeliveryName(String code) {
        return accessor.getMessage(deliveryName + code, Locale.getDefault());
    }

}
