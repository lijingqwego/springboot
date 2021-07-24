package com.example.demo.utils;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MapperUtil {
	
	private static final String config="config/SqlMapConfig.xml";
	
	private static SqlSession session=null;
	
	private static void initSession() {
		try {
			InputStream inputStream = Resources.getResourceAsStream(config);
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(inputStream);
			session = factory.openSession();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static <T> T getMapper(Class<T> type){
		initSession();
		return session.getMapper(type);
	}
	
	public static void closeSession(){
		if(session!=null){
			session.close();
		}
	}
	
	public static void closeUpdSession(){
		if(session!=null){
			session.commit();
			session.close();
		}
	}
}
