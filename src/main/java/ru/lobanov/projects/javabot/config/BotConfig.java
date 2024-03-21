package ru.lobanov.projects.javabot.config;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.model.Update;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import ru.lobanov.projects.javabot.JavaBotApplication;
import ru.lobanov.projects.javabot.model.Jokes;

import ru.lobanov.projects.javabot.service.JokesService;

import java.util.List;

public class BotConfig {
    private final TelegramBot bot;
    private final JokesService jokesService;

    public BotConfig(String botToken, JokesService jokesService) {
        this.bot = new TelegramBot(botToken);
        this.jokesService = jokesService;

        bot.setUpdatesListener(updates -> {
            for (Update update : updates) {
                long chatId = update.message().chat().id();
                String command = update.message().text();

                if (command.startsWith("Получить шутку")) {
                    getRandomJoke(chatId);
                } else {
                    sendMessage(chatId, "Неизвестная команда");
                }
            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    private void sendMessage(long chatId, String message) {
        bot.execute(new SendMessage(chatId, message));
        sendMainMenu(chatId);
    }

    private void sendMainMenu(long chatId) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(
                new KeyboardButton[]{new KeyboardButton("Получить шутку")}
        )
                .oneTimeKeyboard(true)
                .resizeKeyboard(true);

        bot.execute(new SendMessage(chatId, "Выберите команду:")
                .replyMarkup(keyboardMarkup));
    }

    private void getRandomJoke(long chatId) {
        List<Jokes> jokes = jokesService.allJokes();

        if (jokes != null && !jokes.isEmpty()) {
            // Выбираем случайную шутку из списка всех шуток
            int randomIndex = (int) (Math.random() * jokes.size());
            Jokes randomJoke = jokes.get(randomIndex);

            String jokeText = String.format("%s", randomJoke.getShutka());

            sendMessage(chatId, jokeText);
        } else {
            sendMessage(chatId, "Шуток не найдено, попробуйте другой запрос");
        }
    }

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(JavaBotApplication.class, args);
        JokesService jokesService = context.getBean(JokesService.class);
        new BotConfig(System.getenv("TOKEN"), jokesService);
    }
}
