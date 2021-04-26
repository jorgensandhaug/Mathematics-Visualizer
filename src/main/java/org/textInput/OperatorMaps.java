package org.textInput;

import org.math.*;
import org.utils.TriFunctions;

import java.util.ArrayList;
import java.util.List;

public abstract class OperatorMaps {
    public static final List<InputMapTriFunc> triFuncMaps = new ArrayList<>();
    public static final List<InputMapBiFunc> biFuncMaps = new ArrayList<>();
    public static final List<InputMapFunc> funcMaps = new ArrayList<>();
    private static final InputMapTriFunc<Expression, Double, Double, Double> edddOps = new InputMapTriFunc<>(new Expression("0"),0d,0d, 0d);
    private static final InputMapBiFunc<Vector, Vector, Vector> vvvOps = new InputMapBiFunc<>(new Vector(),new Vector(),new Vector());
    private static final InputMapBiFunc<Vector, Vector, Double> vvdOps = new InputMapBiFunc<>(new Vector(),new Vector(),0d);
    private static final InputMapBiFunc<Vector, Double, Vector> vdvOps = new InputMapBiFunc<>(new Vector(),0d,new Vector());
    private static final InputMapBiFunc<Vector, Matrix, Vector> vmvOps = new InputMapBiFunc<>(new Vector(),new Matrix(),new Vector());
    private static final InputMapBiFunc<Matrix, Matrix, Matrix> mmmOps = new InputMapBiFunc<>(new Matrix(),new Matrix(),new Matrix());
    private static final InputMapBiFunc<Point, Matrix, Point> pmpOps = new InputMapBiFunc<>(new Point(),new Matrix(),new Point());
    private static final InputMapBiFunc<Point, Point, Point> pppOps = new InputMapBiFunc<>(new Point(),new Point(),new Point());
    private static final InputMapBiFunc<Complex, Complex, Complex> cccOps = new InputMapBiFunc<>(new Complex(),new Complex(), new Complex());
    private static final InputMapBiFunc<Complex, Double, Complex> cdcOps = new InputMapBiFunc<>(new Complex(),0d, new Complex());
    private static final InputMapBiFunc<Matrix, Vector, Vector> mvvOps = new InputMapBiFunc<>(new Matrix(), new Vector(), new Vector());
    private static final InputMapBiFunc<Expression, Double, Double> eddOps = new InputMapBiFunc<>(new Expression("0"),0d, 0d);
    private static final InputMapFunc<Vector, Double> vdOps = new InputMapFunc<>(new Vector(), 0d);
    private static final InputMapFunc<Matrix, Matrix> mmOps = new InputMapFunc<>(new Matrix(), new Matrix());
    private static final InputMapFunc<Matrix, Double> mdOps = new InputMapFunc<>(new Matrix(), 0d);
    private static final InputMapFunc<Expression, Expression> eeOps = new InputMapFunc<>(new Expression("0"), new Expression("0"));

    /**
     * Fills Maps between textual representations of functions and mathematical functions in the program
     */
    public static void fillOpMaps(){
        edddOps.put("sum", TriFunctions.seriesEval);
        triFuncMaps.add(edddOps);

        vvvOps.put("add", Vectors::sum);vvvOps.put("subtract", Vectors::difference);vvvOps.put("cross", Vectors::cross);
        biFuncMaps.add(vvvOps);

        vvdOps.put("dot", Vectors::dot); vvdOps.put("angle", Vectors::angle);
        biFuncMaps.add(vvdOps);

        vdvOps.put("scale", Vectors::scale);
        biFuncMaps.add(vdvOps);

        vmvOps.put("transform", Vectors::transform);
        biFuncMaps.add(vmvOps);

        mmmOps.put("product", Matrices::product);
        biFuncMaps.add(mmmOps);

        mvvOps.put("solve", Solver::solveLinSys);
        biFuncMaps.add(mvvOps);

        pmpOps.put("transform", Points::transform);
        biFuncMaps.add(pmpOps);

        pppOps.put("add", Points::add);pppOps.put("subtract", Points::subtract);
        biFuncMaps.add(pppOps);

        cccOps.put("add", ComplexNumbers::add);cccOps.put("multiply", ComplexNumbers::multiply);
        biFuncMaps.add(cccOps);

        cdcOps.put("pow", ComplexNumbers::pow);
        biFuncMaps.add(cdcOps);

        vdOps.put("abs", Vectors::getMagnitude);
        funcMaps.add(vdOps);

        mmOps.put("inverse", Solver::invertedMatrix);
        funcMaps.add(mmOps);

        mdOps.put("det", Matrices::det);
        funcMaps.add(mdOps);

    }
}
