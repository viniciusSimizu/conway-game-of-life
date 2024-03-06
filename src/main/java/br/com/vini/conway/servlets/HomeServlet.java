package br.com.vini.conway.servlets;

import br.com.vini.conway.dao.ConwayDAO;
import br.com.vini.conway.dtos.SettingDTO;
import br.com.vini.conway.interfaces.IConwayDAO;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("")
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        IConwayDAO conway = null;
        var settingDto = new SettingDTO();
        String populationState = req.getParameter("populationState");

        settingDto.loadDefaults();
        settingDto.setDimension(req.getParameter("dimension"));
        settingDto.setRadius(req.getParameter("radius"));
        settingDto.setCells(req.getParameter("cells"));

        req.setAttribute("setting", settingDto);

        if (populationState != null && !populationState.isEmpty()) {
            conway = new ConwayDAO();
            var isLoaded = conway.loadPopulation(populationState);

            if (isLoaded) {
                conway.nextTick();
            }
        }

        if (conway != null && conway.getState() != null) {
            var session = req.getSession();
            session.setAttribute("state", conway.getState());
        }

        req.getRequestDispatcher("/WEB-INF/jsp/home.jsp").forward(req, resp);
    }
}
