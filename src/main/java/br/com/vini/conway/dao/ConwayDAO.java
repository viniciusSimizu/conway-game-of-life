package br.com.vini.conway.dao;

import br.com.vini.conway.interfaces.IConwayDAO;
import br.com.vini.geometry.Geometry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class ConwayDAO implements IConwayDAO {

    private int dimension, cells;
    private double radius;

    private List<Boolean> state;

    @Override
    public void newPopulation() {

        for (int i = 0; i < this.state.size(); ++i) {
            this.state.set(i, false);
        }

        var circlePositions = this.findCircleAreaPositions();
        var rand = new Random();

        for (int i = 0; i < this.cells && circlePositions.size() != 0; ++i) {
            int idx = (int) (circlePositions.size() * rand.nextFloat());
            var position = circlePositions.remove(idx);
            this.state.set(position, true);
        }
    }

    @Override
    public void nextTick() {
        var newState = new ArrayList<>(Collections.nCopies(this.state.size(), false));

        for (int i = 0; i < this.state.size(); ++i) {
            int neighbors = 0;
            var position = this.getPosition(i);

            for (int offsetX = -1; offsetX < 2; ++offsetX) {
                for (int offsetY = -1; offsetY < 2; ++offsetY) {
                    if (offsetX == 0 && offsetY == 0) {
                        continue;
                    }

                    var neighborPosition = position.clone();
                    neighborPosition[0] += offsetX;
                    neighborPosition[1] += offsetY;

                    var neighborIdx = this.getTableIdx(neighborPosition);
                    if (neighborIdx == null) {
                        continue;
                    }

                    if (state.get(neighborIdx)) {
                        ++neighbors;
                    }
                }
            }

            var isAlive = this.state.get(i);
            if (isAlive && (neighbors == 2 || neighbors == 3)) {
                newState.set(i, true);
            }

            if (!isAlive && neighbors == 3) {
                newState.set(i, true);
            }
        }

        this.state = newState;
    }

    @Override
    public boolean loadPopulation(String stateText) {
        final var p = Pattern.compile("[^1|0]");
        stateText = stateText.trim();

        if (p.matcher(stateText).find()) {
            return false;
        }

        final var sqrt = Math.sqrt(stateText.length());
        if (sqrt % 1 != 0) {
            return false;
        }

        this.dimension = (int) sqrt;
        this.state = new ArrayList<>(Collections.nCopies(stateText.length(), false));

        for (int i = 0; i < stateText.length(); ++i) {
            if (stateText.charAt(i) == '1') {
                this.state.set(i, true);
            }
        }

        return true;
    }

    @Override
    public String getStateText() {
        var stateText = new StringBuilder();

        for (var alive : this.state) {
            if (alive) {
                stateText.append('1');

            } else {
                stateText.append('0');
            }
        }

        return stateText.toString();
    }

    @Override
    public List<List<Boolean>> getState() {

        List<List<Boolean>> table = new ArrayList<>(this.dimension);
        List<Boolean> row = null;

        for (int i = 0; i < this.state.size(); ++i) {
            if (i % this.dimension == 0) {
                row = new ArrayList<>(this.dimension);
                table.add(row);
            }

            row.add(this.state.get(i));
        }

        return table;
    }

    @Override
    public void setDimension(int dimension) {
        this.dimension = dimension;
        int tableSize = (int) Math.pow(dimension, 2);
        this.state = new ArrayList<>(Collections.nCopies(tableSize, false));
    }

    @Override
    public void setCells(int cells) {
        this.cells = cells;
    }

    @Override
    public void setRadius(double radius) {
        this.radius = radius;
    }

    private List<Integer> findCircleAreaPositions() {
        var positions = new LinkedList<Integer>();
        var middle = new int[2];
        middle[0] = middle[1] = this.dimension / 2;

        for (int i = 0; i < this.state.size(); ++i) {
            var needlePosition = this.getPosition(i);

            if (Geometry.hypotenuse(middle, needlePosition) <= this.radius) {
                positions.add(i);
            }
        }

        return positions;
    }

    private Integer getTableIdx(int[] position) {
        if (position[0] < 0
                || position[0] >= this.dimension
                || position[1] < 0
                || position[1] >= this.dimension) {
            return null;
        }

        return dimension * position[1] + position[0];
    }

    private int[] getPosition(int idx) {
        int[] position = new int[2];
        position[0] = idx % this.dimension;
        position[1] = (int) idx / this.dimension;
        return position;
    }

    public void print() {
        for (int i = 0; i < this.state.size(); ++i) {
            var col = i % this.dimension;

            System.out.print("|%d".formatted(this.state.get(i).compareTo(true) + 1));

            if (col == this.dimension - 1) {
                System.out.print("|\n");
            }
        }

        System.out.println();
    }
}
