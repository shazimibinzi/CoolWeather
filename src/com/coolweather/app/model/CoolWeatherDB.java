package com.coolweather.app.model;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.coolweather.app.db.CoolWeatherOpenHelper;

public class CoolWeatherDB {
	//���ݿ�����
	public static final String DB_NAME="cool weather";
	
	//���ݿ�汾
	public static final int VERSION=1;
	private CoolWeatherDB coolWeatherDB;
	private SQLiteDatabase db;
	
	//���췽��˽�л�
	public CoolWeatherDB(Context context){
		CoolWeatherOpenHelper dbHelper=new CoolWeatherOpenHelper(context,DB_NAME,null,VERSION);
		db=dbHelper.getWritableDatabase();
	}
	
      //��ȡCoolWeather��ʵ��
	public synchronized  CoolWeatherDB getInstance(Context context){
		if(coolWeatherDB==null){
			coolWeatherDB=new CoolWeatherDB(context);
		} return coolWeatherDB;
	}
	
	//�洢provinceʵ�������ݿ�
	public void saveProvince(Province province){
		if(province!=null){
			ContentValues values=new ContentValues();
			values.put("province_name", province.getProvinceName());
			values.put("province_code", province.getProvinceCode());
			db.insert("Province", null, values);
		}
	}
	
	//�������ж�ȡȫ��ʡ�ݵ���Ϣ
	public  List<Province> loadProvinces(){
		List<Province> list=new ArrayList<Province>();
		Cursor cursor=db.query("Province", null, null, null, null, null, null);
		if(cursor.moveToFirst()){
			do {
				Province province =new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				province.setProvinceName(cursor.getString(cursor.getColumnIndex("provinceName")));
				province.setProvinceCode(cursor.getString(cursor.getColumnIndex("provinceCode")));
				list.add(province);
			} while(cursor.moveToNext());
		}
		return list;
	}
	
	
	//�洢cityʵ�������ݿ�
	public void saveCity(City city){
		if (city!=null){
			ContentValues values=new ContentValues();
			values.put("city_name", city.getCityName());
			values.put("city_code", city.getCityCode());
			values.put("province_id", city.getProvinceId());
			db.insert("City", null, values);
		}
	}
	
	//�������ж�ȡĳʡ���³��е���Ϣ
	public List<City> loadCities(int provinceId){
	 List<City> list=new ArrayList<City>();
	 Cursor cursor=db.query("City", null,"province_id=?",new String[]{String.valueOf(provinceId)},null,null,null);
	 if (cursor.moveToFirst()){
		 do{
			 City city=new City();
			 city.setId(cursor.getInt(cursor.getColumnIndex("id")));
			 city.setCityName(cursor.getString(cursor.getColumnIndex("cityName")));
			 city.setCityCode(cursor.getString(cursor.getColumnIndex("cityCode")));
			 city.setProvinceId(provinceId);
			 list.add(city);
			 
		 } while(cursor.moveToNext());
	 }  return list;
	}
	
	//�洢countryʵ�������ݿ�
	public void saveCountry(Country country){
		if (country!=null){
			ContentValues values =new ContentValues();
			values.put("country_name", country.getcountryName());
			values.put("country_code", country.getCountryCode());
			values.put("city_id", country.getCityId());
			db.insert("Country", null, values);
		}
		
	}
	
	//�����ݿ��ж�ȡĳ��������Ժ����Ϣ
	public List<Country> loadCountries(int cityId){
		List<Country> list=new ArrayList<Country>();
		Cursor cursor =db.query("Country", null, "City_id=?",new String[]{String.valueOf(cityId)},null,null,null);
		if(cursor.moveToFirst()){
			do{
				Country country=new Country();
				country.setId(cursor.getInt(cursor.getColumnIndex("id")));
				country.setCountryName(cursor.getString(cursor.getColumnIndex("countryName")));
				country.setCountryCode(cursor.getString(cursor.getColumnIndex("countryCode")));
				country.setCityId(cursor.getInt(cursor.getColumnIndex("cityId")));
				
			} while (cursor.moveToNext());
		} return list;
		
	}
}