package br.com.vini;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.com.vini.geometry.Geometry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConwayTest {

    private int edgeSize, cells;
    private double radius;

    @BeforeEach
    private void setup() {
        edgeSize = 0;
        edgeSize = 0;
        radius = 0;
    }

    @Test
    public void CircleAreaLimiter() {
        this.edgeSize = 20;
        this.radius = 8.5;
        this.cells = Integer.MAX_VALUE;
        var conway = this.createConway();

        var middle = new int[2];
        middle[0] = middle[1] = this.edgeSize / 2;

        var result = conway.build();

        for (int i = 0; i < result.size(); ++i) {
            var position = this.indexToPosition(i, edgeSize);
            var isCell = result.get(i);

            if (Geometry.hypotenuse(position, middle) > radius) {
                assertFalse(isCell);
                continue;
            }

            assertTrue(isCell);
        }
    }

    private int[] indexToPosition(int idx, int edgeSize) {
        int[] position = new int[2];
        position[0] = idx % edgeSize;
        position[1] = idx / edgeSize;

        return position;
    }

    @Test
    public void CellsQuantityInsideTable() {
        this.edgeSize = 14;
        this.cells = 10;
        this.radius = 4;
        var conway = this.createConway();

        int cellsQuantity = 0;
        for (var isCell : conway.build()) {
            if (isCell) {
                ++cellsQuantity;
            }
        }

        assertEquals(cells, cellsQuantity);
    }

    @Test
    public void TableSize() {
        this.edgeSize = 22;
        this.cells = 0;
        this.radius = 0;
        var conway = this.createConway();

        int size = conway.build().size();

        assertEquals((int) Math.pow(edgeSize, 2), size);
    }

    private IConway createConway() {
        return new Conway(edgeSize, cells, radius);
    }
}
