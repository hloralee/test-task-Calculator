import java.util.*;

public class Main {
    Map<String, String> romanMap;
    List<String> romanList = List.of("I", "II", "III", "IV", "V",
            "VI", "VII", "VIII", "IX", "X");

    public static void main(String[] args){
        try(Scanner scanner = new Scanner(System.in)) {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.print("Введите выражение: ");
                if (scanner.hasNextLine()) {
                    System.out.println("Ответ: " + calc(scanner.nextLine()));
                }
            }
        } catch (Exception ex) {
            System.err.println("throws Exception: " + ex.getMessage());
        }
    }
    /*
    Инициализация карты romanMap
     */
    void initRomanNum(){
        romanMap = new HashMap<>();

        for(int i = 1; i <= 10; i++) {
            romanMap.put(romanList.get(i - 1), String.valueOf(i));
        }
    }
    /*
        Вычисление введенного выражения
    */
    public static String calc(String input) throws Exception {
        Main main = new Main();
        String[] digit;

        main.initRomanNum();
        digit = main.splitInput(input);
        main.checkDigit(digit, main);

        return main.returnNum(digit, main);
    }
    /*
        Получение двух чисел и арифметическую операцию в массив String[] retSplit
    */
    String[] splitInput(String input) throws Exception{
        String[] arithmeticIndex = {"+", "-", "*", "/"};
        String[] retSplit = new String[3];
        String[] tmp;

        for(int i = 0; i < arithmeticIndex.length; i++) {
            if (input.contains(arithmeticIndex[i])) {
                retSplit[1] = new String(arithmeticIndex[i]);
                break;
            }
        }
        tmp = input.split("[" + retSplit[1] + "]");
        if (retSplit[1] == null || tmp.length != 2) {
            throw new Exception("Wrong expression");
        }
        retSplit[0] = tmp[0].trim();
        retSplit[2] = tmp[1].trim();

        return retSplit;
    }
    /*
        Проверка полученных чисел на соответствие требованиям задачи
    */
    void checkDigit(String[] digit, Main main) throws Exception{
        if (main.romanMap.containsKey(digit[0])) {
            if (!main.romanMap.containsKey(digit[2])) {
                throw new Exception("Wrong number");
            }
        } else if (main.romanMap.containsValue(digit[0])) {
            if (!main.romanMap.containsValue(digit[2])) {
                throw new Exception("Wrong number");
            }
        } else {
            throw new Exception("Wrong expression");
        }
    }
    /*
        Преобразование полученных чисел в int и вычисление результата
    */
    String returnNum(String[] digit, Main main) throws Exception{
        int a, b;
        boolean isRoman = false;

        if (main.romanMap.containsKey(digit[0])) {
            a = Integer.parseInt(main.romanMap.get(digit[0]));
            b = Integer.parseInt(main.romanMap.get(digit[2]));
            isRoman = true;
        } else {
            a = Integer.parseInt(digit[0]);
            b = Integer.parseInt(digit[2]);
        }

        return calculation(a, b, digit, isRoman);
    }
    /*
        Получение результата арифметичекого действия
    */
    String calculation(int a, int b, String[] digit, boolean isRoman) throws Exception{
        int c;
        String retValue;

        if (digit[1].equals("+")) {
            c = a + b;
        } else if (digit[1].equals("-")) {
            c = a - b;
        } else if (digit[1].equals("*")) {
            c = a * b;
        } else {
            c = a / b;
        }
        retValue = String.valueOf(c);

        if (isRoman) {
            if (c < 1) {
                throw new Exception("Negative roman number");
            }
            retValue = toRoman(c);
        }

        return retValue;
    }
    /*
        Конвертирование арабского числа в римское
    */
    String toRoman(int c) {
        List<Integer> listNum = List.of(100, 90, 50, 40, 10, 9, 5, 4, 1);
        List<String> listSymbol = List.of("C", "XC", "L", "XL", "X", "IX", "V", "IV", "I");
        String result = "";

        for(int i = 0; i < listNum.size(); i++) {
            while (c >= listNum.get(i)) {
                result += listSymbol.get(i);
                c -= listNum.get(i);
            }
        }
        return result;
    }
}
