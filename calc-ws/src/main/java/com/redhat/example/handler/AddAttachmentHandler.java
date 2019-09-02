package com.redhat.example.handler;

import java.util.Set;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.xml.namespace.QName;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class AddAttachmentHandler implements SOAPHandler<SOAPMessageContext> {
	static Logger log = Logger.getLogger(AddAttachmentHandler.class.getName());

	public static final String ATTACHMENT = "ATTACHMENT";

	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		boolean outbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		log.info("### Handler called. " + outbound);

		if (outbound) {
			SOAPMessage soapMessage = context.getMessage();

			AttachmentPart attachment = soapMessage.createAttachmentPart();
			attachment.setMimeHeader("Content-Type", "text/plain");
			attachment.setContentId("ATTACHMENT");
			attachment.setMimeHeader("Content-Transfer-Encoding", "binary");
//        DataHandler dataHandler = new DataHandler(this.getClass().getResource("data.txt"), "text/plain");
			DataHandler dataHandler = new DataHandler("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbb", "text/plain");
			attachment.setDataHandler(dataHandler);
			soapMessage.addAttachmentPart(attachment);
			
			try {
				soapMessage.saveChanges();
			} catch (SOAPException e) {
				throw new RuntimeException("Failed to add Attachement.", e);
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
