package org.example.controller.command;

import java.util.HashMap;

public class CommandManager {
    private static final String SEPARATOR = ":";
    private final HashMap<String, Command> commands = new HashMap<>();

    public CommandManager() {
        commands.put(createCommandKey("get", "cinema"), new GetMovies());

        commands.put(createCommandKey("get", "cinema/buyTicket"), new BuyTicket());
        commands.put(createCommandKey("post", "cinema/buyTicket"), new CreateTicket());

        commands.put(createCommandKey("get", "cinema/returnTicket"), new ReturnTicket());
        commands.put(createCommandKey("post", "cinema/returnTicket"), new DeleteTicket());
    }

    private String createCommandKey(String method, String commandName) {
        return method + SEPARATOR + commandName;
    }

    public Command getCommand(String method, String commandName) {
        return commands.get(createCommandKey(method, commandName));
    }
}
