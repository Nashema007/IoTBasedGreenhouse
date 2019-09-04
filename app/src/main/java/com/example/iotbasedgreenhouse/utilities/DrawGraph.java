package com.example.iotbasedgreenhouse.utilities;

import android.view.animation.DecelerateInterpolator;

import com.scichart.charting.model.dataSeries.IXyDataSeries;
import com.scichart.charting.visuals.SciChartSurface;
import com.scichart.charting.visuals.axes.IAxis;
import com.scichart.charting.visuals.renderableSeries.FastColumnRenderableSeries;
import com.scichart.core.framework.UpdateSuspender;
import com.scichart.drawing.utility.ColorUtil;
import com.scichart.extensions.builders.SciChartBuilder;

import java.util.ArrayList;
import java.util.Collections;

public class DrawGraph {
    public static void columnGraph(final SciChartSurface surface, SciChartBuilder sciChartBuilder, String xAxisTitle, String yAxisTitle, ArrayList<Double> yValues){

        final IAxis xAxisColumn = sciChartBuilder
                .newNumericAxis()
                .withGrowBy(0.1, 0.1)
                .withAxisTitle(xAxisTitle)
                .build();
        final IAxis yAxisColumn = sciChartBuilder
                .newNumericAxis()
                .withGrowBy(0, 0.3)
                .withAxisTitle(yAxisTitle)
                .build();


        IXyDataSeries<Integer,Double> dataSeries = sciChartBuilder.newXyDataSeries(Integer.class, Double.class).build();
        for (int i = 0; i < yValues.size(); i++) {
            dataSeries.append(i, yValues.get(i));
        }

        final FastColumnRenderableSeries rSeries = sciChartBuilder.newColumnSeries()
                .withStrokeStyle(0xFF232323, 0.4f)
                .withDataPointWidth(0.5)
                .withLinearGradientColors(ColorUtil.LightSteelBlue, ColorUtil.SteelBlue)
                .withDataSeries(dataSeries)
                .withPaletteProvider(new ColumnsPaletteProvider())
                .build();
        UpdateSuspender.using(surface, () -> {
            Collections.addAll(surface.getXAxes(), xAxisColumn);
            Collections.addAll(surface.getYAxes(), yAxisColumn);
            Collections.addAll(surface.getRenderableSeries(), rSeries);
            Collections.addAll(surface.getChartModifiers(), sciChartBuilder.newModifierGroupWithDefaultModifiers().build());

            sciChartBuilder.newAnimator(rSeries).withWaveTransformation().withInterpolator(new DecelerateInterpolator()).withDuration(3000).withStartDelay(350).start();
        });




    }

}
