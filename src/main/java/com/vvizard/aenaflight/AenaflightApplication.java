package com.vvizard.aenaflight;

import com.vvizard.aenaflight.service.AfSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement
public class AenaflightApplication implements CommandLineRunner {

    @Autowired
    private AfSourceService afSourceService;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AenaflightApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.printf("For exit press Ctr+C\n");
        afSourceService.runAfSourceStream();

    }


}
