/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.Start_project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author ASUS
 */
public class Analysis_CSV 
{
    String sections[]=new String[]{"Sprint(60-200)","Running Circal(800-1600-3000-60)","Jumping long/triple I","Jumping long/triple II"
                        ,"Jumping High I","Jumping High II","Jumping Pole","Throwing Shot I","Throwing Shot II"};
    String groups[]=new String[]{"7_8_F","7_8_M","9_10_F","9_10_M","11_12_F","11_12_M"
        ,"13_14_F","13_14_M","15_16_F","15_16_M","17_18_F","17_18_M","18_large_F","18_large_M"};
    String days[]=new String[]{"Mon","Tues","Wed","Thur","Fri"};
    
    
    //================================= Best Gamer =============================================
    //الشرح
    //تبدأ نتائج اللعبة من الستارت لـ الايند ويجب ان تحقق ست نتائج فعلية وهي المتغير كاونت
    //اما عن السورت تايب تنازلي يعني صح اما تصاعدي يعني خطأ
    //اما اسم الملف هنا نعطيه ملف معلومات الرياضيين
    
    //لايجاد وتخزين معلومات الرياضيين الستة الاولى من مجموعة العاب معينة وكتابة معلومات الرياضيين الستة الى داخل ملف ما
    //ويرجع هذا التابع مصفوفة الرياضيين الستة المشتركين بمجموعة العاب معينة
    Best_Athlete[] array_bestGamer(String fileName,String fileName_destination,int start,int end,int count,boolean sort_type) throws IOException
    {
        int i,k=0;
        String str;
        
        //----------------------------------------------------------------------
        BufferedReader f11=new BufferedReader(new FileReader(fileName));

        while((str = f11.readLine()) != null)    //ايجاد عدد المشتركين بمجموعة العاب معينة
        {
            if(check_gamer_in_game(str, start, end, count))
                k++;
        }
        f11.close();
        //----------------------------------------------------------------------
        //لتسجيل معلومات المشتركين الذي يمكن ان يأخذ احدهم الجائزة
        f11=new BufferedReader(new FileReader(fileName)); 
        Best_Athlete bst_g[]=new Best_Athlete[k];     // انشاء مصفوفة بعدد المشتركين لتسجيل معلوماتهم بها
        for(i=0;i<k;i++)
            bst_g[i]=new Best_Athlete();
        
//        i=0;
        k=0;
        while((str = f11.readLine()) != null)
        {
            if(check_gamer_in_game(str, start, end, count))
            {
                bst_g[k].name=getName_semicolon(str, 1, ',');
                bst_g[k].surname=getName_semicolon(str, 2, ',');
                bst_g[k].sex=getName_semicolon(str, 3, ',');
                bst_g[k].age=getName_semicolon(str, 4, ',');
                
                for(i=0;i<11;i++)
                    if(i>=start && i<=end)
                    {
                        bst_g[k].values[i]=getName_semicolon(str,i+5,',');
                    }
                    else
                    {
                        bst_g[k].values[i]="0";
                    }
            
                bst_g[k].avg=0;           //لايجاد كم عدد الثواني او كم عدد الامتار الحائز عليها بمجموعة العاب معينة
                for(i=start;i<=end;i++)
                bst_g[k].avg+=find_mTime(getName_semicolon(str,i+5, ','));
//                bst_g[k].avg=bst_g[k].avg/6;
                k++;
            }
        }
        f11.close();
        //----------------------------------------------------------------------
        bubblesort_bestGamer(bst_g, sort_type);         //لترتيب عناصر المصفوفة بشكل تنازلي او تصاعدي بالنسبة للمعدل
        //----------------------------------------------------------------------
        //بعد ترتيب الرياضيين سوف نختار اول ستة منهم لتسجيلهم في النهائيات
        Best_Athlete final_running[]=new Best_Athlete[6];
        k=0;
        for(i=0;i<bst_g.length;i++)                //لايجاد اول ست عناصر
            if(bst_g[i].name!="null")              //لعزل العناصر التي لديها قيمة نال
            {
                final_running[k++]=bst_g[i];
                if(k==6)
                    break;
            }
        //----------------------------------------------------------------------
        //لنقوم الآن بكتابة معلومات الرياضيين الستة الى داخل الملف الذي يدعى
        //fileName_destination
        
        int j;
        for(i=0;i<final_running.length;i++)
            {
                appendStrToFile(fileName_destination,final_running[i].name+",",true);
                appendStrToFile(fileName_destination,final_running[i].surname+",",true);
                appendStrToFile(fileName_destination,final_running[i].sex+",",true);
                appendStrToFile(fileName_destination,final_running[i].age+",",true);
                for(j=0;j<11;j++)
                    if(j>=start && j<=end)
                    {
                        appendStrToFile(fileName_destination,final_running[i].values[j]+",",true);
                    }
                    else
                    {
                        appendStrToFile(fileName_destination,",",true);
                    }
                appendStrToFile(fileName_destination,"\n",true);
                
            }
        
//        appendStrToFile(fileName_destination, str, sort_type);
        
        return final_running;
    }
    
    void bubblesort_bestGamer(Best_Athlete bst[],boolean sort_type)    //اذا كان صحيح فالترتيب يكون تنازلي واذا خطأ يكون تصاعدي
    {
        int i,j,n;
        Best_Athlete t=new Best_Athlete();
        n=bst.length;
        for(i=0;i<n-1;i++)
            for(j=0;j<n-1;j++)
                if(sort_type==false)    // تصاعدي
                {
                    if(bst[j].avg>bst[j+1].avg)
                    {
                        t=bst[j];
                        bst[j]=bst[j+1];
                        bst[j+1]=t;
                    }
                }
                else      // تنازلي
                {
                    if(bst[j].avg<bst[j+1].avg)
                    {
                        t=bst[j];
                        bst[j]=bst[j+1];
                        bst[j+1]=t;
                    }
                }
    }
    
    double find_mTime(String str)    //هذا التابع لتحويل الوقت الى ثانية
    {
        int i;
        double result;
        String aux="";
        int min=0,second=0,msecond=0;
        
        for(i=0;i<str.length();i++)
        {
            if(str.charAt(i)!=':')
            aux+=str.charAt(i);
            if(str.charAt(i)==':')
            {
                min=Atoi(aux);
                aux="";
            }
        }
        double r=Atof(aux);
        result=min*60+r;
        
        
        return result;
    }
    
    boolean check_gamer_in_game(String str,int n1,int n2,int count)    //لاختبار اشتراك اللاعب في لعبة ما لكي يتم تسجيله في قائمة افضل اللاعبين
    {
        int i,j,k;
        
        k=0;
        for(i=n1;i<=n2;i++)
        if(getName_semicolon(str, i+5, ',').length()!=0)
            k++;
        
        if(k==count)
            return true;
        return false;
    }
    
    //***********************************************************************************************
    int[][] analysis_all_games_final() throws IOException         //final*******
    {
        int i,j;
        int all[][]=new int[14][9];
        String str_name[]=new String[]{"Final_Running.csv","Final_jumping.csv","Final_throwing.csv"};
        int join1[][];
        
        for(i=0;i<3;i++)
        {
            join1=analysis_parts("files_analysis_final\\"+str_name[i]);  //لايجاد مصفوفة الاشتراك للعبة ما لملف ما
            all[i]=analysis_parts_totalTime(join1);         //لايجاد مصفوفة الوقت الذي تحوي الوقت المقدر للالعاب بكل مكان او سكشن
        }
        
        return all;
    }
    //***********************************************************************************************
    //================================= Best Gamer =============================================
    
    //=================================Sort GrouSortp and Games=============================================
    void sort_groups_and_games(String path,String fileName) throws IOException  //لفرز جدول الوقت الى عدة جداول حيث كل جدول يكون فيه وقت الالعاب لفريق ما
    {
        int i,j;
        String str,aux1,aux2,aux3;
//        String a[][]=new String[14][9];
//        for(i=0;i<14;i++) a[i]=new String[9];
        
        BufferedReader f11=new BufferedReader(new FileReader(fileName));
        
//        String path="Gamer_time";
        csv_create_files(path);
        File file = new File(path);
        file.mkdirs();
        
        while((str = f11.readLine()) != null)
        {
//            System.out.println("");
            for(j=0;j<9;j++)
            {
                aux1=getName_semicolon(str, j, ',');
                if(set_days_in_all_SortFiles(path,aux1));
                else if(set_sections_in_all_SortFiles(aux1));
                else if(set_CoffeBreak_in_all_SortFiles(path,aux1));
                else if(set_lunch_in_all_SortFiles(path,aux1));
                else if(aux1.length()!=0)
                {
                    aux2=getName_semicolon(aux1,0,'(');    //لايجاد اسم الفريق
                    
                    i=get_i_nameGroup(aux2);       //ايجاد الايندكس لاسم الفريق وفي حال لم يكن اسم الفريق يرجع التابع قيمة سالب واحد
//                    System.out.print("!!!"+i+aux2+" , ");
                    
                    if(i!=-1)       //هذا الشرط يعني ان الكلمة الذي وجدت هي احد اسماء الفريق 
                    {
                        aux3="("+getName_semicolon(aux1,1,'(') +"("+ getName_semicolon(aux1,2,'(');   //ايجاد وقت المسابقة مع وقت الاستراحة
                        appendStrToFile(path+groups[i]+".csv", sections[j]+","+aux3+"\n", true);
//                        a[i][j]=aux1;
                    }
                }
            }
        }
        
        f11.close();
//        return a;
    }
    
    boolean set_lunch_in_all_SortFiles(String path,String str) throws IOException  //كتابة اسم الغداء على جميع الملفات
    {
//        String path="Gamer_time\\";
//        String aux1=getName_semicolon(str,0,',');
        int i;
        boolean test=false;

        if(str.equals("lunch from 12:00 to 13:00"))
            test=true;
        
        if(test==true)
        {
            for(i=0;i<14;i++)        //كتابة اسم الغداء على جميع الملفات
            appendStrToFile(path+groups[i]+".csv", str+"\n", test);
        }
        
        return test;      
    }
    
    boolean set_CoffeBreak_in_all_SortFiles(String path,String str) throws IOException  //كتابة اسم الكوفي بريك على جميع الملفات
    {
//        String path="Gamer_time\\";
//        String aux1=getName_semicolon(str,0,',');
        int i;
        boolean test=false;

        if(str.equals("coffe break from 10:00 to 10:15"))
            test=true;
        
        if(test==true)
        {
            for(i=0;i<14;i++)        //كتابة اسم الكوفي بريك على جميع الملفات
            appendStrToFile(path+groups[i]+".csv", str+"\n", test);
        }
        
        return test;      
    }
    
    boolean set_days_in_all_SortFiles(String path,String str) throws IOException  //كتابة اسم اليوم على جميع الملفات
    {
//        String path="Gamer_time\\";
//        String aux1=getName_semicolon(str,0,',');
        int i;
        boolean test=false;
        for(i=0;i<5;i++)
            if(days[i].equals(str))
                test=true;
        
        if(test==true)
        {
            for(i=0;i<14;i++)        //كتابة اسم اليوم على جميع الملفات
            appendStrToFile(path+groups[i]+".csv", str+"\n", test);
        }
        
        return test;      
    }
    
    boolean set_sections_in_all_SortFiles(String str) throws IOException  //لاختبار فيما اذا كانت السلسلة من اسماء الالعاب 
    {
        boolean test=false;
        if(sections[0].equals(str))
            test=true;
        
        return test;      
    }
    
    int get_i_nameGroup(String str)     //هذا التابع لايجاد الايندكس لاسم الفريق اي يعمل هذا التابع على تبديل اسم الفريق برقم الايندكس
    {
        int i;
//        String groups[]=new String[]{"7_8_F","7_8_M","9_10_F","9_10_M","11_12_F","11_12_M"
//        ,"13_14_F","13_14_M","15_16_F","15_16_M","17_18_F","17_18_M","18_large_F","18_large_M"};
        
        for(i=0;i<14;i++)
            if(groups[i].equals(str))
                return i;
        return -1;
    }
    //=================================Sort Group and Games=============================================
    //==============================================================================================================
    //لايجاد مصفوفة الوقت الفعلي لجميع ملفات الفرز
    //وهي مصفوفة تضم في كل سطر فريق والذي يكون فيه مدة الوقت المعين للعب في كل محطة 
    int[][] analysis_all_games() throws IOException         //لايجاد مصفوفة الوقت الفعلي لجميع ملفات الفرز

    {
        int i,j;
        int all[][]=new int[14][9];
        String str_name[]=new String[]{"7_8_F.csv","7_8_M.csv","9_10_F.csv","9_10_M.csv","11_12_F.csv","11_12_M.csv"
        ,"13_14_F.csv","13_14_M.csv","15_16_F.csv","15_16_M.csv","17_18_F.csv","17_18_M.csv","18_large_F.csv","18_large_M.csv"};
        int join1[][];
        
        for(i=0;i<14;i++)
        {
            //لايجاد مصفوفة الاشتراك لملف من ملفات الفرز
            //Join_file[] -->   (Power Point)
            join1=analysis_parts("files_analysis\\"+str_name[i]);  //لايجاد مصفوفة الاشتراك لملف ما
            //time[]-->3*m1     (Power Point)
            all[i]=analysis_parts_totalTime(join1);         //لايجاد مصفوفة الوقت الذي تحوي الوقت المقدر للالعاب بكل مكان او سكشن
        }
        
        
        return all;
    }
    int[][] analysis_parts(int arr[][])     //لايجاد مصفوفة الاشتراك للعبة ما لمصفوفة الوقت الفعلي للالعاب
    {
        int i,j;
        int join1[][]=new int[arr.length][9];
        
        for(i=0;i<arr.length;i++)
            for(j=0;j<arr[0].length;j++)
                if(arr[i][j]!=0)
                    join1[i][j]=0;
                else
                    join1[i][j]=1;
        
        return join1;
    }
    
    //لايجاد مصفوفة الاشتراك لكل لاعب موجود داخل ملف ما حيث يوجد في كل سطر اصفار وواحدات 
    //الصفر يعبر ان اللاعب مشترك باللعبة اما الواحد يعبر ان اللاعب غير مشترك باللعبة
    int[][] analysis_parts(String fileName) throws IOException  //لايجاد مصفوفة الاشتراك للعبة ما لملف ما
    {
        int i,j,n;
        String str;
        int join1[][];
        //ايجاد عدد الاسماء في الملف
        BufferedReader file1=new BufferedReader(new FileReader(fileName));
        for(n=0;((str = file1.readLine()) != null);n++);  //لايجاد عدد الرياضيين الذين داخل الملف
        file1.close();
        
        //نعرف عن مصفوف اللعبة
        join1=new int[n][11];
        
        //نوجد مصفوفة الاشتراك بالالعاب
        file1=new BufferedReader(new FileReader(fileName));
        
        for(i=0;((str = file1.readLine()) != null);i++)
        {
            for(j=0;j<11;j++)                    //فرز الالعاب
                if(getName_semicolon(str,j+4,',').length()==0)
                join1[i][j]=1;   //غير مشترك باللعبة يعني واحد
                else
                join1[i][j]=0; 
        }

        file1.close();
        return join1;
    }
    
    int [] analysis_parts_totalTime(int a[][])  //لايجاد مصفوفة الوقت الذي تحوي الوقت المقدر للالعاب بكل مكان او سكشن
    {
        int i;
//        double time_calculate;
        int games_count[]=new int[11];
        int total_time[]=new int[9];     //الوقت النهائي لكل عدد الاماكن التي لدينا
        
        //Count_file[]-->3*m   (Power Point)
        for(i=0;i<11;i++)          //لايجاد عدد المشاركين في كل لعبة
        games_count[i]=analysis_parts_n(a,i);
        
        //-----------------طباعة عدد المشاركين لكل لعبة-----------------------
//        System.out.print("games_counts: \n");
//        for(i=0;i<11;i++)
//            System.out.print(games_count[i]+" , ");
//        System.out.println("");
        //-----------------طباعة عدد المشاركين لكل لعبة-----------------------
        
        //total time for Sprint
        total_time[0]=analysis_parts_time(games_count[0],8,1) + analysis_parts_time(games_count[1],8,1);
        
        //total for Running circal without Sprint
        total_time[1]=analysis_parts_time(games_count[2],6,3) + analysis_parts_time(games_count[3],6,5) 
                + analysis_parts_time(games_count[4],6,10) + analysis_parts_time(games_count[5],6,10);
        
        //total time for long/triple to I and II
        total_time[2]=((int)(games_count[6]+games_count[7])/2)*3;
        total_time[3]=analysis_parts_time((games_count[6]+games_count[7]),2,3);
        
        //total time for High_Jump to I and II
        total_time[4]=((int)games_count[8]/2)*3;
        total_time[5]=analysis_parts_time(games_count[8],2,3);
        
        //total time for Pole_Jump
        total_time[6]=analysis_parts_time(games_count[9],1,3);
        
        //total time for Shot_Thtowing to I and II
        total_time[7]=((int)games_count[10]/2)*5;
        total_time[8]=analysis_parts_time(games_count[10],2,5);
        
        //time[]-->1*m1   (Power Point)
        return total_time;
    }
    
    int analysis_parts_n(int a[][],int colom)   //لايجاد عدد المشتركين بلعبة ما
    {
        int i,n=0;
        for(i=0;i<a.length;i++)
            if(a[i][colom]==0)
                n++;
        return n;
    }
    
    int analysis_parts_time(int n,int k,int time1)   //لحساب وتقدير الوقت اللازم لكل لعبة حيث تأخذ عدد اللاعبين وترجع الوقت اللازم
    {
        int k1 = 0;
//        if((int)n/k!=0 && k!=1)
//            k1=((double)n/k==(int)n/k)? n/k : n/k+1;
//        else
//        {
//            if(k==1) k1=n;      //اذا كانت اللعبة تلعب واحد واحد
//            else k1=1;          //اذا كانت اللعبة تلعب فريق فريق
//        }
        
        if(k==1)
        {
            k1=n;
        }
        else if(k!=1)
        {
            if((int)n/k!=0)         //يعني الفريق يجب ألا يكون اقل من العدد كاي
                k1=((double)n/k==(int)n/k)? n/k : ((n/k)+1);
            else if(n!=0)        //بحالة عدد الاشخاص لا تساوي الصفر معناها لدينا فريق واحد لان الشرط الاول غير محقق
                k1=1;
            else if(n==0)     // في حال لم يوجد مشتركين يجب ان يكون كاي واحد يساوي الصفر
                k1=0;
        }
        
        k1=k1*time1;
        return k1;
    }
    
    void csv_analysis(String fileName) throws IOException //لفرز الملف المعطى بحسب العمر والجنس
    {
        int i,j;
        String st,s1,s2,s3,s4;
        String deg[]=new String[11];
        csv_create_files("files_analysis\\");
        
        BufferedReader f11=new BufferedReader(new FileReader(fileName));
        
        //for(i=0;(i<4)&&((st = f11.readLine()) != null);i++);  //لتخطي اول اربع اسطر من الملف المطلوب لانهم رؤوس فقط وليست بيانات
        
        for(i=0;((st = f11.readLine()) != null);i++)
        {
            if(st.charAt(0)==',')          //اذا تم انتهاء الملف سوف يكون احيانا أول محرف فيه هو فاصلة 
                break; 

            s1=getName_semicolon(st,1,',');    //لايجاد الاسم
            s2=getName_semicolon(st,2,',');    //لايجاد الكنية
            s3=getName_semicolon(st,3,',');    //لايجاد الجنس
            s4=getName_semicolon(st,4,',');    //لايجاد العمر
            
            for(j=0;j<11;j++)                    //فرز الالعاب
                deg[j]=getName_semicolon(st,j+5,',');

            data_sort(s1,s2,s3,s4,deg);

//            System.out.println(s1);
        }
//        System.out.println("read i = "+i);
        
        f11.close();
    }
    
    void csv_create_files(String folderName) throws IOException    //لانشاء ملفات المشروع الذي تتضمن ملفات فرز البيانات الى الجنس والعمر
    {
        File file;
        file = new File(folderName);
        file.mkdirs();
        int i;
        
        for(i=0;i<14;i++)
            appendStrToFile(folderName+groups[i]+".csv","",false);
    }
    
    void appendStrToFile(String fileName,String str,boolean b1) throws IOException  //للكتابة على الملف او انشاء الملفات
    {
        FileWriter fd=new FileWriter(fileName, b1);
        fd.write(str);
        fd.close();
    }
    
    public String getName_semicolon(String str,int count,char ch) //لايجاد السلسلة الذي تكون بعد عدد ما من الفواصل
    {
        int i,count0=0,i1,i2;
        String aux;
        
        for(i=0;i<str.length();i++)
        {
            if(count0==count)
                break;
            if(str.charAt(i)==ch)
                count0++;
        }
        i1=i;     //start index name
        
        for(i=i1;i<str.length();i++)
        {
            if(str.charAt(i)==ch)
                break;
        }
        i2=i;     //last index name
        
        aux=str.substring(i1,i2);
        
        return aux;
    }
    
    public void data_sort(String s1,String s2,String s3,String s4,String deg[]) throws IOException  //لفرز البيانات بحسب الجنس والعمر
    {
        int i;
        String name=s1;
        String surname=s2;
        String sex=s3;
        String age=s4;
        String str_line;
        //
        str_line=name+","+surname+","+sex+","+age+",";
        
        for(i=0;i<11;i++)
            str_line=str_line+deg[i]+",";
        
        str_line=str_line+"\n";
        
        if((Atoi(age)==7 || Atoi(age)==8) && sex.charAt(0)=='F')
            appendStrToFile("files_analysis\\7_8_F.csv",str_line,true);
        if((Atoi(age)==7 || Atoi(age)==8) && sex.charAt(0)=='M')
            appendStrToFile("files_analysis\\7_8_M.csv",str_line,true);
        
        if((Atoi(age)==9 || Atoi(age)==10) && sex.charAt(0)=='F')
            appendStrToFile("files_analysis\\9_10_F.csv",str_line,true);
        if((Atoi(age)==9 || Atoi(age)==10) && sex.charAt(0)=='M')
            appendStrToFile("files_analysis\\9_10_M.csv",str_line,true);
        
        if((Atoi(age)==11 || Atoi(age)==12) && sex.charAt(0)=='F')
            appendStrToFile("files_analysis\\11_12_F.csv",str_line,true);
        if((Atoi(age)==11 || Atoi(age)==12) && sex.charAt(0)=='M')
            appendStrToFile("files_analysis\\11_12_M.csv",str_line,true);
        
        if((Atoi(age)==13 || Atoi(age)==14) && sex.charAt(0)=='F')
            appendStrToFile("files_analysis\\13_14_F.csv",str_line,true);
        if((Atoi(age)==13 || Atoi(age)==14) && sex.charAt(0)=='M')
            appendStrToFile("files_analysis\\13_14_M.csv",str_line,true);
        
        if((Atoi(age)==15 || Atoi(age)==16) && sex.charAt(0)=='F')
            appendStrToFile("files_analysis\\15_16_F.csv",str_line,true);
        if((Atoi(age)==15 || Atoi(age)==16) && sex.charAt(0)=='M')
            appendStrToFile("files_analysis\\15_16_M.csv",str_line,true);
        
        if((Atoi(age)==17 || Atoi(age)==18) && sex.charAt(0)=='F')
            appendStrToFile("files_analysis\\17_18_F.csv",str_line,true);
        if((Atoi(age)==17 || Atoi(age)==18) && sex.charAt(0)=='M')
            appendStrToFile("files_analysis\\17_18_M.csv",str_line,true);
        
        if((Atoi(age) > 18) && sex.charAt(0)=='F')
            appendStrToFile("files_analysis\\18_large_F.csv",str_line,true);
        if((Atoi(age) > 18) && sex.charAt(0)=='M')
            appendStrToFile("files_analysis\\18_large_M.csv",str_line,true);

    }
    
    double Atof(String s)
    {
        int i, numMinuses = 0, numDots = 0;

        s = s.trim();
        for (i = 0; i < s.length()
                && (s.charAt(i) == '-' || s.charAt(i) == '.' || Character.isDigit(s.charAt(i))); i++)
            if (s.charAt(i) == '-')
                numMinuses++;
            else if (s.charAt(i) == '.')
                numDots++;

        if (i != 0 && numMinuses < 2 && numDots < 2)
            return Double.parseDouble(s.substring(0, i));

        return 0.0;
    }
    
    int Atoi(String str)  //لايجاد العدد من سلسلة محرفية
    {

        int res = 0;
        for (int i = 0; i < str.length(); i++)
            res = res * 10 + str.charAt(i) - '0';

        return res;
    }
}
