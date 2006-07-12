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
package org.apache.axis2.jaxws;

import junit.framework.TestSuite;

public class DispatchTestSuite {

    public static TestSuite suite() {
        TestSuite suite = new TestSuite();
        suite = addTestSuites(suite);
        return suite;
    }
	
    public static TestSuite addTestSuites(TestSuite suite) {
        suite.addTestSuite(StringDispatch.class);
        suite.addTestSuite(SourceDispatch.class);
        suite.addTestSuite(DOMSourceDispatch.class);
        suite.addTestSuite(SAXSourceDispatch.class);
        // FIXME: Add this test in
        
        suite.addTestSuite(JAXBDispatch.class);
        
        return suite;
    }

}