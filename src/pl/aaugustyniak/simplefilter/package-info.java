@Plugin({
    InvertImageModifier.class,
    HistogramEqualizeImageModifier.class,
    GaussianBlurImageModifier.class,
    SimpleSharpenImageModifier.class,
    LowPassFilterImageModifier.class,
    BwImageModifier.class
})
package pl.aaugustyniak.simplefilter;

import org.simple.pluginspi.PluginManager.Plugin;
import pl.aaugustyniak.simplefilter.plugins.BwImageModifier;

import pl.aaugustyniak.simplefilter.plugins.InvertImageModifier;
import pl.aaugustyniak.simplefilter.plugins.HistogramEqualizeImageModifier;
import pl.aaugustyniak.simplefilter.plugins.SimpleSharpenImageModifier;
import pl.aaugustyniak.simplefilter.plugins.GaussianBlurImageModifier;
import pl.aaugustyniak.simplefilter.plugins.LowPassFilterImageModifier;
