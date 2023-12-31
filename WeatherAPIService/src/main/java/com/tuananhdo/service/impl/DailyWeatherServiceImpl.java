package com.tuananhdo.service.impl;

import com.tuananhdo.entity.DailyWeather;
import com.tuananhdo.entity.Location;
import com.tuananhdo.repository.DailyWeatherRepository;
import com.tuananhdo.repository.LocationRepository;
import com.tuananhdo.service.AbstractLocationSerivce;
import com.tuananhdo.service.DailyWeatherService;
import org.springframework.stereotype.Service;
import payload.LocationDTO;

import java.util.List;

@Service
public class DailyWeatherServiceImpl extends AbstractLocationSerivce implements DailyWeatherService {
    private final DailyWeatherRepository dailyWeatherRepository;
    public DailyWeatherServiceImpl(LocationRepository locationRepository, DailyWeatherRepository dailyWeatherRepository) {
        this.locationRepository = locationRepository;
        this.dailyWeatherRepository = dailyWeatherRepository;
    }

    @Override
    public List<DailyWeather> getByLocation(LocationDTO locationDTO) {
        Location location = getCountryCodeAndCityName(locationDTO);
        return dailyWeatherRepository.findByLocationCode(location.getCode());
    }

    @Override
    public List<DailyWeather> getByLocationCode(String code) {
        Location location = getLocationCode(code);
        return dailyWeatherRepository.findByLocationCode(location.getCode());
    }

    @Override
    public List<DailyWeather> updateByLocationCode(String code, List<DailyWeather> dailyWeathers) {
        Location location = getLocationCode(code);
        dailyWeathers.forEach(dailyWeather -> dailyWeather.getDailyWeatherId()
                .setLocation(location));
        List<DailyWeather> dailyWeatherList = location.getDailyWeatherList();
        List<DailyWeather> dailyWeatherToRemoved = dailyWeatherList.stream()
                .filter(dailyWeather -> !dailyWeathers.contains(dailyWeather))
                .map(DailyWeather::getCoppyId)
                .toList();
        dailyWeatherList.removeAll(dailyWeatherToRemoved);
        return dailyWeatherRepository.saveAll(dailyWeathers);
    }
}
