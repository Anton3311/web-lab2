package org.example.controller.command;

import java.util.HashMap;

public class CommandManager {
    private static final String SEPARATOR = ":";
    private final HashMap<String, Command> commands = new HashMap<>();

    public CommandManager() {
        commands.put(createCommandKey("get", "cinema/createMovie"), new GetMovie());
        commands.put(createCommandKey("post", "cinema/createMovie"), new CreateMovie());

        commands.put(createCommandKey("get", "cinema/movie"), new GetMovie());

        commands.put(createCommandKey("get", "cinema/movies"), new GetMovies());

        commands.put(createCommandKey("get", "cinema/buyTicket"), new BuyTicket());
        commands.put(createCommandKey("post", "cinema/buyTicket"), new CreateTicket());
    }

    private String createCommandKey(String method, String commandName) {
        return method + SEPARATOR + commandName;
    }

    public Command getCommand(String method, String commandName) {
        return commands.get(createCommandKey(method, commandName));
    }
}
