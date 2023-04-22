import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;

public class CalculatorV3 {

    public static void main(String[] args) {
        String[] arrayOperation = {"+", "-", "/", "*"};
//        HashMap romeArabicNumber = romeMapArabic();


        String userExpression = receiveEnter();
        ArrayList<String> workExpression = formatExpression(userExpression, arrayOperation);

        if (workExpression.contains("Error")){
            System.out.println("throws Exception //т.к. формат математической операции не удовлетворяет заданию - " +
                    "два операнда и один оператор (+, -, /, *)");
            return;
        }
        if (workExpression.size() == 0){
            System.out.println("throws Exception //т.к. строка не является математической операцией");
            return;
        }

        if (workExpression.size() !=3) {
            System.out.println("throws Exception //т.к. формат математической операции не удовлетворяет заданию - " +
                    "два операнда и один оператор (+, -, /, *)");
            return;
        }

        // Check numbers are arabic or rome
        String operation = workExpression.get(1);
        workExpression.remove(1);

        boolean romeNumber = whatIsNumber(workExpression);

        String number1 = workExpression.get(0);
        String number2 = workExpression.get(1);
        Integer[] numbers = new Integer[2];

        if (romeNumber){
            numbers = translateRomeArabicNumber(number1, number2);
        } else {
            numbers[0] = Integer.parseInt(number1);
            numbers[1] = Integer.parseInt(number2);

        }

//        Integer[] numbers = whatIsNumber(workExpression, romeArabicNumber);

        int result = receiveResult(operation, numbers);
        if (result == 0){
            return;
        }

        String resultRome = "";
        if(romeNumber && result > 0){
            resultRome = arabicToRoman(result);
            System.out.println("Result Rome: " + resultRome);
        } else if (romeNumber && result < 0) {
            try {
                throw new ArithmeticException();
            } catch (ArithmeticException e) {
                System.out.println("throws Exception //т.к. в римской системе нет отрицательных чисел");
            }

        } else {
            System.out.println("Result Arabic: " + result);
        }



    }

    public static String arabicToRoman(int number) {

        String result = "";
        int[] values = {100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romanNumerals = {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int i = 0;

        //Here happens the main task
        while(i < values.length) {
            if(number >= values[i]) {
                result += romanNumerals[i];
                number = number - values[i];
            }
            else {
                i++;
            }
        }
        return result;
    }


    private static HashMap romeMapArabic(){
        HashMap<String, Integer> romeArabicNumbers = new HashMap<>();
        romeArabicNumbers.put("I", 1);
        romeArabicNumbers.put("II", 2);
        romeArabicNumbers.put("III", 3);
        romeArabicNumbers.put("IV", 4);
        romeArabicNumbers.put("V", 5);
        romeArabicNumbers.put("VI", 6);
        romeArabicNumbers.put("VII", 7);
        romeArabicNumbers.put("VIII", 8);
        romeArabicNumbers.put("IX", 9);
        romeArabicNumbers.put("X", 10);

        return romeArabicNumbers;
    }

    private static String receiveEnter() {
        System.out.print("Enter expression for calculation: ");
        Scanner exp = new Scanner(System.in);
        String userExpression = exp.nextLine();
        exp.close();
        userExpression = userExpression.trim();
        userExpression = userExpression.toUpperCase();

        return userExpression;
    }


    // Func create arrayList for continues work
    private static ArrayList<String> formatExpression(String userExpression, String[] arrayOperation) {
        String[] workExpression = {};
        // put ArrayList here
        ArrayList<String> workExpressionArray = new ArrayList<String>();
        String operation = "";

        if (userExpression.contains(" ")) {
//            System.out.println("String contains spaces.");
            workExpression = userExpression.split(" ");
            // Put element of workExpression to ArrayList
            for (String element : workExpression) {
                workExpressionArray.add(element);
            }
        } else {
            int checkElement = 0; // Check that operation happens one time
            for (String val : arrayOperation) {
                if (userExpression.contains(val) && checkElement == 0) {
//                    val = "\\%s".formatted(val);
                    workExpression = userExpression.split("\\%s".formatted(val));
                    operation = val;
                    checkElement++;

                    for(String element: workExpression){
                        workExpressionArray.add(element);
                    }
                    workExpressionArray.add(1, operation);

                } else if(userExpression.contains(val) && checkElement > 0) {
                    try {
                        throw new IOException("");
                    } catch (IOException e) {
                        workExpressionArray.add("Error");
                        break;
                    }
                }
            }
        }

        return workExpressionArray;
    }

    private static boolean whatIsNumber(ArrayList<String> workExpression) {
        boolean isArabicNumber = false;
        boolean isRomeNumber = false;

        for (String number : workExpression) {
            try {
                Integer.parseInt(number);
                isArabicNumber = true;


            } catch (NumberFormatException e) {
                isRomeNumber = true;
            }
        }

        // throws Exception //т.к. используются одновременно разные системы счисления
        if(isArabicNumber && isRomeNumber){
            try {
                throw new ArithmeticException();
            } catch (ArithmeticException e){
                System.out.println("throws Exception //т.к. используются одновременно разные системы счисления");
            }
        }

        if(isRomeNumber){
            return true;
        }
        return false;
    }

    private static Integer[] translateRomeArabicNumber(String number1, String number2){
        HashMap romeArabicNumber = romeMapArabic();
        int IntNumber1 = 0;
        int IntNumber2 = 0;
        Integer[] numbers = new Integer[2];
        if (romeArabicNumber.containsKey(number1) && romeArabicNumber.containsKey(number2)) {
            IntNumber1 = (int) romeArabicNumber.get(number1);
            IntNumber2 = (int) romeArabicNumber.get(number2);

        } else {
            // throws Exception //т.к. это не римские цифры
            try {
                throw new IOException();
            } catch (IOException e) {
                System.out.println("throws Exception //т.к. это не римские цифры");
            }
        }
        numbers[0] = IntNumber1;
        numbers[1] = IntNumber2;

        return numbers;
    }

    // Func Receive Result calculation
    private static int receiveResult(String operation, Integer[] numbers){
        int result = 0;
        int number1 = numbers[0];
        int number2 = numbers[1];
        if((0 < number1 && number1 < 11) && (0 <= number2 && number2 < 11)) {
            switch (operation) {
                case "+":
                    result = number1 + number2;
                    break;
                case "-":
                    result = number1 - number2;
                    break;
                case "*":
                    result = number1 * number2;
                    break;
                case "/":
                    result = number1 / number2;
            }
        } else {
            throw new ArithmeticException("Numbers must be from 1 to 10");
        }

        return result;
    }

}

