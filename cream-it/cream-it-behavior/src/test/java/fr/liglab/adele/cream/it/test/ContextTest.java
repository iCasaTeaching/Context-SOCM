package fr.liglab.adele.cream.it.test;

/*
 * #%L
 * OW2 Chameleon - Fuchsia Core [IntegrationTests]
 * %%
 * Copyright (C) 2009 - 2014 OW2 Chameleon
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import fr.liglab.adele.cream.testing.helpers.ContextBaseTest;
import fr.liglab.adele.cream.annotations.entity.ContextEntity;
import fr.liglab.adele.cream.it.behavior.BehaviorSpec1;
import fr.liglab.adele.cream.it.behavior.ContextEntity1;
import fr.liglab.adele.cream.it.behavior.ContextService1;
import org.apache.felix.ipojo.ConfigurationException;
import org.apache.felix.ipojo.Factory;
import org.apache.felix.ipojo.MissingHandlerException;
import org.apache.felix.ipojo.UnacceptableConfiguration;
import org.junit.Test;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerMethod;
import org.osgi.framework.ServiceReference;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExamReactorStrategy(PerMethod.class)
public class ContextTest extends ContextBaseTest {

    @Override
    protected List<String> getExtraExports() {
        return Arrays.asList(
                "fr.liglab.adele.cream.it.behavior"
        );
    }


    @Override
    public boolean deployTestBundle() {
        return true;
    }


    @Test
    public void testContextEntityFactoIsPresent()  {
        Factory contextFacto = contextHelper.getContextEntityHelper().getContextEntityFactory(ContextEntity1.class.getName());
        assertThat(contextFacto).isNotNull();
    }


    @Test
    public void testServiceExposedByContextEntity() throws MissingHandlerException, UnacceptableConfiguration, ConfigurationException {
        createContextEntity();
        Object serviceObj1 = osgiHelper.getServiceObject(ContextService1.class);
        Object serviceObj2 = osgiHelper.getServiceObject(BehaviorSpec1.class);

        assertThat(serviceObj1).isNotNull();
        assertThat(serviceObj2).isNotNull();
    }

    @Test
    public void testServiceObjectImplementsBehaviorAndContextServices() throws MissingHandlerException, UnacceptableConfiguration, ConfigurationException {
        createContextEntity();

        Object serviceObj1 = osgiHelper.getServiceObject(ContextService1.class);

        assertThat(serviceObj1 instanceof ContextService1).isTrue();
        assertThat(serviceObj1 instanceof BehaviorSpec1).isTrue();

    }


    @Test
    public void testDirectAccessBehavior() throws MissingHandlerException, UnacceptableConfiguration, ConfigurationException {
        createContextEntity();

        BehaviorSpec1 serviceObj1 = (BehaviorSpec1) osgiHelper.getServiceObject(ContextService1.class);

        ServiceReference serviceReference = osgiHelper.getServiceReference(ContextService1.class);

        assertThat(serviceObj1.getterMethodParam1()).isEqualTo(BehaviorSpec1.PARAM_1_INIT_VALUE).overridingErrorMessage("first getter call didn't return the right value");

     //   assertThat(serviceReference.getProperty(ContextEntity.State.ID(BehaviorSpec1.class,BehaviorSpec1.PARAM_1_DIRECTACCESS))).isEqualTo(BehaviorSpec1.PARAM_1_INIT_VALUE).overridingErrorMessage("Service property isn't set to initial value");

        boolean newValue = !BehaviorSpec1.PARAM_1_INIT_VALUE;
        serviceObj1.setterMethodParam1(newValue);
        assertThat(serviceObj1.getterMethodParam1()).isEqualTo(newValue);

  //      assertThat(serviceReference.getProperty(ContextEntity.State.ID(BehaviorSpec1.class,BehaviorSpec1.PARAM_1_DIRECTACCESS))).isEqualTo(BehaviorSpec1.PARAM_1_INIT_VALUE).overridingErrorMessage("Service property isn't set to the modified value");

    //TODO : Default value are not published as service property !!!!
    }

    @Test
    public void testPullBehavior() throws MissingHandlerException, UnacceptableConfiguration, ConfigurationException {
        createContextEntity();

        BehaviorSpec1 serviceObj1 = (BehaviorSpec1) osgiHelper.getServiceObject(ContextService1.class);
        ServiceReference serviceReference = osgiHelper.getServiceReference(ContextService1.class);

        assertThat(serviceObj1.getterMethodParam2()).isEqualTo(BehaviorSpec1.PARAM_2_VALUE);

        assertThat(serviceReference.getProperty(ContextEntity.State.ID(BehaviorSpec1.class,BehaviorSpec1.PARAM_2_PULL))).isEqualTo(BehaviorSpec1.PARAM_2_VALUE).overridingErrorMessage("Service property isn't set to the pulled value");

    }

    private void createContextEntity() throws MissingHandlerException, UnacceptableConfiguration, ConfigurationException {
        contextHelper.getContextEntityHelper().createContextEntity(ContextEntity1.class.getName(),"ContextEntityTest",null);
    }
}
