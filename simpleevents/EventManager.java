package com.mlenterman.simpleevents;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class EventManager{
    private static List<EventListener> registeredListeners = new ArrayList();

    public static void register(EventListener listener){
        if(!registeredListeners.contains(listener))
            registeredListeners.add(listener);
    }

    public static void unregister(EventListener listener){
        if(registeredListeners.contains(listener))
            registeredListeners.remove(listener);
    }

    public static List<EventListener> getRegistered(){
        return registeredListeners;
    }

    public static void postEvent(final Event event){
        new Thread(){
            @Override
            public void run(){
                post(event);
            }
        }.start();
    }

    private static void post(final Event event){
        for(EventListener listener : registeredListeners){
            Method[] methods = listener.getClass().getMethods();

            for(int i = 0; i < methods.length; i++){
                EventHandler eventListener = methods[i].getAnnotation(EventHandler.class);
                if(eventListener != null){
                    Class<?>[] methodParams = methods[i].getParameterTypes();

                    if(methodParams.length < 1 || !event.getClass().getSimpleName().equals(methodParams[0].getSimpleName()))
                        continue;
                    
                    try{
                        methods[i].invoke(listener, event);
                    }catch (Exception exception){
                        System.err.println(exception);
                    }
                }
            }
        }
    }
}
