package com.example.spring.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.awt.print.Pageable;
import java.util.List;

@Configuration
public class MvcConfigurer implements WebMvcConfigurer {


}
