package org.iata.imx.autotest;

import org.iata.concordion.ConcordionFixture;
import org.iata.imx.selenium.web.LoginPage;


public class Logout extends ConcordionFixture<LoginPage> {



    public String logout() {
    	return page.logout();
    }
    
    
}
