package ru.job4j.weather;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class WeatherService {

    private final Map<Integer, Weather> weathers = new ConcurrentHashMap<>();
    {
        weathers.put(1, new Weather(1, "Msc", 20));
        weathers.put(2, new Weather(2, "SPb", 15));
        weathers.put(3, new Weather(3, "Bryansk", 17));
        weathers.put(4, new Weather(4, "Smolensk", 15));
        weathers.put(5, new Weather(5, "Kiev", 16));
        weathers.put(6, new Weather(6, "Minsk", 15));
    }

    public Mono<Weather> findById(Integer id) {
        return Mono.justOrEmpty(weathers.get(id));
    }

    public Flux<Weather> all() {
        return Flux.fromIterable(weathers.values());
    }

    public Mono<Weather> hottest(){
        int maxValue = Integer.MIN_VALUE;
        Weather result = null;
        for (Weather value : weathers.values()) {
            if (value.getTemperature() > maxValue) {
                maxValue = value.getTemperature();
                result = value;
            }
        }
        return Mono.justOrEmpty(result);
    }

    public Flux<Weather> allCityGreatThen(int temp) {
        List<Weather> result = weathers.values().stream()
                .filter(val -> val.getTemperature() >= temp)
                .collect(Collectors.toList());
        return Flux.fromIterable(result);
    }
}
