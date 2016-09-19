package org.iata.imx.autotest.audit;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.iata.concordion.ConcordionFixture;
import org.iata.imx.selenium.web.AuditPage;

public class CreateAudit extends ConcordionFixture<AuditPage> {

	public String enter() {
		
		String pagePosition = page.enterNewAudit();
		return pagePosition.substring(pagePosition.lastIndexOf(">>")+2).trim();
		
	}
	
	
	public String save(String type, String subType, String location, String auditee, String leadAuditor, String scopeType, String scope) {
		Date startDate = new Date();
		Date endDate = DateUtils.addDays(startDate, 1);
		String pagePosition = page.saveAudit(type, subType, location, auditee, startDate, endDate, leadAuditor, scopeType, scope);
		return pagePosition.substring(pagePosition.lastIndexOf(">>")+2).trim();
	}
	
	public String confirm() {
		return page.confirmSaveAudit();
	}
	
}
