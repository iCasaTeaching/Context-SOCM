package fr.liglab.adele.cream.internal.factories;

import org.apache.felix.ipojo.*;
import org.apache.felix.ipojo.metadata.Element;
import org.osgi.framework.BundleContext;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

/**
 * Created by aygalinc on 31/05/16.
 */
public class BehaviorManager extends InstanceManager {

    /**
     * Creates a new Component Manager.
     * The instance is not initialized.
     *
     * @param factory  the factory managing the instance manager
     * @param context  the bundle context to give to the instance
     * @param handlers handler object array
     */
    public BehaviorManager(ComponentFactory factory, BundleContext context, HandlerManager[] handlers) {
        super(factory, context, handlers);
    }
}
