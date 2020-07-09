package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Controller {

    @FXML
    private TextField sumTextField;

    @FXML
    private TextField arrayTextField;

    @FXML
    private TextArea outputTextArea;

     List<Pair<Integer,Integer>> solve(int[] arr, int sum){

         MyMap<Integer, List<Integer>> map = new MyMap<>();

         for(int i = 0; i < arr.length; i++){
             if(map.containsKey(arr[i])) {
                 map.get(arr[i]).add(i);
             }
             else {
                 List<Integer> new_list = new ArrayList<>();
                 new_list.add(i);
                 map.put(arr[i],new_list);
             }
         }

         ArrayList<Pair<Integer,Integer>> result = new ArrayList<>();

         for(int a : map.keySet()){
            int b = sum - a;

            List<Integer> firstIndexList = map.get(a);
            List<Integer> secondIndexList = map.get(b);

            if(secondIndexList != null){
                for(int firstIndex : firstIndexList){
                    for(int secondIndex : secondIndexList){
                        if(firstIndex > secondIndex){
                            result.add(new Pair<>(a,b));
                        }
                    }
                }
            }
        }
        return result;
    }



    @FXML
    private void run(){


        Integer sum;
        try{
            sum = Integer.parseInt(sumTextField.getText());
        }
        catch (Exception e){
            return;
        }

        Scanner scanner = new Scanner(arrayTextField.getText());

        ArrayList<Integer> list = new ArrayList<>();

        while (scanner.hasNextInt()) {
            Integer a = scanner.nextInt();
            list.add(a);
        }

        int[] arr = new int[list.size()];

        for(int i = 0; i < list.size(); i++){
            arr[i] = list.get(i);
        }

        List<Pair<Integer,Integer>> result = solve(arr, sum);

        for(Pair<Integer, Integer> pair : result){
            outputTextArea.appendText(Integer.toString(pair.getKey()));
            outputTextArea.appendText(" ");
            outputTextArea.appendText(Integer.toString(pair.getValue()));
            outputTextArea.appendText("\n");
        }

        outputTextArea.appendText("\n");
    }
}
