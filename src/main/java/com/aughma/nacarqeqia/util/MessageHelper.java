package com.aughma.nacarqeqia.util;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Utility class to help with message loading and debugging
 */
@Component
public class MessageHelper {

    private final MessageSource messageSource;

    public MessageHelper(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Get a message for the current locale
     * 
     * @param code the message code
     * @param args any arguments
     * @return the message
     */
    public String getMessage(String code, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, args, code, locale);
    }
    
    /**
     * Get a message for a specific locale
     *
     * @param code the message code
     * @param locale the locale
     * @param args any arguments
     * @return the message
     */
    public String getMessage(String code, Locale locale, Object... args) {
        return messageSource.getMessage(code, args, code, locale);
    }
}
