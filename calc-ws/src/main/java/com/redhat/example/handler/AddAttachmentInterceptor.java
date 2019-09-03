package com.redhat.example.handler;

import org.apache.cxf.attachment.AttachmentUtil;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Attachment;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class AddAttachmentInterceptor extends AbstractPhaseInterceptor<Message> {
	static Logger log = Logger.getLogger(AddAttachmentInterceptor.class.getName());

	public AddAttachmentInterceptor() {
        super(Phase.PREPARE_SEND);
    }

    @Override
    public void handleMessage(Message message) throws Fault {
		log.info("### Interceptor called. ");
		
        boolean isOutbound;
        isOutbound = message == message.getExchange().getOutMessage()
                || message == message.getExchange().getOutFaultMessage();

        if (isOutbound) {
            try {
            	// Get context key/value pair.
            	String filename = (String)message.getExchange().get("FILENAME");
            	log.info("### filename = "+filename);
            	
            	// Add attachment to the current SOAP message.
                BufferedInputStream bis =  new BufferedInputStream(this.getClass().getResourceAsStream(filename));
                Attachment attachment = AttachmentUtil.createAttachment(bis, attachmentHeaders());
                message.setAttachments(Arrays.asList(attachment));
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }

    private Map<String, List<String>> attachmentHeaders() {
        Map<String, List<String>> headers = new HashMap<>();
        headers.put("Content-Type", Arrays.asList("plain/text"));
        headers.put("Content-Transfer-Encoding", Arrays.asList("binary"));
        headers.put("Content-ID", Arrays.asList("data.txt"));
        return headers;
    }
}