package org.iata.imx.autotest.audit;

import org.iata.concordion.ConcordionFixture;
import org.iata.imx.selenium.web.AuditPage;

public class SearchAudit extends ConcordionFixture<AuditPage> {
	
	public String enter() {
		
		String pagePosition = page.enter();
		return pagePosition.substring(pagePosition.lastIndexOf("/")+1).split("\\.")[0];
		
	}
	
	public String searchAudit(String audit,String type,String text) {
		
		return page.searchAudit(audit, type, text).trim();
	}
}
