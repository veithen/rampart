/*
* Copyright 2004,2005 The Apache Software Foundation.
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


package org.apache.axis2.engine;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.description.HandlerDescription;
import org.apache.axis2.description.Parameter;

import javax.xml.namespace.QName;

/**
 * Interface Handler
 */
public interface Handler {

    /**
     * Method cleanup.
     */
    public void cleanup();

    /**
     * Method init.
     *
     * @param handlerdesc
     */
    public void init(HandlerDescription handlerdesc);

    /**
     * Invoke is called to do the actual work of the Handler object.
     * If there is a fault during the processing of this method it is
     * invoke's job to catch the exception and undo any partial work
     * that has been completed.  Once we leave 'invoke' if a fault
     * is thrown, this class's 'onFault' method will be called.
     * Invoke should rethrow any exceptions it catches, wrapped in
     * an AxisFault.
     *
     * @param msgContext the <code>MessageContext</code> to process with this
     *                   <code>Handler</code>.
     * @throws AxisFault if the handler encounters an error
     */
    public void invoke(MessageContext msgContext) throws AxisFault;

    /**
     * Gets the HandlerDescription of a handler. This is used as an input to get phaseRule of a handler.
     *
     * @return Returns HandlerDescription.
     */
    public HandlerDescription getHandlerDesc();

    /**
     * Method getName.
     *
     * @return Returns String
     */
    public String getName();

    /**
     * Method getParameter.
     *
     * @param name
     * @return Returns Parameter.
     */
    public Parameter getParameter(String name);
}