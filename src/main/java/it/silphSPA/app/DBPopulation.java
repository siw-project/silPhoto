package it.silphSPA.app;

import it.silphSPA.app.repository.*;
import it.silphSPA.app.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class DBPopulation implements ApplicationRunner {

    @Autowired
    private  FunzionarioRepository funzionarioRepository;


    public void run(ApplicationArguments args) throws Exception {
        this.deleteAll();
        this.populateDB();
    }

    private void deleteAll() {
        System.out.println("Deleting all users from DB...");
        funzionarioRepository.deleteAll();
        System.out.println("Done");
    }

    private void populateDB() throws IOException, InterruptedException {

        System.out.println("Storing users...");

        Funzionario admin = new Funzionario(1L, "Mario", "Rossi", "mariorossi", null, "ADMIN");
        String adminPassword = new BCryptPasswordEncoder().encode("mrpass");
        admin.setPassword(adminPassword);
        admin = this.funzionarioRepository.save(admin);

        

        System.out.println("Done.\n");
    }
}
