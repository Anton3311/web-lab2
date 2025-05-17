package org.example.controller;

import org.example.controller.command.Command;
import org.example.controller.command.CommandManager;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FrontController extends HttpServlet {
    private final CommandManager commands = new CommandManager();
    private final Logger logger = Logger.getLogger(FrontController.class);

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info(request.getRequestURI());

        String method = request.getMethod().toLowerCase();
        String commandName = request.getRequestURI().replaceFirst("/Lab2Cinema/", "");

        logger.info(method + " " + commandName);

        Command command = commands.getCommand(method, commandName);

        if (command == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            request.getRequestDispatcher("/WEB-INF/view/error/notFound.jsp").forward(request, response);
        } else {
            String pagePath = command.execute(request, response);
            request.getRequestDispatcher(pagePath).forward(request, response);
        }
    }
}
