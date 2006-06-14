package org.apache.axis2.savan;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import javax.xml.namespace.QName;
import junit.framework.TestCase;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.databinding.types.Duration;
import org.apache.axis2.databinding.utils.ConverterUtil;
import org.apache.savan.SavanMessageContext;
import org.apache.savan.eventing.EventingSubscriptionProcessor;
import org.apache.savan.subscription.ExpirationBean;
import org.apache.savan.util.CommonUtil;

public class EventingExpirationTypesTest extends TestCase {

	public void testDuration () throws Exception {
		SavanMessageContext renewMessage = getRenewMessage("eventing-renew-duration.xml");
		EventingSubscriptionProcessor processor = new EventingSubscriptionProcessor ();
		ExpirationBean expirationBean = processor.getExpirationBean(renewMessage);
		assertTrue(expirationBean.isDuration());
		
		Duration duration = ConverterUtil.convertToduration("P1Y2M3DT10H30M");
		assertEquals(duration,expirationBean.getDurationValue());
		assertEquals (expirationBean.getSubscriberID(),"UUID:DummySubscriberID");
	}
	
	public void testDateTime () throws Exception {
		SavanMessageContext renewMessage = getRenewMessage("eventing-renew-datetime.xml");
		EventingSubscriptionProcessor processor = new EventingSubscriptionProcessor ();
		ExpirationBean expirationBean = processor.getExpirationBean(renewMessage);
		assertFalse(expirationBean.isDuration());
		
		Date date = ConverterUtil.convertTodateTime("2004-06-26T21:07:00.000-08:00").getTime();
		assertEquals(expirationBean.getDateValue(),date);
		assertEquals (expirationBean.getSubscriberID(),"UUID:DummySubscriberID");
	}
	
	private SavanMessageContext getRenewMessage (String name) throws IOException {
        File baseDir = new File("");
        String testRource = baseDir.getAbsolutePath() + File.separator + "test-resources";

		SOAPEnvelope envelope = CommonUtil.getTestEnvelopeFromFile(testRource,name);
		
		MessageContext mc = new MessageContext ();
		SavanMessageContext smc = new SavanMessageContext (mc);
		mc.setEnvelope(envelope);
		
		Options options = new Options ();
		options.setTo(new EndpointReference ("http://DummyToAddress/"));
		
		EndpointReference replyToEPR = new EndpointReference ("http://DummyReplyToAddress/");
		replyToEPR.addReferenceParameter(new QName ("RefParam1"),"RefParamVal1");
		options.setTo(replyToEPR);
		
		options.setAction("urn:uuid:DummyAction");
		
		return smc;
	}
}