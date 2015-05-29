/*
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.yang.gen.v1.urn.opendaylight.capwap.impl.rev150217;

import org.opendaylight.capwap.ODLCapwapACProvider;

public class CapwapImplModule extends org.opendaylight.yang.gen.v1.urn.opendaylight.capwap.impl.rev150217.AbstractCapwapImplModule {
    public CapwapImplModule(org.opendaylight.controller.config.api.ModuleIdentifier identifier, org.opendaylight.controller.config.api.DependencyResolver dependencyResolver) {
        super(identifier, dependencyResolver);
    }

    public CapwapImplModule(org.opendaylight.controller.config.api.ModuleIdentifier identifier, org.opendaylight.controller.config.api.DependencyResolver dependencyResolver, org.opendaylight.yang.gen.v1.urn.opendaylight.capwap.impl.rev150217.CapwapImplModule oldModule, java.lang.AutoCloseable oldInstance) {
        super(identifier, dependencyResolver, oldModule, oldInstance);
    }

    @Override
    public void customValidation() {
        // add custom validation form module attributes here.
    }

    @Override
    public java.lang.AutoCloseable createInstance() {
        //Below code is manually added
        ODLCapwapACProvider provider = new ODLCapwapACProvider(getDataBrokerDependency());
        getBrokerDependency().registerProvider(provider);
        return provider;
    }

}
