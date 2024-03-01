package br.com.vini;

import br.com.vini.geometry.Geometry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Conway implements IConway {

    private int edgeSize, tableSize, cells;
    private double radius;
    private List<Boolean> state;
    private Random rand;

    public Conway(int edgeSize, int cells, double radius) {
        this.edgeSize = edgeSize;
        this.tableSize = (int) Math.pow(this.edgeSize, 2);
        this.cells = cells;
        this.radius = radius;
        this.rand = new Random();
    }

    public List<Boolean> build() {

        this.state = new ArrayList<>(Collections.nCopies(this.tableSize, false));
        var circlePositions = this.findCircleAreaPositions();

        for (int i = 0; i < this.cells && circlePositions.size() != 0; ++i) {
            int idx = (int) (circlePositions.size() * this.rand.nextFloat());
            var position = circlePositions.remove(idx);
            this.state.set(position, true);
        }

        return this.state;
    }

    private List<Integer> findCircleAreaPositions() {
        var positions = new LinkedList<Integer>();
        var middle = new int[2];
        middle[0] = middle[1] = this.edgeSize / 2;

        for (int i = 0; i < this.tableSize; ++i) {
            if (this.isInsideCircle(this.getTableIdx(middle), i)) {
                positions.add(i);
            }
        }

        return positions;
    }

    private boolean isInsideCircle(int middle, int[] position) {
        if (Geometry.hypotenuse(position, this.getPosition(middle)) <= this.radius) {
            return true;
        }
        return false;
    }

    private boolean isInsideCircle(int middle, int needle) {
        var position = this.getPosition(needle);
        return this.isInsideCircle(middle, position);
    }

    private Integer getTableIdx(int[] position) {
        if (position[0] < 0
                || position[0] >= this.edgeSize
                || position[1] < 0
                || position[1] >= this.edgeSize) {
            return null;
        }

        return position[1] * this.edgeSize + position[0];
    }

    private int[] getPosition(int idx) {
        int[] position = new int[2];
        position[0] = idx % this.edgeSize;
        position[1] = (int) idx / this.edgeSize;
        return position;
    }

    public void print() {
        for (int i = 0; i < this.tableSize; ++i) {
            var col = i % this.edgeSize;

            System.out.print("|%d".formatted(this.state.get(i).compareTo(true) + 1));

            if (col == this.edgeSize - 1) {
                System.out.print("|\n");
            }
        }

        System.out.println();
    }
}
