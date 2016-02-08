package Tetris;

import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

/**
 * Created by Nat-nyan on 19.11.2015.
 */
public class KeyGetter {

    private static HashMap<Integer, String> keys;

    public  static  void loadKeys() {
        keys = new HashMap<Integer, String>();
        Field[] fields = KeyEvent.class.getFields();
        for (Field f: fields) {
            if(Modifier.isStatic(f.getModifiers())) {
                if(f.getName().startsWith("VK")) {
                    try {
                        int num = f.getInt(null);
                        String name = KeyEvent.getKeyText(num);
                        keys.put(num, name);
                        System.out.println(name);
                        //keys.put(f.getInt(null), KeyEvent.getKeyText(f.getInt(null)));
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
