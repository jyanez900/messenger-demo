package coop.nisc.jacobdemo.web;

import com.google.common.collect.Lists;
import coop.nisc.courier.utils.MessengerGson;
import feign.Contract;
import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class MessengerClientProvider{
    Contract contract;

    @Autowired
    public MessengerClientProvider(Contract contract){
           this.contract = contract;
    }
    @Bean
    public VariableTarget<MessengerClient> createTarget(){
        return new VariableTarget<>(MessengerClient.class);
    }
    @Bean
    public MessengerClient getMessengerClient(VariableTarget<MessengerClient> target){
        return Feign.builder()
                .contract(contract)
                .decoder(new GsonDecoder())
                .encoder(new GsonEncoder())
                .requestInterceptor(new BasicAuthRequestInterceptor("username@nisc.coop", "password"))
                .target(target);
    }
    @Bean
    public GsonHttpMessageConverter converter() {
        return new GsonHttpMessageConverter(MessengerGson.GSON);

    }

    @Bean
    public FilterRegistrationBean setupCors() {
        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowedHeaders(Lists.newArrayList(CorsConfiguration.ALL));
        config.setAllowedOrigins(Lists.newArrayList(CorsConfiguration.ALL));
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        configurationSource.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean<>(new CorsFilter(configurationSource));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
    //https://gotham.arcus.coop/courier/services
}
