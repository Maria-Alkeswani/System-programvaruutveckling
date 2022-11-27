/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.Start_project;

/**
 *
 * @author ASUS
 */
public class Athlete 
{
    public int t1,t2;    //وقت البدء  وقت الانتهاء
    public int s1,s2;    //لتسجيل وقت الاستراحة من البدية الى النهاية
    public int check_g[]=new int[10];     //رقم اللعبة للتحقق من دخولها
    public int check_t[]=new int[10];     //رقم اللعبة للتحقق من وقتها
    
    Athlete(int count_games)
    {
        t1=0; t2=0; s1=0; s2=0;
        check_g=new int[count_games];
        check_t=new int[count_games];
        for(int i=0;i<count_games;i++)
        check_g[i]=0;
    }
//    void gamer_set_count_game(int count_games)
//    {
//        
//    }
}
