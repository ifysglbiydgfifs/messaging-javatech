package com.example.messagingrabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Runner implements CommandLineRunner {

	private final RabbitTemplate rabbitTemplate;

	public Runner(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	@Override
	public void run(String... args) {
		System.out.println("Type smth...");

		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.print("> ");
			String input = scanner.nextLine();
			if (input == null || input.trim().isEmpty()) {
				System.out.println("Shutdown...");
				break;
			}

			rabbitTemplate.convertAndSend(MessagingRabbitmqApplication.fanoutExchangeName, "", input);
		}
	}
}
