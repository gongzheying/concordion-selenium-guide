package org.iata.imx.autotest;

import org.iata.concordion.ConcordionFixture;
import org.iata.imx.selenium.web.LoginPage;


public class Login extends ConcordionFixture<LoginPage> {


    public String getLogininfo(String username, String password) {
    	return page.login(username, password);
    }
	

    
    
}
