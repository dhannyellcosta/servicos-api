//package clientes.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebConfigTeste implements WebMvcConfigurer {
//
//	@Override
//	public void addCorsMappings(CorsRegistry registry) {
//		
//		registry.addMapping("/**")
//				.allowedOrigins("*")
//				.allowedMethods("*")
//				.allowedHeaders("*")
//				.allowCredentials(true);
//	}
//
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebConfigTeste();
//	}
//}
