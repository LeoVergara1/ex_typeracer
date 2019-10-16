package mx.edu.ebc.comisiones

import mx.edu.ebc.comisiones.interceptor.CasInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@Component
class AppConfig extends WebMvcConfigurerAdapter {
   @Autowired
   CasInterceptor casInterceptor

   @Override
   void addInterceptors(InterceptorRegistry registry) {
      registry.addInterceptor(casInterceptor).addPathPatterns("/administration/*").excludePathPatterns("/administration/search/association")
      .addPathPatterns("/")
      .addPathPatterns("/authorization/")
      .addPathPatterns("/authorization/query")
      .addPathPatterns("/authorization/marketing")
      .addPathPatterns("/sicoss/")
      .addPathPatterns("/sicoss/process")
      .addPathPatterns("/sicoss/queryProcess");
   }
}