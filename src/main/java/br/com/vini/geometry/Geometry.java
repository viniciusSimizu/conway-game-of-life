package br.com.vini.geometry;

public class Geometry {

    public static double hypotenuse(int[] point1, int[] point2) {
        int width = Math.abs(point1[0] - point2[0]);
        int height = Math.abs(point1[1] - point2[1]);
        return Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2));
    }

    public static double circleArea(double radius) {
        return Math.PI * Math.pow(radius, 2);
    }
}
