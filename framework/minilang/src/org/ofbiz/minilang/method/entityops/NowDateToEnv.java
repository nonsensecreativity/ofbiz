/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/
package org.ofbiz.minilang.method.entityops;

import org.ofbiz.base.util.collections.FlexibleMapAccessor;
import org.ofbiz.base.util.string.FlexibleStringExpander;
import org.ofbiz.minilang.MiniLangException;
import org.ofbiz.minilang.MiniLangValidate;
import org.ofbiz.minilang.SimpleMethod;
import org.ofbiz.minilang.method.MethodContext;
import org.ofbiz.minilang.method.MethodOperation;
import org.w3c.dom.Element;

/**
 * Creates a <code>java.sql.Timestamp</code> object that is set to the current system time.
 */
public class NowDateToEnv extends MethodOperation {

    private final FlexibleMapAccessor<Object> fieldFma;

    public NowDateToEnv(Element element, SimpleMethod simpleMethod) throws MiniLangException {
        super(element, simpleMethod);
        if (MiniLangValidate.validationOn()) {
            MiniLangValidate.handleError("Deprecated - use <now>", simpleMethod, element);
            MiniLangValidate.attributeNames(simpleMethod, element, "field");
            MiniLangValidate.requiredAttributes(simpleMethod, element, "field");
            MiniLangValidate.noChildElements(simpleMethod, element);
        }
        this.fieldFma = FlexibleMapAccessor.getInstance(element.getAttribute("field"));
    }

    @Override
    public boolean exec(MethodContext methodContext) throws MiniLangException {
        this.fieldFma.put(methodContext.getEnvMap(), new java.sql.Date(System.currentTimeMillis()));
        return true;
    }

    @Override
    public String expandedString(MethodContext methodContext) {
        return FlexibleStringExpander.expandString(toString(), methodContext.getEnvMap());
    }

    @Override
    public String rawString() {
        return toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("<now-date-to-env ");
        if (!this.fieldFma.isEmpty()) {
            sb.append("field=\"").append(this.fieldFma).append("\" ");
        }
        sb.append("/>");
        return sb.toString();
    }

    public static final class NowDateToEnvFactory implements Factory<NowDateToEnv> {
        public NowDateToEnv createMethodOperation(Element element, SimpleMethod simpleMethod) throws MiniLangException {
            return new NowDateToEnv(element, simpleMethod);
        }

        public String getName() {
            return "now-date-to-env";
        }
    }
}
