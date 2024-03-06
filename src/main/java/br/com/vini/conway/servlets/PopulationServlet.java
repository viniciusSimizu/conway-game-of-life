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

@WebServlet("/population")
public class PopulationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        var sess = req.getSession();
        var conway = (IConwayDAO) sess.getAttribute("conway");

        if (conway == null) {
            resp.sendError(403, "Without session");
            return;
        }

        req.setAttribute("population", conway.getState());
        req.setAttribute("populationText", conway.getStateText());
        req.getRequestDispatcher("/WEB-INF/jsp/population.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        var sess = req.getSession();
        var settingDto = new SettingDTO();
        settingDto.setDimension(req.getParameter("dimension"));
        settingDto.setCells(req.getParameter("cells"));
        settingDto.setRadius(req.getParameter("radius"));

        if (!settingDto.isValid()) {
            resp.sendError(400, settingDto.getErrors());
            return;
        }

        var conway = (IConwayDAO) sess.getAttribute("conway");
        if (conway == null) {
            conway = new ConwayDAO();
            sess.setAttribute("conway", conway);
        }

        conway.setDimension(settingDto.getDimension());
        conway.setRadius(settingDto.getRadius());
        conway.setCells(settingDto.getCells());
        conway.newPopulation();

        this.doGet(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        var sess = req.getSession();

        var conway = (IConwayDAO) sess.getAttribute("conway");
        if (conway == null) {
            this.doPost(req, resp);
            return;
        }

        conway.nextTick();
    }
}
