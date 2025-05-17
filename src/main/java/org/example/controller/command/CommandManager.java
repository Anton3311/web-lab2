package org.example.controller.command;

import java.util.HashMap;

public class CommandManager {
    private static final String SEPARATOR = ":";
    private final HashMap<String, Command> commands = new HashMap<>();

    public CommandManager() {
        commands.put(createCommandKey("get", "cinema/createMovie"), new CreateMovie());
        commands.put(createCommandKey("post", "cinema/createMovie"), new CreateMovie());
    }

    private String createCommandKey(String method, String commandName) {
        return method + SEPARATOR + commandName;
    }

    public Command getCommand(String method, String commandName) {
        return commands.get(createCommandKey(method, commandName));
    }
}
