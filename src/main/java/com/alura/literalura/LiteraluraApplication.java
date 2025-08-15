package com.alura.literalura;

import com.alura.literalura.cli.Menu;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class LiteraluraApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(LiteraluraApplication.class, args);
        Menu menu = context.getBean(Menu.class);
        menu.exibirMenu();
    }
}
