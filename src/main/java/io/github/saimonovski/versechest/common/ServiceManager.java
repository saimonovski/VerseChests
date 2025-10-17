package io.github.saimonovski.versechest.common;

import com.google.inject.Injector;
import io.github.saimonovski.versechest.chest.item.service.ChestItemService;
import io.github.saimonovski.versechest.chest.rarity.service.RarityService;
import io.github.saimonovski.versechest.chest.service.ChestService;
import io.github.saimonovski.versechest.chestPlayer.service.PlayerService;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ServiceManager {
    private final Set<Service> services = new HashSet<>();
    private final Injector injector;
    public ServiceManager(Injector injector){
        this.injector = injector;
    }
    protected void lodService(Service service){
        service.load();
        this.services.add(service);
    }
    public void disableAll(){
        services.forEach(Service::unload);
    }
    public void reloadAll(){
        services.forEach(Service::reload);
    }
    @SafeVarargs
    public static ServiceManager getInstance(Injector injector, Class<? extends Service> ... serviceCLasses) {
        ServiceManager manager = new ServiceManager(injector);
        for(Class<? extends Service> serviceClass : serviceCLasses){
            Service service = injector.getInstance(serviceClass);
            manager.lodService(service);
        }
        return manager;
    }
}
