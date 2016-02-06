package com.spencerwhyte.configuration;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.spencerwhyte.dao.AppDAO;
import com.spencerwhyte.dao.AppDAOImpl;

@ComponentScan(basePackages = {"com.spencerwhyte"})
@Configuration
@EnableWebMvc
public class MVCConfiguration extends WebMvcConfigurerAdapter{
	
    @Bean
    public CommonsMultipartResolver multipartResolver(){

        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        return resolver;
    }
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/"); // Handle static resources
		registry.addResourceHandler("/air/apps/**").addResourceLocations("file:/Users/spencerwhyte/temp/apps/"); // Handle static resources
		registry.addResourceHandler("/air/images/**").addResourceLocations("file:/Users/spencerwhyte/temp/images/"); // Handle static resources
    }
	
    @Bean
    public ViewResolver getViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }    
    
    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/appsdb");
        dataSource.setUsername("root");
        dataSource.setPassword("NEWPASSWORD");
         
        return dataSource;
    }
    
    @Bean
    public AppDAO getContactDAO() {
        return new AppDAOImpl(getDataSource());
    }
    

    
}