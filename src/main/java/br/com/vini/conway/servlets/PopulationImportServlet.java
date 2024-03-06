package br.com.vini.conway.servlets;

import br.com.vini.conway.dao.ConwayDAO;
import br.com.vini.conway.interfaces.IConwayDAO;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/population/import")
public class PopulationImportServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String state = req.getParameter("populationText");
        if (state == null) {
            resp.sendError(400, "Parameter not provided");
            return;
        }

        IConwayDAO conway = new ConwayDAO();
        var isLoaded = conway.loadPopulation(state);

        if (!isLoaded) {
            resp.sendError(400, "Parameter invalid");
            return;
        }

        req.getSession().setAttribute("conway", conway);
    }
}
