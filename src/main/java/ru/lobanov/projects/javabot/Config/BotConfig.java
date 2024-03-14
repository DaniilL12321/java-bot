package ru.lobanov.projects.javabot.Config;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.model.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.lobanov.projects.javabot.Model.Jokes;

public class BotConfig {
    private final TelegramBot bot;
    private final String apiUrl = "http://localhost:8080/jokes";

    public BotConfig(String botToken) {
        this.bot = new TelegramBot(botToken);

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
        ResponseEntity<Jokes[]> response = new RestTemplate().getForEntity(apiUrl, Jokes[].class);
        Jokes[] jokes = response.getBody();

        if (jokes != null && jokes.length > 0) {
            // Выбираем случайную шутку из массива всех шуток
            int randomIndex = (int) (Math.random() * jokes.length);
            Jokes randomJoke = jokes[randomIndex];

            String jokeText = String.format("%s",
                    randomJoke.getShutka());

            sendMessage(chatId, jokeText);
        } else {
            sendMessage(chatId, "Шуток не найдено, попробуйте другой запрос");
        }
    }

    public static void main(String[] args) {
        new BotConfig(System.getenv("TOKEN"));
    }
}
