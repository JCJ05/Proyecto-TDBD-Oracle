package web.proyecto.oracle;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfigurer implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		WebMvcConfigurer.super.addResourceHandlers(registry);
		
		registry.addResourceHandler("/uploads/**").addResourceLocations("file:/C:/Temp/uploads/");
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		
		
		registry.addViewController("/error_403").setViewName("error_403");
		
	}

	
	
	 
	
}
