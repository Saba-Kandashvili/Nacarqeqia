package com.aughma.nacarqeqia.util;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageHelper {

    private final MessageSource messageSource;

    public MessageHelper(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String code, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, args, code, locale);
    }

    public String getMessage(String code, Locale locale, Object... args) {
        return messageSource.getMessage(code, args, code, locale);
    }
}
