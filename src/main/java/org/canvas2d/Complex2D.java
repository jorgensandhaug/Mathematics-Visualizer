package org.canvas2d;

import org.utils.Interpolatable;
import javafx.scene.canvas.GraphicsContext;
import org.math.Complex;
import org.math.Matrix;
import org.linalgfx.Writable;

public class Complex2D extends Render2D implements Interpolatable, Writable {
    Complex complex;

    public Complex2D(Complex complex){
        this.complex = complex;
    }
    @Override
    public void render(GraphicsContext gc) {

    }

    @Override
    public Object getMath() {
        return complex;
    }

    @Override
    public void startInterpolation(Matrix m, int millis) {

    }

    @Override
    public void handleInterpolation() {

    }

    @Override
    public String writeString() {
        return "org.canvas2d.Complex2D---"+complex.getRe()+" "+complex.getIm();
    }
}
