package com.tuananhdo.mapper;

import com.tuananhdo.entity.DailyWeather;
import com.tuananhdo.entity.Location;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import payload.DailyWeatherDTO;
import payload.DailyWeatherListDTO;

import java.util.List;

@Component
@AllArgsConstructor
public class DailyWeatherMapper {

    private final ModelMapper mapper;

    public DailyWeatherListDTO mapToDailyWeatherListDTO(List<DailyWeather> dailyWeatherList) {
        Location location = dailyWeatherList.get(0).getDailyWeatherId().getLocation();
        List<DailyWeatherDTO> dailyWeatherDTOS = dailyWeatherList.stream()
                .map(dailyWeather -> mapper.map(dailyWeather,DailyWeatherDTO.class))
                .toList();
        DailyWeatherListDTO dailyWeatherListDTO = new DailyWeatherListDTO();
        dailyWeatherListDTO.setLocation(location.toString());
        dailyWeatherListDTO.setDailyForecast(dailyWeatherDTOS);
        return dailyWeatherListDTO;
    }

    public List<DailyWeather> mapToDailyWeatherList(List<DailyWeatherDTO> dailyWeatherDTOS) {
        return dailyWeatherDTOS.stream()
                .map(dailyWeatherDTO -> mapper.map(dailyWeatherDTO, DailyWeather.class))
                .toList();
    }
}
