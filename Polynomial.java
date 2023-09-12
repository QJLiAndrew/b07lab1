public class Polynomial {
    double[] coefficients;

    private static double power(double a, int b){
        double res = 1;
        for(int i=1;i<=b;++i){
            res = res * a;
        }
        return res;
    }

    public Polynomial(){
        this.coefficients = new double[1005];
        for (int i = 0; i < this.coefficients.length; i++) {
            this.coefficients[i] = 0;
        }
    }


    public Polynomial(double[] coefficients){
        this.coefficients = coefficients.clone();
    }


    public Polynomial add(Polynomial a){
        int length = Math.max(this.coefficients.length, a.coefficients.length);
        double[] new_polynomial = new double[length];
        for(int i=0;i<length;++i){
            new_polynomial[i] = 0;
            if(i >= length){
                continue;
            }
            if(i < this.coefficients.length){
                new_polynomial[i] += this.coefficients[i];
            }
            if(i < a.coefficients.length){
                new_polynomial[i] += a.coefficients[i];
            }
        }
        return new Polynomial(new_polynomial);
    }


    public double evaluate(double x){
        double res = 0, t = 0;
        for(int i=0;i<this.coefficients.length;++i){
            if(this.coefficients[i] == 0)
                continue;
            t = power(x, i);
            res += this.coefficients[i] * t;
        }
        return res;
    }


    public Boolean hasRoot(double x){
        if(this.evaluate(x) == 0){
            return true;
        }
        return false;
    }
}