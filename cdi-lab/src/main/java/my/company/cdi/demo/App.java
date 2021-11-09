package my.company.cdi.demo;

import java.util.Set;
import java.util.function.Supplier;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;

import my.company.cdi.demo.load.NameLoaderStartup;

public class App {

    public static void main(String[] args) {
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            System.out.println("Starting the NameLoaderStartup class");

            Supplier<Set<String>> loaderStartup = container.select(NameLoaderStartup.class).get();

            System.out.println("The fakes names" + loaderStartup.get());
        }
    }
}
