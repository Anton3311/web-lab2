package org.example.controller;

import org.example.controller.command.Command;
import org.example.controller.command.CommandManager;

import org.apache.log4j.Logger;
import org.example.model.dao.DaoFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FrontController extends HttpServlet {
    private final CommandManager commands = new CommandManager();
    private final Logger logger = Logger.getLogger(FrontController.class);

    public static final String REDIRECT = "redirect";

    @Override
    public void init() throws ServletException {
        super.init();

        DaoFactory.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info(request.getRequestURI());

        String method = request.getMethod().toLowerCase();
        String commandName = request.getRequestURI().replaceAll(RegExp.NUMBER, "").replaceFirst("/Lab2Cinema/", "");
        if (commandName.endsWith("/")) {
            commandName = commandName.substring(0, commandName.length() - 1);
        }

        logger.info(method + " " + commandName);

        Command command = commands.getCommand(method, commandName);

        if (command == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            request.getRequestDispatcher("/WEB-INF/view/error/notFound.jsp").forward(request, response);
        } else {
            String pagePath = command.execute(request, response);

            if (!pagePath.equals(REDIRECT)) {
                request.getRequestDispatcher(pagePath).forward(request, response);
            }
        }
    }
}
