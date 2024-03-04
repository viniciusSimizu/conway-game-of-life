package br.com.vini;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.com.vini.conway.dao.ConwayDAO;
import br.com.vini.conway.interfaces.IConwayDAO;
import br.com.vini.geometry.Geometry;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ConwayTest {

    @Test
    public void CircleAreaLimiter() {
        int dimension = 20;
        int cells = Integer.MAX_VALUE;
        double radius = 8.5;

        IConwayDAO conway = new ConwayDAO();
        conway.setDimension(dimension);
        conway.setCells(cells);
        conway.setRadius(radius);

        var middle = new int[2];
        middle[0] = middle[1] = dimension / 2;

        conway.newPopulation();
        var result = conway.getState();

        for (int y = 0; y < result.size(); ++y) {
            var row = result.get(y);

            for (int x = 0; x < row.size(); ++x) {
                int[] position = new int[2];
                position[0] = x;
                position[1] = y;
                var isCell = row.get(x);

                if (Geometry.hypotenuse(position, middle) > radius) {
                    assertFalse(isCell);
                    continue;
                }

                assertTrue(isCell);
            }
        }
    }

    @Test
    public void CellsQuantityInsideTable() {
        int dimension = 14;
        int cells = 10;
        double radius = 4;

        IConwayDAO conway = new ConwayDAO();
        conway.setDimension(dimension);
        conway.setCells(cells);
        conway.setRadius(radius);

        conway.newPopulation();
        var stateText = conway.getStateText();

        int cellsQuantity = 0;
        for (int i = 0; i < stateText.length(); ++i) {
            if (stateText.charAt(i) == '1') {
                ++cellsQuantity;
            }
        }

        assertEquals(cells, cellsQuantity);
    }

    @Test
    public void TableSize() {
        int dimension = 22;
        int cells = 0;
        double radius = 0;

        IConwayDAO conway = new ConwayDAO();
        conway.setDimension(dimension);
        conway.setCells(cells);
        conway.setRadius(radius);

        int size = conway.getStateText().length();

        assertEquals((int) Math.pow(dimension, 2), size);
    }

    @Test
    public void NextTick() {
        StringBuilder state = new StringBuilder();
        state.append("00000");
        state.append("01010");
        state.append("01001");
        state.append("01010");
        state.append("00000");

        IConwayDAO conway = new ConwayDAO();
        conway.loadPopulation(state.toString());
        conway.nextTick();

        List<List<Boolean>> actual = conway.getState();
        List<List<Boolean>> expected = new ArrayList<>(25);
        expected.add(List.of(false, false, false, false, false));
        expected.add(List.of(false, false, true, false, false));
        expected.add(List.of(true, true, false, true, true));
        expected.add(List.of(false, false, true, false, false));
        expected.add(List.of(false, false, false, false, false));

        assertEquals(expected, actual);
    }
}
