package org.example;
import java.util.Scanner;

public class App {

    public void run() {
       Scanner sc = new Scanner(System.in);
        System.out.println("==실행==");
      while(true){
          System.out.println("명령어)");
          String cmd = new Scanner(System.in).nextLine();
          if(cmd.equals("exit")) {
              break;
          }else if(cmd.length() == 0){
              System.out.println("제대로 입력하세요");
              continue;
          }else if(cmd.equals("article write")) {
              System.out.print("motivation: ");
              String motivation = sc.nextLine();

              System.out.println();


          }else if (cmd.equals("article list")) {
             
          }
      }

        System.out.println("===프로그램 끝==="); }


}
