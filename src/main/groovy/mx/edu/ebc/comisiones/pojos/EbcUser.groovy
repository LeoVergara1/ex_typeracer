package mx.edu.ebc.comisiones.pojos

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.security.core.userdetails.User;

class EbcUser {

  String username
  List<Profile> profiles
  def menus

}