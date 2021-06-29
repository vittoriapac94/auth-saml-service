package com.example.authsamlservice.controllers;

import java.util.List;

import com.example.authsamlservice.domains.User;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.opensaml.saml2.core.*;
import org.springframework.security.providers.ExpiringUsernameAuthenticationToken;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class IndexController {
    String email = "";

    @RequestMapping("/logout")
    public ModelAndView logout(ExpiringUsernameAuthenticationToken userToken) {
        //ModelAndView modelAndView = new ModelAndView("redirect:https://dev-56778466.okta.com/app/dev-56778466_springsaml_1/exkzgi53nhiiaLeBr5d6/slo/saml");
        ModelAndView modelAndView = new ModelAndView("redirect:/");
        return modelAndView;
    }

    @RequestMapping("/")
    public ModelAndView index(ExpiringUsernameAuthenticationToken userToken, Model model) {
        System.out.println("Loading home page");
        System.out.println("*******************************************");

        SAMLCredential credential = (SAMLCredential) userToken.getCredentials();
        List<Attribute>  attributes = credential.getAttributes();
        System.out.println("userToken.getName()" + userToken.getName());
        String firstName = "";
        String lastName = "";
        String matricola = "";

        for( Attribute data : attributes ) {
            System.out.println(data.getName() + " == " + credential.getAttributeAsString(data.getName()));
            if(!StringUtils.isBlank(data.getName())) {
                switch(data.getName().trim()) {
                    case "email" :
                        email = credential.getAttributeAsString(data.getName());
                        model.addAttribute(data.getName(), credential.getAttributeAsString(data.getName()));
                        break;
                    case "firstName" :
                        firstName = credential.getAttributeAsString(data.getName());
                        model.addAttribute(data.getName(), credential.getAttributeAsString(data.getName()));
                        break;
                    case "lastName" :
                        lastName = credential.getAttributeAsString(data.getName());
                        model.addAttribute(data.getName(), credential.getAttributeAsString(data.getName()));
                        break;
                    case "employeeNumber" :
                        matricola = credential.getAttributeAsString(data.getName());
                        model.addAttribute(data.getName(), credential.getAttributeAsString(data.getName()));
                        break;
                    default:
                        break;
                }
            }
        }
        User currentUser = new User(email, firstName+" "+lastName, matricola);
        ModelAndView modelAndView = new ModelAndView("redirect:https://localhost:9443/getAll");
        Gson gson = new Gson();
        String userJSON = gson.toJson(currentUser);
        /*try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(userJSON.getBytes("utf8"));
            token = String.format("%040x", new BigInteger(1, digest.digest()));
        } catch (Exception e){
            e.printStackTrace();
        }*/

        String integrityAuth = org.apache.commons.codec.digest.DigestUtils.sha1Hex(userJSON);
        modelAndView.addObject("currentUserJSON", userJSON);
        modelAndView.addObject("integrityAuth", integrityAuth);
        return modelAndView;
    }

}
