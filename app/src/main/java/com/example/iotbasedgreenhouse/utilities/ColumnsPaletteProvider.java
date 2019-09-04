package com.example.iotbasedgreenhouse.utilities;

import com.scichart.charting.visuals.renderableSeries.FastColumnRenderableSeries;
import com.scichart.charting.visuals.renderableSeries.data.XSeriesRenderPassData;
import com.scichart.charting.visuals.renderableSeries.paletteProviders.IFillPaletteProvider;
import com.scichart.charting.visuals.renderableSeries.paletteProviders.PaletteProviderBase;
import com.scichart.core.model.IntegerValues;

public class ColumnsPaletteProvider extends PaletteProviderBase<FastColumnRenderableSeries> implements IFillPaletteProvider {
    /*
        Gradients as in iOS, we don't support gradient palette provider yet
        #1 start: 0xFFa9d34f; finish: 0xFF93b944; PEN 0xFF232323
        #2 start: 0xFFfc9930; finish: 0xFFd17f28; PEN 0xFF232323
        #3 start: 0xFFd63b3f; finish: 0xFFbc3337; PEN 0xFF232323
     */
    private final IntegerValues colors = new IntegerValues();
    private final int[] desiredColors = new int[]{0xFFa9d34f, 0xFFfc9930, 0xFFd63b3f};

    protected ColumnsPaletteProvider() {
        super(FastColumnRenderableSeries.class);
    }

    @Override
    public void update() {
        final XSeriesRenderPassData currentRenderPassData = (XSeriesRenderPassData) renderableSeries.getCurrentRenderPassData();

        final int size = currentRenderPassData.pointsCount();
        colors.setSize(size);

        final int[] colorsArray = colors.getItemsArray();
        final int[] indices = currentRenderPassData.indices.getItemsArray();
        for (int i = 0; i < size; i++) {
            final int index = indices[i];
            colorsArray[i] = desiredColors[index % 3];
        }
    }

    @Override
    public IntegerValues getFillColors() {
        return colors;
    }
}