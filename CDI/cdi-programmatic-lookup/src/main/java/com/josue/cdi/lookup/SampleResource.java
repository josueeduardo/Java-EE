package com.josue.cdi.lookup;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

/**
 * @author Josue Gontijo .
 */
@Path("generic")
@ApplicationScoped
public class SampleResource {

   @Inject
   private BeanManager beanManager;


    @GET
    @Path("by-type")
    public String getMessageByType(){
        Bean<Control> bean = (Bean<Control>) beanManager.getBeans(ControlB.class).iterator().next();
        CreationalContext<Control> ctx = beanManager.createCreationalContext(bean);
        Control control = (Control) beanManager.getReference(bean, ControlB.class, ctx);

        return control.getMessage();
    }

    @GET
    @Path("by-name")
    public String getMessageByName(@QueryParam("bean") @DefaultValue("controlB") String beanName){
        Bean bean = beanManager.getBeans(beanName).iterator().next();
        CreationalContext ctx = beanManager.createCreationalContext(bean);
        Control control = (Control) beanManager.getReference(bean, bean.getBeanClass(), ctx);

        return control.getMessage();
    }
}
