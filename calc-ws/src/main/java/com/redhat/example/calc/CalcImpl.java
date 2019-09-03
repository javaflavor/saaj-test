package com.redhat.example.calc;

import javax.annotation.Resource;
import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

import org.apache.cxf.interceptor.OutInterceptors;
import org.apache.cxf.phase.PhaseInterceptorChain;

@WebService(name="Calc", serviceName="CalcService")
//@HandlerChain(file="handlers.xml")
@OutInterceptors(interceptors="com.redhat.example.handler.AddAttachmentInterceptor")
public class CalcImpl {
	@Resource WebServiceContext webServiceContext;
	
	public int add(int i, int j) {
    	// Set context key/value pair, shared with CXF interceptors.
		PhaseInterceptorChain.getCurrentMessage().getExchange().put("FILENAME", "/data.txt");
		
		return i + j;
	}

}
