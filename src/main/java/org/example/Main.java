package org.example;

import com.sun.net.httpserver.HttpServer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    static ArrayList<Child> childs = new ArrayList<>();
    static ArrayList<Parent> parents = new ArrayList<>();
    public static void main(String[] args) throws Exception {
        parse();
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/test", new Handler());
        server.setExecutor(null);
        server.start();
    }

    public static void parse() {
        try {
            JSONArray childsJson = fileToJson("Child.json").getJSONArray("data");
            JSONArray parentsJson = fileToJson("Parent.json").getJSONArray("data");

            for (int i = 0; i < parentsJson.length(); i++) {
                JSONObject parentJson = parentsJson.getJSONObject(i);

                parents.add(new Parent(
                        parentJson.getInt("id"),
                        parentJson.getString("sender"),
                        parentJson.getString("receiver"),
                        parentJson.getInt("totalAmount")
                ));
            }

            parents.sort(Comparator.comparingInt(o -> o.id));

            for (int i = 0; i < childsJson.length(); i++) {
                JSONObject childJson = childsJson.getJSONObject(i);
                Child child = new Child(
                        childJson.getInt("id"),
                        childJson.getInt("paidAmount")
                );

                for (Parent parent : parents) {
                    if (parent.id == childJson.getInt("parentId")) {
                        parent.childs.add(child);
                        child.parent = parent;
                        break;
                    }
                }

                childs.add(child);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JSONObject fileToJson(String path) throws FileNotFoundException {
        File jsonFile = new File(path);
        StringBuilder jsonString = new StringBuilder();
        Scanner scanner = new Scanner(jsonFile);

        while (scanner.hasNextLine()) {
            jsonString.append(scanner.nextLine());
        }

        scanner.close();
        return new JSONObject(jsonString.toString());
    }
}