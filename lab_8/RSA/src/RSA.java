
import java.util.Arrays;
import java.util.Random;

public class RSA {

    private long n;
    private long eulerFunction;
    private long e;
    private long d;

    public RSA(long p, long q){
        countN(p,q);
        countEulerFunction(p,q);
        findE();
        findD();
    }

    public long[] getPublicKey(){
        return new long[]{e, n};
    }

    public long[] getPrivateKey(){
        return new long[]{d, n};
    }


    public long encrypt(long message, long[] key){
        return findRemainder(message, key[0], key[1]);
    }

    public long decrypt(long message, long[] key){
        return findRemainder(message, key[0], key[1]);
    }


    private void countN(long p, long q){
        n = p*q;
    }

    private void countEulerFunction(long p, long q){
        eulerFunction = (p-1)*(q-1);
    }

    private void findE(){

        e = new Random().ints(2, Math.toIntExact(eulerFunction)).limit(1).findFirst().getAsInt();
        long g = EuclideanAlgorithhm(e, eulerFunction);

        while (g != 1){
            e = new Random().ints(2, Math.toIntExact(eulerFunction)).limit(1).findFirst().getAsInt();
            g = EuclideanAlgorithhm(e, eulerFunction);
        }

    }

    private void findD(){
        d = extendedEuclideanAlgorithhm(e, eulerFunction)[0];
        while (d <= 0){
            d += eulerFunction;
        }
    }

    private long EuclideanAlgorithhm(long a, long b) {
        if (b == 0)
            return Math.abs(a);
        return EuclideanAlgorithhm(b, a % b);
    }

    private static long[] extendedEuclideanAlgorithhm(long a, long b) {
        if (a == 0) {
            return new long[]{0, 1, b};
        }
        long[] sol = extendedEuclideanAlgorithhm(b%a, a);
        long x = sol[1] - (b / a) * sol[0];
        long y = sol[0];
        return new long[]{x, y, sol[2]};
    }

    private long findRemainder(long message, long power, long mod){
        long result = message;
        for (int i = 2; i <= power; ++i){
            result *= message;
            result %= mod;
        }
        return result;
    }

    public static void main(String[] args) {

        RSA rsa = new RSA(100403  , 101089  );
        long[] publicKey = rsa.getPublicKey();
        long[] privateKey = rsa.getPrivateKey();
        long secretMessage = 15;
        long message = rsa.encrypt(secretMessage, publicKey);
        long decrypted = rsa.decrypt(message, privateKey);

        long d;
        for (int i = 1; ; ++i){
            if (rsa.decrypt(message, new long[]{i, publicKey[1]}) == secretMessage){
                d = i;
                break;
            }
        }

        System.out.println("Secret message: " + secretMessage);
        System.out.println("Encrypted message: " + message);
        System.out.println("Decrypted message: " + decrypted);
        System.out.println("\nPublic key: " + Arrays.toString(publicKey));
        System.out.println("d = " + d);
        System.out.println("\nPrivate key: " + Arrays.toString(privateKey));
    }
}




