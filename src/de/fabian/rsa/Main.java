package de.fabian.rsa;

public class Main {

    public static void main(String[] args) {
        RSA rsa = new RSA();

        int e = rsa.encode(2);
        System.out.println("2 -> " + e);
        System.out.println(e + " -> " + rsa.decode(e));

    }

}
