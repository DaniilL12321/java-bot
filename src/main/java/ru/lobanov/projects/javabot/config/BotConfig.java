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

import ru.lobanov.projects.javabot.model.Users;
import ru.lobanov.projects.javabot.repository.JokesRepository;
import ru.lobanov.projects.javabot.repository.UsersRepository;
import ru.lobanov.projects.javabot.service.JokesService;
import ru.lobanov.projects.javabot.service.UsersService;

import java.util.Date;
import java.util.List;

public class BotConfig {
    private final TelegramBot bot;
    private final JokesService jokesService;
    private final UsersService usersService;
    private final UsersRepository usersRepository;

    public BotConfig(String botToken, JokesService jokesService, UsersService usersService, UsersRepository usersRepository) {
        this.bot = new TelegramBot(botToken);
        this.jokesService = jokesService;
        this.usersService = usersService;
        this.usersRepository = usersRepository;

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

    void getRandomJoke(long chatId) {
        List<Jokes> jokes = jokesService.allJokes();

        int randomIndex = (int) (Math.random() * jokes.size());
        if (jokes != null && !jokes.isEmpty()) {
            Jokes randomJoke = jokes.get(randomIndex);

            String jokeText = String.format("%s", randomJoke.getShutka());

            sendMessage(chatId, jokeText);
        } else {
            sendMessage(chatId, "Шуток не найдено, попробуйте другой запрос");
        }
        Users user = new Users();
        user.setUserId(chatId);
        user.setTimeWatched(new Date());
        user.setJokesId(randomIndex + 1);
        usersRepository.save(user);
    }

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(JavaBotApplication.class, args);
        JokesService jokesService = context.getBean(JokesService.class);
        UsersService usersService = context.getBean(UsersService.class);
        UsersRepository usersRepository = context.getBean(UsersRepository.class);
        BotConfig botConfig = new BotConfig(System.getenv("TOKEN"), jokesService, usersService, usersRepository);

//        long specificUserId = 1019481069;
//        for (int i = 0; i < 1000; i++) {
//            botConfig.getRandomJoke(specificUserId);
//            System.out.println("Joke sent to user with id " + specificUserId);
//        }
    }
}