package ch.hsr.testing.unittest.parametrized;

public class PrimeChecker {
    public static boolean isPrime(int primeCandidate){
        for (int i = 2; i <= Math.sqrt(primeCandidate); i++){
            if (primeCandidate % i == 0){
                return false;
            }
        }
        return true;
    }
}
