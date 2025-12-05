package pt.isel;

import java.util.ArrayList;
import java.util.stream.Stream;

public class App {
    static class Weather {
        private final boolean sunny;
        private final int celsius;

        public Weather(boolean sunny, int celsius) {
            this.sunny = sunny;
            this.celsius = celsius;
        }

        public boolean isSunny() {
            return sunny;
        }

        public int getCelsius() {
            return celsius;
        }
    }

    static void imperativeApproach() {
        Stream<Weather> src = Stream.empty();
        Iterable<Weather> pastWeather = src::iterator;
        var top5temps = new ArrayList<Integer>();
        for (var w : pastWeather){
            if (w.isSunny()) {                  // ~ filter
                top5temps.add(w.getCelsius());  // ~ map
                if (top5temps.size() >= 5) {    // ~ limit
                    break;
                }
            }
        }
    }
}
