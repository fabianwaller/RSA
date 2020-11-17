package de.fabian.rsa;

import java.math.BigInteger;
import java.util.Random;

public class RSA {

    protected int p, q, n, phi, e, d;

    private final Random random;

    public RSA() {
        random = new Random();

        p = choosePrime(2000);
        q = choosePrime(2000);
        n = p*q;
        phi = (p-1)*(q-1);
        e = chooseE(phi);
        //System.out.println("p = " + p + "; q = " + q + "; n = " + n + "; phi(n) = " + phi + "; e = " + e + ";");
        System.out.println("public key ("+ e + "," + n + ")");

        d = extendedEuklid(phi, e);
        System.out.println("private key ("+ d + "," + n + ") \n ");
    }

    public int encode(int m) {
        //return (int) (Math.pow(m, e)%n);
        return new BigInteger(String.valueOf(m)).modPow(new BigInteger(String.valueOf(e)), new BigInteger(String.valueOf(n))).intValue();
    }

    public int decode(int c) {
        return new BigInteger(String.valueOf(c)).modPow(new BigInteger(String.valueOf(d)), new BigInteger(String.valueOf(n))).intValue();
    }

    private int choosePrime(int pMax) {
        while(true) {
            final int prime = random.nextInt(pMax);
            if(p!=prime && q != prime) {
                if(prime> 3 && isPrime(prime)) {
                    return prime;
                }
            }
        }
    }

    private boolean isPrime(int n) {
        if(n<2) {
            return false;
        }
        final int wurzel = (int) Math.sqrt(n);
        for(int i=2; i<=wurzel; i++) {
            if(n%i == 0) {
                return false;
            }
        }
        return true;
    }

    private int chooseE(int eMax) {
        while(true) {
            final int e = random.nextInt(eMax);
            if(teilerfremd(e, phi)) {
                return e;
            }
        }
    }

    private boolean teilerfremd(int a, int b) {
        int h = Math.min(a, b);
        for(int i=h; i>1; i--) {
            if ((a % i) == 0 && (b % i) == 0) {
                return false;
            }
        }
        return true;
    }

    protected int g, u, v;

    private int extendedEuklid(int a, int b) {
        int q, r, s, t;
        u=t=1;
        v=s=0;
        //System.out.println("u=t = " + u + "; v=s = " + v + ";");
        while(b>0) {
            //System.out.println("--------------------------");
            q=a/b;    //System.out.println("q = " + a + "/" + b + " = " + q);
            r=a-q*b;  //System.out.println("r = " + a + " - " + q + " * " + b + " = " + r);
            a=b; b=r; //System.out.println("b -> a = " + a + "; r -> b = " + b + "; \n");

            r=u-q*s;  //System.out.println("r = " + u + " - " + q + " * " + s + " = " + r);
            u=s; s=r; //System.out.println("s -> u = " + u + "; r -> s = " + s + ";");
            r=v-q*t;  //System.out.println("r = " + v + " - " + q + " * " + t + " = " + r);
            v=t; t=r; //System.out.println("t -> v = " + v + "; r -> t = " + t + ";");
        }
        //System.out.println("--------------------------");
        g=a;
        if(v<0) {
            v=v+phi;
        }
        //System.out.println("g = " + g + "; u = " + u + "; v = " + v + ";");
        return v;
    }

}
