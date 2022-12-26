package com.example.scientificcalculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button b0;
    Button b1;
    Button b2;
    Button b3;
    Button b4;
    Button b5;
    Button b6;
    Button b7;
    Button b8;
    Button b9;
    Button bpi;
    Button bdot;
    Button bequel;
    Button bsum;
    Button bminus;
    Button bmul;
    Button bdiv;
    Button bsin;
    Button bcos;
    Button btan;
    Button blog;
    Button bin;
    Button bfact;
    Button bsq;
    Button broot;
    Button bac;
    Button bc;
    Button bclon;
    Button bclon1;

    String pi = "3.14159265";

    TextView tvMain;
    TextView tvSecond;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b0 = findViewById(R.id.b0);
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        b4 = findViewById(R.id.b4);
        b5 = findViewById(R.id.b5);
        b6 = findViewById(R.id.b6);
        b7 = findViewById(R.id.b7);
        b8 = findViewById(R.id.b8);
        b9 = findViewById(R.id.b9);
        bpi = findViewById(R.id.bpi);
        bdot = findViewById(R.id.bdot);


        bequel = findViewById(R.id.bequel);
        bsum = findViewById(R.id.bsum);
        bmul = findViewById(R.id.bmul);
        bminus = findViewById(R.id.bminus);
        bdiv = findViewById(R.id.bdiv);

        bac = findViewById(R.id.bac);
        bc = findViewById(R.id.bc);
        bclon = findViewById(R.id.bk1);
        bclon1 = findViewById(R.id.bk2);

        bsin = findViewById(R.id.bsin);
        bcos = findViewById(R.id.bcos);
        btan = findViewById(R.id.btan);
        blog = findViewById(R.id.blog);
        bin = findViewById(R.id.bIn);

        bfact = findViewById(R.id.bfact);
        bsq = findViewById(R.id.bsqure);
        broot = findViewById(R.id.broot);

        tvMain = findViewById(R.id.tvMain);
        tvSecond = findViewById(R.id.tvSecond);


        //onCLick Listeners
        b1.setOnClickListener(view -> tvMain.setText(tvMain.getText() + "1"));
        b2.setOnClickListener(view -> tvMain.setText(tvMain.getText() + "2"));
        b3.setOnClickListener(view -> tvMain.setText(tvMain.getText() + "3"));
        b4.setOnClickListener(view -> tvMain.setText(tvMain.getText() + "4"));
        b5.setOnClickListener(view -> tvMain.setText(tvMain.getText() + "5"));
        b6.setOnClickListener(view -> tvMain.setText(tvMain.getText() + "6"));
        b7.setOnClickListener(view -> tvMain.setText(tvMain.getText() + "7"));
        b8.setOnClickListener(view -> tvMain.setText(tvMain.getText() + "8"));
        b9.setOnClickListener(view -> tvMain.setText(tvMain.getText() + "9"));
        b0.setOnClickListener(view -> tvMain.setText(tvMain.getText() + "0"));
        bdot.setOnClickListener(view -> tvMain.setText(tvMain.getText() + "."));
        bclon.setOnClickListener(view -> tvMain.setText(tvMain.getText() + "("));
        bclon1.setOnClickListener(view -> tvMain.setText(tvMain.getText() + ")"));
        bsum.setOnClickListener(view ->  tvMain.setText(tvMain.getText()+ "+"));
        bminus.setOnClickListener(view -> tvMain.setText(tvMain.getText()+"-"));
        bdiv.setOnClickListener(view -> tvMain.setText(tvMain.getText()+"÷"));
        bmul.setOnClickListener(view -> tvMain.setText(tvMain.getText()+"×"));
        bsin.setOnClickListener(view -> tvMain.setText(tvMain.getText() + "sin"));
        bcos.setOnClickListener(view -> tvMain.setText(tvMain.getText() + "cos"));
        btan.setOnClickListener(view -> tvMain.setText(tvMain.getText() + "tan"));
        bin.setOnClickListener(view -> tvMain.setText(tvMain.getText() + "^"+"(-1)"));

        bc.setOnClickListener(view -> {
            String val = tvMain.getText().toString();
            if(!val.isEmpty()){
                val = val.substring(0, val.length() - 1);
                tvMain.setText(val);
            }else {
                bc.setEnabled(false);
            }
        });

        bac.setOnClickListener(view -> {
            tvMain.setText("");
            tvSecond.setText("");
        });


        broot.setOnClickListener(view -> {
                String val = tvMain.getText().toString();
                if(!val.isEmpty()){
                    double r = Math.sqrt(Double.parseDouble(val));
                    tvMain.setText(String.valueOf(r));
                }else {
                    bsq.setEnabled(false);
                }
        });

        bpi.setOnClickListener(view -> {
            tvSecond.setText(bpi.getText());
            tvMain.setText(tvMain.getText() + pi);
        });


        bfact.setOnClickListener(view -> {
            int val = Integer.parseInt(tvMain.getText().toString());
            int fact = factorial(val);
            tvMain.setText(String.valueOf(fact));
            tvSecond.setText(val+"!");
        });

        bsq.setOnClickListener(view -> {
            double d = Double.parseDouble(tvMain.getText().toString());
            double sq = d*d;
            tvMain.setText(String.valueOf(sq));
            tvSecond.setText(d+"²");
        });




        bequel.setOnClickListener(view -> {
            String val = tvMain.getText().toString();
            String replacedstr = val.replace('÷', '/').replace('×','*');
            double result = eval(replacedstr);
            tvMain.setText(String.valueOf(result));
            tvSecond.setText(val);
        });

    }

    //Function
    int factorial(int n) {
        return (n==1 || n==0) ? 1 : n*factorial(n-1);
    }

    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    switch (func) {
                        case "sqrt":
                            x = Math.sqrt(x);
                            break;
                        case "sin":
                            x = Math.sin(Math.toRadians(x));
                            break;
                        case "cos":
                            x = Math.cos(Math.toRadians(x));
                            break;
                        case "tan":
                            x = Math.tan(Math.toRadians(x));
                            break;
                        case "log":
                            x = Math.log10(x);
                            break;
                        case "ln":
                            x = Math.log(x);
                            break;
                        default:
                            throw new RuntimeException("Unknown function: " + func);
                    }
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();

}

}
