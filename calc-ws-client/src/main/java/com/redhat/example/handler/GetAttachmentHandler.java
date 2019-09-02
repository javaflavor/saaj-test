package com.redhat.example.handler;

import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.MessageContext.Scope;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class GetAttachmentHandler implements SOAPHandler<SOAPMessageContext> {
	static Logger log = Logger.getLogger(GetAttachmentHandler.class.getName());

	public static final String ATTACHMENT = "ATTACHMENT";

	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		boolean outbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		log.info("### Handler called. outbound=" + outbound);
		if (!outbound) {
			SOAPMessage soapMessage = context.getMessage();
			Iterator<?> it = soapMessage.getAttachments();
			if (it.hasNext()) {
				AttachmentPart attachment = (AttachmentPart)it.next();
				log.info("### attachment="+attachment);
				
				try {
					context.put(ATTACHMENT, attachment.getContent());
				} catch (SOAPException e) {
					throw new RuntimeException("Failed to get attachment content.", e);
				}
				context.setScope(ATTACHMENT, Scope.APPLICATION);
			} else {
				log.info("### No attachment exists.");
			}
		}
		return true;
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		return true;
	}

	@Override
	public void close(MessageContext context) {
	}

	@Override
	public Set<QName> getHeaders() {
		return null;
	}

}
