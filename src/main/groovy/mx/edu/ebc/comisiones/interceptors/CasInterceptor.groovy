package mx.edu.ebc.comisiones.interceptor

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
class CasInterceptor implements HandlerInterceptor {

   @Override
   boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

      System.out.println("Pre Handle method is Calling");
      return true;
   }
}
