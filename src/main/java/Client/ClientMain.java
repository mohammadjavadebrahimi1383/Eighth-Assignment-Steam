package Client;
import Shared.Request;

import java.io.*;

import java.net.Socket;

import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) throws IOException{
        try {
            Socket s = new Socket("localhost", 8888);

            DataInputStream in=new DataInputStream(s.getInputStream());
            DataOutputStream out=new DataOutputStream(s.getOutputStream());
            Scanner input = new Scanner(System.in);

            while (true) {
                System.out.println("sign up");
                System.out.println("sign in");
                System.out.println("turn off");

                String command = input.nextLine();

                out.writeUTF(command);
                out.flush();

                if (command.equals("sign up")) {

                    System.out.println("name: ");
                    String name = input.nextLine();
                    System.out.println("pass: ");
                    String pass = input.nextLine();

                    out.writeUTF(name);
                    out.writeUTF(pass);
                    out.flush();

                    System.out.println("done");


                } else if (command.equals("sign in")) {

                    System.out.println("name: ");
                    String name = input.nextLine();
                    System.out.println("pass: ");
                    String pass = input.nextLine();

                    out.writeUTF(name);
                    out.writeUTF(pass);
                    out.flush();

                    if (in.readUTF().equals("T")) {

                        System.out.println("games");
                        System.out.println("downloaded");
                        System.out.println("back");

                        command=input.nextLine();

                        out.writeUTF(command);
                        out.flush();

                        if(command.equals("games")){

                            int Nrow = in.read();

                            for(int i=0 ; i<Nrow ; i++){
                                System.out.println(in.readUTF());
                                System.out.print("developer: " + in.readUTF() + "  genre: "+ in.readUTF() + "  price: " + in.readDouble() + "  year: " + in.read() + "  controller_support: " + in.readBoolean() + "  reviews: " + in.read() + "  size: " + in.read());
                                System.out.println();
                                System.out.println();
                            }

                            System.out.println("inter game's name for download :");

                            command = input.nextLine();

                            // N.F
                            System.out.println("done");

                        }
                        else if(command.equals("downleaded")){

                        }

                    } else {
                        System.out.println("password was wrong");
                    }

                } else if(command.equals("turn off")){
                    break;
                }
            }

            s.close();
        }
        catch (Exception e){

        }
    }
}