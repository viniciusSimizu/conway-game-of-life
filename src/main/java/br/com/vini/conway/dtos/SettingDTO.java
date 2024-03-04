package br.com.vini.conway.dtos;

import br.com.vini.interfaces.DTO;

public class SettingDTO implements DTO {

    private Integer dimension, cells;
    private Double radius;

    @Override
    public boolean isValid() {
        if (dimension == null) return false;
        if (cells == null) return false;
        if (radius == null) return false;
        return true;
    }

    @Override
    public String getErrors() {
        StringBuilder error = new StringBuilder();

        if (dimension == null) {
            error.append("Dimension invalid\n");
        }
        if (radius == null) {
            error.append("Radius invalid\n");
        }
        if (cells == null) {
            error.append("Cells invalid\n");
        }

        return error.toString();
    }

    public void loadDefaults() {
        dimension = 14;
        cells = 10;
        radius = 4.;
    }

    public Integer getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        if (this.isInteger(dimension)) {
            this.dimension = Integer.parseInt(dimension);
        }
    }

    public Integer getCells() {
        return cells;
    }

    public void setCells(String cells) {
        if (this.isInteger(cells)) {
            this.cells = Integer.parseInt(cells);
        }
    }

    public Double getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        if (this.isDouble(radius)) {
            this.radius = Double.parseDouble(radius);
        }
    }

    private boolean isInteger(String str) {
        return str != null && str.matches("\\d+");
    }

    private boolean isDouble(String str) {
        return str != null && str.matches("(\\d+|\\d*\\.\\d+)");
    }
}
