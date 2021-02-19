package com.xx.cortp.utils;



import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
	public final static SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
	public final static SimpleDateFormat sdfDays = new SimpleDateFormat("yyyyMMdd");
	public final static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public final static SimpleDateFormat sdfyymmdd = new SimpleDateFormat("yyMMdd");
	public final static SimpleDateFormat sdfyyyymm = new SimpleDateFormat("yyyyMM");
	public final static SimpleDateFormat sdfyyyymmV2 = new SimpleDateFormat("yyyy-MM");
	/**
	 * 获取YYYY格式
	 * @return
	 */
	public static String getYear() {
		return sdfYear.format(new Date());
	}

	/**
	 * 获取YYYY-MM格式
	 * @return
	 */
	public static String getMonth() {
		return sdfyyyymmV2.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD格式
	 * @return
	 */
	public static String getDay() {
		return sdfDay.format(new Date());
	}
	
	/**
	 * 获取YYYYMMDD格式
	 * @return
	 */
	public static String getDays(){
		return sdfDays.format(new Date());
	}

	/**
	 * 获取YYYY-MM-DD HH:mm:ss格式 
	 * @return
	 */
	public static String getTime() {
		return sdfTime.format(new Date());
	}

	/**
	* @Title: compareDate
	* @Description: TODO(日期比较，如果s>=e 返回true 否则返回false)
	* @param s
	* @param e
	* @return boolean  
	* @throws
	 */
	public static boolean compareDate(String s, String e) {
		if(fomatDate(s)==null||fomatDate(e)==null){
			return false;
		}
		return fomatDate(s).getTime() >=fomatDate(e).getTime();
	}

	/**
	 * 格式化日期
	 * @return
	 */
	public static Date fomatDateTime(String date) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd ");
		try {
			return fmt.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 格式化时间
	 * @return
	 */
	public static Date fomatDate(String date) {
		try {
			return sdfTime.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 校验日期是否合法
	 * 
	 * @return
	 */
	public static boolean isValidDate(String s) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			fmt.parse(s);
			return true;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return false;
		}
	}
	public static int getDiffYear(String startTime,String endTime) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			long aa=0;
			int years=(int) (((fmt.parse(endTime).getTime()-fmt.parse(startTime).getTime())/ (1000 * 60 * 60 * 24))/365);
			return years;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return 0;
		}
	}
	  /**
     * <li>功能描述：时间相减得到天数
     * @param beginDateStr
     * @param endDateStr
     * @return
     * long 
     * @author Administrator
     */
    public static long getDaySub(String beginDateStr,String endDateStr){
        long day=0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = null;
        Date endDate = null;
        
            try {
				beginDate = format.parse(beginDateStr);
				endDate= format.parse(endDateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
            day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
            //System.out.println("相隔的天数="+day);
      
        return day;
    }
    public static long getDaySub(Date beginDateStr,Date endDateStr){
        long day=0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = null;
        Date endDate = null;

            try {
            	String begin = format.format(beginDateStr);
            	String end = format.format(endDateStr);
				beginDate = format.parse(begin);
				endDate= format.parse(end);
			} catch (ParseException e) {
				e.printStackTrace();
			}
            day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
            //System.out.println("相隔的天数="+day);

        return day;
    }

    
    /**
     * <li>功能描述：时间相减得到天数
     * @param beginDateStr
     * @param endDateStr
     * @return
     * long 
     * @author Administrator
     */
    public static long getDaySub1(String beginDateStr,String endDateStr){
        long day=0;
        Date beginDate = null;
        Date endDate = null;
        
            try {
				beginDate = sdfTime.parse(beginDateStr);
				endDate= sdfTime.parse(endDateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
            day=(endDate.getTime()-beginDate.getTime())/(60*60*1000);
            //System.out.println("相隔的天数="+day);
      
        return day;
    }
    
    /**
     * 得到n天之后的日期
     * @param days
     * @return
     */
    public static String getAfterDayDate(String days) {
    	int daysInt = Integer.parseInt(days);
    	
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        
        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdfd.format(date);
        
        return dateStr;
    }

	/**
	 * 获取n月之前的年月份
	 * @param i
	 * @return
	 */
	public static String getLastMonths(int i) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MONTH, -i);
		Date m = c.getTime();
		return sdf.format(m);
	}

	/**
	 * 获取n月之前的年月份
	 * @param i
	 * @return
	 */
	public static String getLastMonths2(int i) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MONTH, -i);
		Date m = c.getTime();
		return sdf.format(m);
	}

	/**
	 * 指定日期后几天
	 * @param startDay 指定日期
	 * @param count    天数
	 * @user Yaoyn
	 */
	public static String getAfterDay(String startDay, int count) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = sdf.parse(startDay);
			Calendar cl = Calendar.getInstance();
			cl.setTime(date);
			cl.add(Calendar.DATE, count);
			return sdf.format(cl.getTime());
		} catch (Exception e) {
		}
		return "";
	}
    
    /**
     * 得到n天之后是周几
     * @param days
     * @return
     */
    public static String getAfterDayWeek(String days) {
    	int daysInt = Integer.parseInt(days);
    	
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();
        
        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String dateStr = sdf.format(date);
        
        return dateStr;
    }


	public static int diffDate(Date date) {
		try
		{
			long diff = date.getTime() - new Date().getTime();//这样得到的差值是毫秒级别
			long days = diff / (1000 * 60 * 60 * 24);

			long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
			return (int) hours;
		}catch (Exception e)
		{
			return 0;
		}
	}
    
	//new Timestamp(System.currentTimeMillis())
	//Timestamp.valueOf(sdf.format(new Date())) 
    //秒转化为时间
    public static String TsSecond2DtimeString(double doubleTimeStampSecond,String dtformat) {
 
    	SimpleDateFormat df=new SimpleDateFormat(dtformat); //定义格式
    	long longTimeMillis=(long)doubleTimeStampSecond*1000;
    	Timestamp ts       = new Timestamp(longTimeMillis);//获取系统当前时间
    	return df.format(ts);   
    }
    
    //毫秒转换为时间
    public static String Ts2DtimeString(long longTimeMillis,String dtformat) {
    	 
    	SimpleDateFormat df=new SimpleDateFormat(dtformat); //定义格式
    	Timestamp ts       = new Timestamp(longTimeMillis);//获取当前时间
    	return df.format(ts);   
    }
    
  //new Timestamp(System.currentTimeMillis())
  	//Timestamp.valueOf(sdf.format(new Date())) 
      public static String TsString2DtimeString(String tsString,String dtformat) {
   
      	SimpleDateFormat df=new SimpleDateFormat(dtformat); //定义格式
      	long longTimeMillis=(long)Double.parseDouble(tsString)*1000;
      	Timestamp ts       = new Timestamp(longTimeMillis);//获取系统当前时间
      	return df.format(ts);   
      }
    
    public static Timestamp DT2Timestamp(Date date,String dtformat) {
    	 
    	SimpleDateFormat df=new SimpleDateFormat(dtformat); //定义格式
	    String time = df.format(date);
	    Timestamp ts = Timestamp.valueOf(time);
    	return ts;  
    }	 
    
    //日期字符串转换为日期类型
    public static Date StringtoDate(String dateString,String dformat) throws ParseException
    {
    	SimpleDateFormat sdf=new SimpleDateFormat(dformat);  
    	Date dt=sdf.parse(dateString);  
    	return dt;
	}
    
    //获取UTC TIme (unix Time)
    public static  long gettimeUTC()
    {
    	return System.currentTimeMillis()/1000;
    }
 
    

	/*
	* 月份加减
	*/
	public static String getDateAfterMonths(String dateStr,int month){
		Date dt = null;//将字符串生成Date
		try {
			dt = sdfyyyymmV2.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);//使用给定的 Date 设置此 Calendar 的时间。
		rightNow.add(Calendar.MONTH, month);// 日期加（减）month 个月
		Date dt1 = rightNow.getTime();//返回一个表示此 Calendar 时间值的 Date 对象。
		String reStr = sdfyyyymmV2.format(dt1);//将给定的 Date 格式化为日期/时间字符串，并将结果添加到给定的 StringBuffer。
		return reStr;
	}

	//获取UTC(毫秒) TIme (unix Time)String类型
	public static String gettimeUTCString()
	{
		return String.valueOf(System.currentTimeMillis()/1000);
	}




}
