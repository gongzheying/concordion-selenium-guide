package org.iata.imx.autotest;

import org.iata.concordion.ConcordionFixture;
import org.iata.imx.selenium.web.AutotestPage;


public class Autotest extends ConcordionFixture<AutotestPage> {

	public void open(String url) {

		page.open(url);
		
	}
	
}
