package com.eima.contacts;

//JSON is a serializer/deserializer for data text files
//https://code.google.com/archive/p/json-simple/
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Contact {

    private String name;
    private String email;
    private String phoneNumber;

    Contact(String name, String email, String phoneNumber)
    {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }


    public String getName() {
        return name;
    }

    //Writes Contact class ArrayList to Json file
    static void WriteJsonFile(String filename, ArrayList<Contact> contacts)
    {
        JSONObject root = new JSONObject();
        JSONArray jsonContactsArray = new JSONArray();
        try
        {
            for (int i = 0; i < contacts.size(); i++)
            {
                jsonContactsArray.add(CreateJsonObject(contacts.get(i)));
            }
            root.put("root", jsonContactsArray );
        }
        catch (NumberFormatException e)
        {
            e.printStackTrace();
        }

        try (FileWriter file = new FileWriter(filename))
        {
            file.write(root.toJSONString());
            file.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    //Reads Json file strings and outputs a Contact class ArrayList
    static ArrayList<Contact> ReadJsonFile(String filename)
    {
        File file = new File(filename);
        StringBuilder fileContents = new StringBuilder((int) file.length());

        String rawData = null;

        try (Scanner scanner = new Scanner(file))
        {
            while (scanner.hasNextLine())
            {
                fileContents.append(scanner.nextLine()).append(System.lineSeparator());
            }
            rawData = fileContents.toString();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        JSONObject data;
        try
        {
            JSONParser helper = new JSONParser();
            data = (JSONObject) helper.parse(rawData);
        }
        catch (ParseException parse)
        {
            System.out.println(parse);
            return null;
        }

        // Note that these may throw several exceptions
        JSONArray jsonArray = (JSONArray) data.get("root");
        int length = jsonArray.size();
        ArrayList<Contact> parsedData = new ArrayList<>(length);
        for (int i = 0; i < length; i++)
        {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            parsedData.add(JsonToClassParser(jsonObject));
        }

        return parsedData;
    }

    //Parses Contact class data to Json string
    private static JSONObject CreateJsonObject(Contact ContactData)
    {
        JSONObject Contact = new JSONObject();
        Contact.put("name", ContactData.name);
        Contact.put("email", ContactData.email);
        Contact.put("phoneNumber", ContactData.phoneNumber);
        return Contact;
    }

    //Parse Json string values to Contact class values
    private static Contact JsonToClassParser(JSONObject jsonObject)
    {
        return new Contact(jsonObject.get("name").toString(), jsonObject.get("email").toString(), jsonObject.get("phoneNumber").toString());
    }

    String getEmail() {
        return email;
    }

    String getPhoneNumber() {
        return phoneNumber;
    }
}
