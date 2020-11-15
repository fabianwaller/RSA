package de.fabian.rsa;

import java.util.Random;

public class RSA {

    protected int p, q, n, phi, e, d;

    private final Random random;

    public RSA() {
        random = new Random();

        p = choosePrime(50);
        q = choosePrime(50);
        n = p*q;
        phi = (p-1)*(q-1);
        e = chooseE(phi);

        System.out.println("p = " + p + "; q = " + q + "; n = " + n + "; phi(n) = " + phi + "; e = " + e);
        System.out.println("public key ("+ e + "," + n + ") \n ");

        d = extendedEuklid(phi, e);
        System.out.println("private key ("+ d + "," + n + ") \n ");

    }

    public int encode(int m) {
        return (int) (Math.pow(m, e)%n);
    }

    public int decode(int c) {
        // FIX Fehler in der Berechnung wenn die Zahl zu groß wird
        return (int) (Math.pow(c, d)%n);
    }

    private int choosePrime(int pMax) {
        while(true) {
            final int prime = random.nextInt(pMax);
            if(p!=prime && q != prime) {
                if(isPrime(prime)) {
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
            if(ggt1(e, phi)) {
                return e;
            }
        }
    }

    private boolean ggt1(int a, int b) {
        int h = Math.min(a, b);
        for(int i=h; i>1; i--) {
            if ((a % i) == 0 && (b % i) == 0) {
                return false;
            }
        }
        return true;
    }

    int g, u, v;

    private int extendedEuklid(int a, int b) {
        int q, r, s, t;
        u=t=1;
        v=s=0;
        while(b>0) {
            q=a/b;
            r=a-q*b; a=b; b=r;
            r=u-q*s; u=s; s=r;
            r=v-q*t; v=t; t=r;
        }
        g=a;
        if(v<0) {
            v=v+phi;
        }
        return v;
    }

}