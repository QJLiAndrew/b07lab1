import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;


public class Polynomial {
    double[] coefficients;
    int[] degrees;
    private static double power(double a, int b){
        double res = 1;
        for(int i=1;i<=b;++i){
            res = res * a;
        }
        return res;
    }


    private static String ReadFile(File f) throws Exception{
        BufferedReader b = new BufferedReader(new FileReader(f));
        String line = b.readLine();
        b.close();
        return line;
    }


    public Polynomial(){
        this.coefficients = new double[10005];
        this.degrees = new int[10005];
        for (int i = 0; i < this.coefficients.length; i++) {
            this.coefficients[i] = 0;
        }
        for (int i = 0; i < this.degrees.length; i++){
            this.degrees[i] = 0;
        }
    }


    public Polynomial(double[] coefficients, int[] degrees){
        this.coefficients = coefficients.clone();
        this.degrees = degrees.clone();
    }


    public Polynomial(File f){
        try{
            String raw_data = ReadFile(f);
            String[] data = raw_data.split("\\+|-");
            int[] degrees = new int[data.length];
            double[] coefficients = new double[data.length];
            int t = 0, index = 0, degree = 0;
            double coefficient = 0;
            for(int i=0;i<data.length;++i){
                coefficient = 0;
                degree = 0;
                if(data[i].contains("x")){
                    if(data[i].length() == 1){
                        coefficient = 1;
                        degree = 1;
                    }else{
                        String[] s = data[i].split("x");
                        coefficient = 0;
                        if(data[i].indexOf("x") == 0){
                            coefficient = 1;
                            degree = Integer.parseInt(s[1]);
                        }else if(data[i].indexOf("x") == data[i].length() - 1){
                            coefficient = Double.parseDouble(s[0]);
                            degree = 1;
                        }else{
                            coefficient = Double.parseDouble(s[0]);
                            if(s.length == 1){
                                degree = 1;
                            }else{
                                degree = Integer.parseInt(s[1]);
                            }
                        }
                    }
                    
                }else{
                    degree = 0;
                    coefficient = Double.parseDouble(data[i]);
                }
                if(coefficient != 0){
                    index = raw_data.indexOf(data[i]);
                    if(index > 0){
                        if(raw_data.charAt(index - 1) == '-'){
                            coefficient *= -1;
                        }
                    }
                    coefficients[t] = coefficient;
                    degrees[t] = degree;
                    t += 1;
                    
                }
            }
            this.coefficients = new double[coefficients.length];
            this.degrees = new int[degrees.length];
            for(int i=0;i<t;++i){
                if(coefficients[i] != 0){
                    this.coefficients[i] = coefficients[i];
                    this.degrees[i] = degrees[i];
                }
            }
        }catch(Exception e){
            System.out.println("failed on reading from file!");
        }
    }


    public Polynomial add(Polynomial a){
        int length = Math.max(this.coefficients.length, a.coefficients.length);
        double[] new_coefficients = new double[length];
        int[] new_degrees = new int[length];
        int j=0, k=0, n=0, flag = 0, sum=0;
        for(int i=0;j<this.degrees.length || k<a.degrees.length;++i){
            flag = 0;
            sum=0;
            if(j<this.degrees.length){
                if(i == this.degrees[j]){
                    sum += this.coefficients[j];
                    j++;
                    flag = 1;
                }
            }
            if(k<a.degrees.length){
                if(i == a.degrees[k]){
                    sum += a.coefficients[k];
                    k++;
                    flag = 1;
                }
            }
            if(flag == 1&&sum != 0){
                new_coefficients[n] = sum;
                new_degrees[n] = i;
                n += 1;
            }
        }
        // double[] coefficients = new double[n];
        // int[] degrees = new int[n];
        // int t=0;
        // for(int i=0;i<10005;++i){
        //     if(new_coefficients[i])
        // }
        Polynomial new_Polynomial = new Polynomial(new_coefficients, new_degrees);
        return new_Polynomial;
    }


    public double evaluate(double x){
        double res = 0, t = 0;
        for(int i=0;i<this.degrees.length;++i){
            t = power(x, this.degrees[i]);
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


    public Polynomial multiply(Polynomial a){
        double[] res = new double[20011];
        int[] flag = new int[20011];
        for(int i=0;i<10005;++i){
            res[i] = 0;
        }
        int t=0, new_length = 0;
        for(int i=0;i<this.degrees.length;++i){
            for(int j=0;j<a.degrees.length;++j){
                t = this.degrees[i] + a.degrees[j];
                if(flag[t] == 0){
                    new_length += 1;
                    flag[t] = 1;
                }
                res[t] += this.coefficients[i] * a.coefficients[j];
                if(res[t] == 0){
                    new_length -= 1;
                    flag[t] = 0;
                }
            }
        }
        double[] new_coefficients = new double[new_length];
        int[] new_degrees = new int[new_length];
        t = 0;
        for(int i=0;i<20011;++i){
            if(flag[i] == 1){
                new_degrees[t] = i;
                new_coefficients[t] = res[i];
                t += 1;
            }
        }
        Polynomial new_Polynomial = new Polynomial(new_coefficients, new_degrees);
        return new_Polynomial;
    }


    public void saveToFile(String filepath){
        try{
            PrintStream ps = new PrintStream(filepath);
            String output = "";
            String t = "";
            for(int i=0;i<this.degrees.length;++i){
                t = "";
                if(this.coefficients[i] != 0){
                    if(this.coefficients[i] != 1&&this.coefficients[i] != -1){
                        t = Double.toString(this.coefficients[i]);
                    }
                    if(this.degrees[i] != 0){
                        t += "x";
                        if(this.degrees[i] != 1){
                            t += Integer.toString(this.degrees[i]);
                        }
                    }
                    if(i != 0){
                        if(this.coefficients[i] > 0){
                            output += "+";
                        }else{
                            output += "-";
                        }
                    }
                }
                output += t;
            }
            ps.println(output);
            ps.close();
        }catch(Exception e){
            System.out.println("failed on saving!");
        }
    }
}