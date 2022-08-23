package patterns.structural.proxy;

import patterns.structural.proxy.ext.ExternalDatabaseService;

/*
Pełnomocnik to strukturalny wzorzec projektowy pozwalający stworzyć obiekt zastępczy w miejsce innego obiektu.
Pełnomocnik nadzoruje dostęp do pierwotnego obiektu, pozwalając na wykonanie jakiejś czynności przed lub po przekazaniu do niego żądania.
 */
public class DPS1_Proxy {

    public void start() {

        CacheDatabaseService cachedService = new CacheDatabaseService(new ExternalDatabaseService());

        System.out.println(cachedService.getRecords());
        System.out.println(cachedService.getRecords());
        System.out.println(cachedService.getRecords());
        cachedService.flush();
        System.out.println(cachedService.getRecords());
        System.out.println(cachedService.getRecords());


    }
}
