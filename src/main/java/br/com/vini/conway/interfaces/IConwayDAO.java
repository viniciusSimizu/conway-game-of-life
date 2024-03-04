package br.com.vini.conway.interfaces;

import java.util.List;

public interface IConwayDAO {

    void newPopulation();

    void nextTick();

    boolean loadPopulation(String stateText);

    List<List<Boolean>> getState();

    String getStateText();

    void setDimension(int dimension);

    void setCells(int cells);

    void setRadius(double radius);
}
