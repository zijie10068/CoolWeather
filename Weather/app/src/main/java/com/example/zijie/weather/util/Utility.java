package com.example.zijie.weather.util;

import android.text.TextUtils;
import android.util.Log;

import com.example.zijie.weather.db.City;
import com.example.zijie.weather.db.County;
import com.example.zijie.weather.db.Provice;
import com.example.zijie.weather.gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

public class Utility  {

    public static boolean handleCityResponse(String response,int porvinceId)
    {
        if(!TextUtils.isEmpty(response))
        {
            try
            {
                JSONArray allCities = new JSONArray(response);
                for(int i = 0;i<allCities.length();i++)
                {
                    JSONObject cityObject = allCities.getJSONObject(i);
                    City city = new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProviceid(porvinceId);
                    city.save();
                }
                return true;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return  false;
    }

    public static boolean handleCountyResponse(String response,int cityId)
    {
        if(!TextUtils.isEmpty(response))
        {
            try
            {
                JSONArray allCounties = new JSONArray(response);
                for(int i = 0;i<allCounties.length();i++)
                {
                    JSONObject countyObject = allCounties.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(countyObject.getString("name"));
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean handleProvinceResponse(String response)
    {
        if(!TextUtils.isEmpty(response))
        {
            try
            {
                JSONArray allProvinces = new JSONArray(response);
                for(int i = 0;i<allProvinces.length();i++)
                {
                    JSONObject jsonObject = allProvinces.getJSONObject(i);
                    Provice provice = new Provice();
                    provice.setProvinceCode(jsonObject.getInt("id"));
                    provice.setProvinceName(jsonObject.getString("name"));
                    provice.save();
                }
                Log.d("插入成功", "handleProvinceResponse: ");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    public static Weather handleWeatherResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent,Weather.class);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
