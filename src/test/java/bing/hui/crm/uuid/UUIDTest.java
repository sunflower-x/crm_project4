package bing.hui.crm.uuid;

import java.util.UUID;

public class UUIDTest {
    public static void main(String[] args) {
        String s = UUID.randomUUID().toString().replaceAll("-","");
        System.out.println(s);
    }
}
