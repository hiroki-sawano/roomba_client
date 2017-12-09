package jp.gr.java_conf.hs.roomba.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

@SpringBootApplication
public class RoombaClient {

	public static void main(String[] args) {
		SpringApplication.run(RoombaClient.class, args);
	}
	
	@Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages/Messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
