package com.report.adapter.api.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan({
    "com.report.adapter.api.controller",
    "com.report.adapter.api.error"
})
public class WebConfig implements WebMvcConfigurer {

}
