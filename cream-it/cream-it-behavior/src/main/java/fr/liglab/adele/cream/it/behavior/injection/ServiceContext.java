package fr.liglab.adele.cream.it.behavior.injection;

import fr.liglab.adele.cream.annotations.ContextService;

/**
 * Created by aygalinc on 24/08/16.
 */
public  @ContextService interface ServiceContext {

    boolean returnTrueFromTheInjectedBehavior();

}