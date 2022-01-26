package com.example.grafoproyecto.redsocial;

public class RedSocialSingleton {
    private static RedSocial redSocial;

    public static void setRedSocial(RedSocial redSocial){
        RedSocialSingleton.redSocial = redSocial;
    }

    public static RedSocial getRedSocial(){
        return RedSocialSingleton.redSocial;
    }
}
