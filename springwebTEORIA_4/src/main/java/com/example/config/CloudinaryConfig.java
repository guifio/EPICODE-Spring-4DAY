package com.example.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

@Configuration
public class CloudinaryConfig {

	@Bean
	public Cloudinary uploader() {
		Map<String, String> config = new HashMap<>();
		config.put("cloud_name", "dskaugxxk");
		config.put("api_key", "489476569137377");
		config.put("api_secret", "b8TS5w97UYRWLkBHhAnq6hEiZZM");
		return new Cloudinary(config);
	}
	
	
}
