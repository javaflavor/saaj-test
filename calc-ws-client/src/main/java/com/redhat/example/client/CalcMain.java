package com.redhat.example.client;

import java.net.URL;
import java.util.List;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;

import com.redhat.example.calc.Calc;
import com.redhat.example.calc.CalcService;
import com.redhat.example.handler.GetAttachmentHandler;


public class CalcMain {

	public static void main(String[] args) throws Exception {

		URL url = new URL("http://localhost:8080/calc-ws/CalcService?wsdl");
//		URL url = CalcMain.class.getResource("/CalcService.wsdl");
		CalcService service = new CalcService(url);
		Calc calc = service.getCalcPort();
		
		BindingProvider bp = (BindingProvider) calc;
		List<Handler> handlerChain = bp.getBinding().getHandlerChain();
		handlerChain.add(new GetAttachmentHandler());
		bp.getBinding().setHandlerChain(handlerChain);
		
        int i = 20, j = 30;
        int ret = calc.add(i, j);
        
        System.out.printf("### %d + %d = %d%n", i, j, ret);
        
        System.out.println("### Attachment: "+bp.getResponseContext().get(GetAttachmentHandler.ATTACHMENT));
	}
}
