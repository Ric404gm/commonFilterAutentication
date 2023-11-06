package com.eiyu.common.filter;


public final class ContainerTokenUserComponent { 
    private  String currentUser;


    public ContainerTokenUserComponent(){}


    public ContainerTokenUserComponent(String currentUser){
        this.currentUser = currentUser;
    }

    public String getCurrentUser() {
        return currentUser;
    }
    
    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    


}
