package br.com.vini.conway.servlets;

import br.com.vini.conway.dao.ConwayDAO;
import br.com.vini.conway.dtos.SettingDTO;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/population")
public class PopulationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        var settingDto = new SettingDTO();
        settingDto.setDimension(req.getParameter("dimension"));
        settingDto.setCells(req.getParameter("cells"));
        settingDto.setRadius(req.getParameter("radius"));

        if (!settingDto.isValid()) {
            resp.sendError(400, settingDto.getErrors());
            return;
        }

        var conway = new ConwayDAO();

        conway.setDimension(settingDto.getDimension());
        conway.setRadius(settingDto.getRadius());
        conway.setCells(settingDto.getCells());
        conway.newPopulation();

        var population = conway.getState();
        var populationText = conway.getStateText();

        req.setAttribute("population", population);
        req.setAttribute("populationState", populationText);

        req.getRequestDispatcher("/WEB-INF/jsp/population.jsp").forward(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String populationState = req.getParameter("populationState");

        if (populationState == null || populationState.isBlank()) {
            resp.sendError(400, "Population not provided");
            return;
        }

        var conway = new ConwayDAO();
        var loaded = conway.loadPopulation(populationState);

        if (!loaded) {
            resp.sendError(400, "Population invalid");
            return;
        }

        conway.nextTick();

        req.setAttribute("population", conway.getState());
        req.setAttribute("populationState", conway.getStateText());

        req.getRequestDispatcher("/WEB-INF/jsp/population.jsp").forward(req, resp);
    }
}
