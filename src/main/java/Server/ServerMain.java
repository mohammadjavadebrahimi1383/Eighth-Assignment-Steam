package Server;

import Shared.Request;

import java.net.ServerSocket;
import java.net.Socket;

import java.io.*;

import java.sql.*;

public class ServerMain {
    public static void main(String[] args) throws IOException{

        try {

            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/steam", "postgres", "in the heights");
            Statement statement = connection.createStatement();

            //statement.execute("CREATE TABLE PUBLIC.user_pass(users text , pass text );" );
            //statement.execute("insert into user_pass (users , pass) values ('navid' , '1402' );" );

            //statement.execute("CREATE TABLE PUBLIC.games(ID text ,  title text , developer text , genre text , price double precision , year integer , controller_support boolean , reviews integer , size integer);" );
            //statement.execute("insert into games values ('292030' , 'the witcher 3:wild hunt' ,'cd projekt red', 'Role-playing' , 29.99 , 2015 , true , 96 , 798 );" );
            //statement.execute("insert into games values ('1174180' , 'RED DEAD ' ,'rockstar' , 'action_adventure' , 59.99 , 2018 , true , 90 , 1098 );" );

            ServerSocket server = new ServerSocket(8888);
            Socket s = server.accept();

            DataInputStream in=new DataInputStream(s.getInputStream());
            DataOutputStream out=new DataOutputStream(s.getOutputStream());

            while (true) {

                String command = in.readUTF();

                if (command.equals("sign up")) {

                    statement.execute("insert into user_pass values ('" + in.readUTF() + "' , '" + in.readUTF() + "' );");

                } else if (command.equals("sign in")) {

                    ResultSet r = statement.executeQuery("select * from user_pass where users = '" + in.readUTF() + "';");
                    r.next();

                    if (in.readUTF().equals(r.getString("pass"))) {
                        out.writeUTF("T");
                        out.flush();

                        if (in.readUTF().equals("games")){

                            //ResultSetMetaData rm = r.getMetaData(); int Nc= rm.getColumnCount();
                            ResultSet rm = statement.executeQuery("SELECT COUNT(*) AS row_count FROM games");
                            rm.next();
                            out.write(rm.getInt("row_count"));

                            r = statement.executeQuery("select * from games ;");

                            while(r.next()){
                                out.writeUTF(r.getString("title"));
                                out.flush();
                                out.writeUTF(r.getString("developer"));
                                out.flush();
                                out.writeUTF(r.getString("genre"));
                                out.flush();
                                out.writeDouble(r.getDouble("price"));
                                out.flush();
                                out.write(r.getInt("year"));
                                out.flush();
                                out.writeBoolean(r.getBoolean("controller_support"));
                                out.flush();
                                out.write(r.getInt("reviews"));
                                out.flush();
                                out.write(r.getInt("size"));
                                out.flush();
                            }


                        }

                    } else {
                        out.writeUTF("F");
                        out.flush();
                    }



                }
                else if(command.equals("turn off")){
                    break;
                }

            }

            s.close();
            server.close();

            statement.close();
            connection.close();

        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }
}