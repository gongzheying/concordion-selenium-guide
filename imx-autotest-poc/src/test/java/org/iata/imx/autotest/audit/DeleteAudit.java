package org.iata.imx.autotest.audit;

import org.iata.concordion.ConcordionFixture;
import org.iata.imx.selenium.web.AuditPage;

public class DeleteAudit extends ConcordionFixture<AuditPage> {
	public String enter() {
		
		String pagePosition = page.enter();
		return pagePosition.substring(pagePosition.lastIndexOf("/")+1).split("\\.")[0];
		
	}
	
	public boolean deleteAudit() {
		
		String pagePosition = page.deleteAudit();
		pagePosition = pagePosition.substring(pagePosition.lastIndexOf("/")+1).split("\\.")[0];
		
		return "searchAudits".equals(pagePosition);
		
	}
}
