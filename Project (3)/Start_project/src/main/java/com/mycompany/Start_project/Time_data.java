/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.Start_project;

/**
 *
 * @author ASUS
 */
public class Time_data {
    
    //h_start: وقت البدء بالساعات اي بأي ساعة؟ سوف نبدأ من الساعة الثامنة 
    //min: نعطي هنا الدقائق ليتم تنسيقها مع وقت البدء
    public String time_format(double h_start,double min)    //تنسيق الوقت
    {
       double minutes;
       minutes=h_start*60+min;
       int h1,m1;
       h1=(int)minutes/60;         //5
       m1=(int)minutes-(h1*60);
       
       String msg;
       msg=Integer.toString((int)h1/10);
       msg=msg+Integer.toString(h1%10);
       msg=msg+":";
       msg=msg+Integer.toString((int)m1/10);
       msg=msg+Integer.toString(m1%10);
       
       return msg;
    }
}
