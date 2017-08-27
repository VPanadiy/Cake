package com.Aleksandr.Cake;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    private final Logger LOGGER = LoggerFactory.getLogger(WebConfig.class);
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        LOGGER.info("-- Initialization addResourceHandlers!!!");
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");

//        registry.addResourceHandler("/*.js/**").addResourceLocations("/static/");
//        registry.addResourceHandler("/*.css/**").addResourceLocations("/static/");
//        registry.addResourceHandler("/*.images/**").addResourceLocations("/static/");
    }

    @Bean
    public MessageSource messageSource() {
        LOGGER.info("-- Initialization messageSource in WebConfig!!!");
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("i18n/menu");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
    @Bean
    public CookieLocaleResolver localeResolver() {
        LOGGER.info("-- Initialization localeResolver in WebConfig!!!");
        CookieLocaleResolver resolver= new CookieLocaleResolver();
        resolver.setCookieDomain("myAppLocaleCookie");
        // 60 minutes
        resolver.setCookieMaxAge(60*60);
        return resolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LOGGER.info("-- Initialization localeChangeInterceptor in WebConfig!!!");
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LOGGER.info("-- Initialization addInterceptors in WebConfig!!!");
        registry.addInterceptor(localeChangeInterceptor());
    }
//        @Bean
//    public InternalResourceViewResolver getViewResolver() {
//        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//        viewResolver.setPrefix("/");
//        viewResolver.setSuffix(".html");
//            viewResolver.setContentType("text/html;charset=UTF-8");
//        return viewResolver;
//    }
}
