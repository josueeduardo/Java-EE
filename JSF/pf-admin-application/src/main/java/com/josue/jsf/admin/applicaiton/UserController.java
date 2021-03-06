/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.josue.jsf.admin.applicaiton;

import com.josue.jsf.admin.applicaiton.entity.AppUser;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 *
 * @author Josue
 */
@Named
@ViewScoped
public class UserController implements Serializable {

    private static final Logger logger = Logger.getLogger(UserController.class.getName());

    private AppUser user = new AppUser();
    private List<AppUser> users = new ArrayList<>();

    //Ajax navigation
    private static final String LIST = "list";
    private static final String CREATE = "create";
    private static final String UPDATE = "update";

    private static final String REFRESH = "user.xhtml?faces-redirect=true";

    private String step = LIST;

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    public void init() {
        logger.info("********* Initializing USER CONTROLLER *************");
        String stepParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("step");
        if (stepParam != null) {
            step = stepParam;
        }
        fetchData();
    }

    @Transactional
    public void createUser() {
        em.persist(user);
        Messages.infoMessage("User created !");
        user = new AppUser();
        step = LIST;
        fetchData();
    }

    @Transactional
    public void deleteUser(AppUser user) {
        AppUser foundUser = em.find(AppUser.class, user.getId());
        if (foundUser != null) {
            em.remove(foundUser);
        }
        Messages.infoMessage("User deleted !");
        fetchData();
    }

    @Transactional
    public void updateUser() {
        em.merge(user);
        Messages.infoMessage("User updated !");
        user = new AppUser();
        step = LIST;
        fetchData();
    }

    public void fetchData() {
        logger.info("************** FETCHING DATA *************");
        users = em.createQuery("SELECT u FROM AppUser u", AppUser.class).getResultList();
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public List<AppUser> getUsers() {
        return users;
    }

    public void setUsers(List<AppUser> users) {
        this.users = users;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String refresh() {
        return REFRESH;
    }

}
