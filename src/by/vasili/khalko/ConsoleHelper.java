package by.vasili.khalko;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleHelper {

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message){
        System.out.println(message);
    }

    public static void wrongDataMessage(){
        writeMessage("invalid data entered. try again");
    }

    public static int readInt(){
        while (true) {
            try {
                String input = reader.readLine();
                if(input.matches("^\\d+$")){
                    return Integer.parseInt(input);
                } else {
                    wrongDataMessage();
                    continue;
                }
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    public static String readString() {
        String input = "";
        try {
             input = reader.readLine();
            return input;
        } catch (IOException e) {
            e.printStackTrace();
            return input;
        }
    }
}
