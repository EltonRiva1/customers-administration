package com.klayrocha.soap.webservices.customersadministration.soap;

import java.util.Collections;
import java.util.List;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.soap.security.wss4j2.callback.SimplePasswordValidationCallbackHandler;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@Configuration
@EnableWs
public class WebServiceConfig extends WsConfigurerAdapter {
	@Bean
	ServletRegistrationBean<MessageDispatcherServlet> servletRegistration(ApplicationContext applicationContext) {
		MessageDispatcherServlet dispatcherServlet = new MessageDispatcherServlet();
		dispatcherServlet.setApplicationContext(applicationContext);
		dispatcherServlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean<MessageDispatcherServlet>(dispatcherServlet, "/ws/*");
	}

	@Bean
	XsdSchema schema() {
		return new SimpleXsdSchema(new ClassPathResource("customer-information.xsd"));
	}

	@Bean(name = "customers")
	DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema schema) {
		DefaultWsdl11Definition defaultWsdl11Definition = new DefaultWsdl11Definition();
		defaultWsdl11Definition.setPortTypeName("CustomerPort");
		defaultWsdl11Definition.setTargetNamespace("http://klayrocha.com.br");
		defaultWsdl11Definition.setLocationUri("/ws");
		defaultWsdl11Definition.setSchema(schema);
		return defaultWsdl11Definition;
	}

	@Bean
	Wss4jSecurityInterceptor wss4jSecurityInterceptor() {
		Wss4jSecurityInterceptor wss4jSecurityInterceptor = new Wss4jSecurityInterceptor();
		wss4jSecurityInterceptor.setSecurementActions("UsernameToken");
		wss4jSecurityInterceptor.setSecurementUsername("klay");
		wss4jSecurityInterceptor.setSecurementPassword("123");
		wss4jSecurityInterceptor.setSecurementPasswordType("PasswordText");
		wss4jSecurityInterceptor.setValidationActions("UsernameToken");
		wss4jSecurityInterceptor.setValidationCallbackHandler(this.callbackHandler());
		return wss4jSecurityInterceptor;
	}

	@Bean
	SimplePasswordValidationCallbackHandler callbackHandler() {
		SimplePasswordValidationCallbackHandler callbackHandler = new SimplePasswordValidationCallbackHandler();
		callbackHandler.setUsersMap(Collections.singletonMap("klay", "123"));
		return callbackHandler;
	}

	@Override
	public void addInterceptors(List<EndpointInterceptor> interceptors) {
		// TODO Auto-generated method stub
		interceptors.add(this.wss4jSecurityInterceptor());
	}
}
