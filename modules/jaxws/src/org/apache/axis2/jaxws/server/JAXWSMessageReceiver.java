/*
 * Copyright 2004,2005 The Apache Software Foundation.
 * Copyright 2006 International Business Machines Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.axis2.jaxws.server;

import javax.xml.ws.Service.Mode;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.description.AxisService;
import org.apache.axis2.engine.AxisEngine;
import org.apache.axis2.engine.MessageReceiver;
import org.apache.axis2.jaxws.param.Parameter;
import org.apache.axis2.jaxws.param.ParameterFactory;
import org.apache.axis2.util.Utils;


/**
 * The JAXWSMessageReceiver is the entry point, from the server's perspective,
 * to the JAX-WS code.  This will be called by the Axis Engine and is the end
 * of the chain from an Axis2 perspective.
 */
public class JAXWSMessageReceiver implements MessageReceiver {

	private static String PARAM_SERVICE_CLASS = "ServiceClass";
    /**
     * We should have already determined which AxisService we're targetting at
     * this point.  So now, just get the service implementation and invoke
     * the appropriate method.
     */
    public void receive(MessageContext reqMsgContext) throws AxisFault {
    	
    	if(reqMsgContext == null){
    		throw new  AxisFault("Message Context is null");	
    	}
    	
        // Let's go ahead and create the MessageContext for the response and add
        // it to the list.
        MessageContext rspMsgContext = Utils.createOutMessageContext(reqMsgContext);
        reqMsgContext.getOperationContext().addMessageContext(rspMsgContext);
        
        // Get the name of the service impl that was stored as a parameter
        // inside of the services.xml.
        AxisService service = reqMsgContext.getAxisService();
        if(service == null){
    		throw new AxisFault("Axis service is null");	
    	}
        
        org.apache.axis2.description.Parameter svcClassParam = service.getParameter(PARAM_SERVICE_CLASS);
        if(svcClassParam == null){
        	throw new RuntimeException("No service class was found for this AxisService");	
    	}
                
        try {
        	
        	// Get the appropriate endpoint dispatcher for this service
        	EndpointDispatcher endpointDispatcher = new EndpointController(reqMsgContext).getDispatcher();
        	Object response = endpointDispatcher.execute();
            
            Parameter rspParam = ParameterFactory.createParameter(response);
            SOAPEnvelope rspEnvelope = rspParam.toEnvelope(Mode.PAYLOAD, null);
            rspMsgContext.setEnvelope(rspEnvelope);
            
            // Create the AxisEngine for the response and send it.
            AxisEngine engine = new AxisEngine(rspMsgContext.getConfigurationContext());
            engine.send(rspMsgContext);
        } catch (Exception e) {
        	//TODO: This temp code for alpha till we add fault processing on client code.
        	Exception ex = new Exception("Server Side Exception :" +e.getMessage());
            throw AxisFault.makeFault(ex);
        } 
    }
}