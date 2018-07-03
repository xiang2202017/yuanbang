package com.health.system.util;

public class CustomUtil
{
  public static String getNewsHeader(String title, String imgPath, String time, String clickNum)
  {
    String headstr = "<p align=\"center\"><font size=\"6\" style=\"font-family:Microsoft YaHei,tahoma,arial,sans-serif;\">" + title + "</font></p><p align=\"center\"><font color=\"#999999\">" + time + "&nbsp;&nbsp;&nbsp;&nbsp;浏览次数: " + clickNum + "</font></p><p align=\"center\"><img src=\"" + imgPath + "\" width=\"380\" height=\"250\"></p><p><br/></p>";
    return headstr;
  }
  
  public static String getMsgHeader(String title, String imgPath, String time)
  {
    String headstr = "<p align=\"center\"><font size=\"6\" style=\"font-family:Microsoft YaHei,tahoma,arial,sans-serif;\">" + title + "</font></p><p align=\"center\"><font color=\"#999999\">" + time + "</font></p><p align=\"center\"><img src=\"" + imgPath + "\" width=\"280\" height=\"180\"></p><p><br/></p>";
    return headstr;
  }
  
  public static String getProductHeader(String name)
  {
    String headstr = "<p align=\"center\"><font size=\"6\" style=\"font-family:Microsoft YaHei,tahoma,arial,sans-serif;\">" + name + "</font></p><p><br/></p>";
    return headstr;
  }
  
  public static String getEducationHeader(String title)
  {
    String headstr = "<p align=\"center\"><font size=\"6\" style=\"font-family:Microsoft YaHei,tahoma,arial,sans-serif;\">" + title + "</font></p><p align=\"center\"></p><p><br/></p>";
    return headstr;
  }
  
  public static String getTravelHeader(String title)
  {
    String headstr = "<p align=\"center\"><font size=\"6\" style=\"font-family:Microsoft YaHei,tahoma,arial,sans-serif;\">" + title + "</font></p><p align=\"center\"></p><p><br/></p>";
    return headstr;
  }
}