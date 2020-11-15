package de.fabian.rsa;

import java.math.BigInteger;

public class Main {

    public static void main(String[] args) {
        RSA rsa = new RSA();

        BigInteger e = rsa.encode(18);
        System.out.println("18 -> " + e);
        System.out.println(e + " -> " + rsa.decode(Integer.parseInt(String.valueOf(e))));

    }

}
