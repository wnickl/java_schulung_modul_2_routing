/** 
 * Copyright 2013 SSI Schaefer PEEM GmbH. All Rights reserved. 
 * <br /> <br />
 * 
 * $Id$
 * <br /> <br />
 *
 */

package at.edu.hti.routing.util;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the class header. The first sentence (ending with "."+SPACE) is important, because it is
 * used summary in the package overview pages.<br />
 * <br />
 * 
 * @author nickl
 * @version $Revision$
 */

public final class StringUtils {

  public static final String pad(String str, boolean right, int size, char padChar) {
    if (str == null) {
      return "";
    }
  
    if (str.length() > size) {
      return str;
    }
    char[] temp = new char[size];
    int i = 0;
  
    if (right) {
      while (i < str.length()) {
        temp[i] = str.charAt(i);
        i++;
      }
      while (i < size) {
        temp[i] = padChar;
        i++;
      }
    } else {
      int tmpSize = size - str.length();
      while (i < tmpSize) {
        temp[i] = padChar;
        i++;
      }
      tmpSize = size - i;
      int tmpIdx = 0;
      while (tmpIdx < tmpSize) {
        temp[i] = str.charAt(tmpIdx);
        tmpIdx++;
        i++;
      }
    }
    return String.valueOf(temp);
  }

  public static final String center(String s, int size) {
    StringBuilder sb = new StringBuilder();
    List<String> tmp = new ArrayList<String>();
  
    if (s == null || s.length() == 0) {
      return "";
    }
  
    if (s.length() >= size) {
      String s2 = s;
      while (s2.length() > size) {
        String _s = s2.substring(0, size);
        // get the index of the last whitespace char of _s
        int last = _s.lastIndexOf(' ');
        if (last == -1) {
          tmp.add(_s);
          s2 = s2.substring(_s.length());
        } else {
          last++;
          tmp.add(s2.substring(0, last));
          s2 = s2.substring(last);
        }
      }
      tmp.add(s2);
    } else {
      tmp.add(s);
    }
  
    int i = 0;
    for (String str : tmp) {
      if (str.length() < size) {
        int toPad = size / 2;
        String s1 = str.substring(0, str.length() / 2);
        String s2 = str.substring(str.length() / 2);
  
        s1 = pad(s1, false, toPad, ' ');
        s2 = pad(s2, true, toPad, ' ');
  
        String padded = pad(s1 + s2, true, size, ' ');
        sb.append(padded);
      } else {
        sb.append(str);
      }
  
      if (++i < tmp.size()) {
        sb.append("\n");
      }
    }
    return sb.toString();
  }

}

//---------------------------- Revision History ----------------------------
//$Log$
//
