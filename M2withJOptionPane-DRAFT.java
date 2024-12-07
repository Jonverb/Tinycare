package javaapplication1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import javax.swing.JOptionPane;

public class M2 
{
    public static void main(String args[]) 
    {
        Scanner input = new Scanner(System.in); // For user input

        int answer = JOptionPane.showConfirmDialog(null, "Do you need assistance with your infant child?", "This is my Title", JOptionPane.YES_NO_CANCEL_OPTION);
      
      //CONDITION IN YES OR NO STATEMENT
      if (answer == 1)
      {
         System.out.println("Feel free to ask us anytime for any concern regarding your infant child.");
         System.exit(0);
      }
          else if (answer == 2)
              {
                  System.out.println("Feel free to ask us anytime for any concern regarding your infant child.");
                  System.exit(0);
              }
             else if (answer == -1)
              {
                  System.out.println("Feel free to ask us anytime for any concern regarding your infant child.");
                  System.exit(0);
              }
              
              
      String userInput;
      userInput = JOptionPane.showInputDialog("Enter the symptoms (Ex.: fever, cough, headache): ");
        
        
        System.out.println("Enter the symptoms (Ex.: fever, cough, headache): ");
        String[] userSymptoms = input.nextLine().toLowerCase().split(",\\s*"); // Convert input to lowercase and split

        String filePath = "C:\Users\Andrew\OneDrive\Desktop\DSA FINALS\SymptoMed_Database - Sheet1.csv"; // Update with your CSV file path
        
        BufferedReader br = null; // BufferedReader for reading the CSV file
        Map<String, Double> illnessMatchPercentage = new HashMap<String, Double>(); // Map to store illnesses and match percentage
        Map<String, String[]> illnessDetails = new HashMap<String, String[]>(); // Map to store details for each illness

        try 
        {
            br = new BufferedReader(new FileReader(filePath)); // To reference the csv file for reading
            String line;

            while ((line = br.readLine()) != null) 
            {
                String[] data = line.split(","); // Delimiter for the data fields in the text
                if (data.length >= 5) 
                { // Ensure the array has enough elements
                    String[] csvSymptoms = data[0].toLowerCase().split(";"); // Symptoms from CSV
                    String illness = data[1];
                    String medication = data[2];
                    String instruction = data[3];
                    String reminder = data[4];

                    int matchCount = 0;
                    for (String csvSymptom : csvSymptoms) 
                    { // Count matches between user symptoms and CSV symptoms
                        for (String userSymptom : userSymptoms) 
                        {
                            if (csvSymptom.trim().equals(userSymptom.trim())) 
                            {
                                matchCount++;
                            }
                        }
                    }

                    if (matchCount > 0) 
                    {
                        double matchPercentage = (double) matchCount / csvSymptoms.length * 100;
                        illnessMatchPercentage.put(illness, matchPercentage);
                        illnessDetails.put(illness, new String[]{medication, instruction, reminder});
                    }
                }
            }

            // Sort illnesses by match percentage in descending order
            List<Map.Entry<String, Double>> sortedEntries = new ArrayList<Map.Entry<String, Double>>(illnessMatchPercentage.entrySet());
            Collections.sort(sortedEntries, new Comparator<Map.Entry<String, Double>>() 
            {
                public int compare(Map.Entry<String, Double> e1, Map.Entry<String, Double> e2) 
                {
                    return e2.getValue().compareTo(e1.getValue()); // Sort by match percentage descending
                }
            });

            if (!sortedEntries.isEmpty()) 
            {
                for (Map.Entry<String, Double> entry : sortedEntries) 
                {
                    String illness = entry.getKey();
                    double matchPercentage = entry.getValue();
                    String[] details = illnessDetails.get(illness);

                    System.out.println("Illness: " + illness + " (" + String.format("%.2f", matchPercentage) + "% match)");
                    System.out.println("Recommended Medication/Treatment: " + details[0]);
                    System.out.println("Instruction: " + details[1]);
                    System.out.println("Reminder: " + details[2]);
                    System.out.println("---------------------");
                }
            } 
            else 
            {
                System.out.println("No specific illness identified. Please consult a healthcare professional for further advice.");
            }

        } 
        catch (IOException e) 
        {
            e.printStackTrace(); // To handle file reading errors
        } 
        finally 
        {
            // Ensure the BufferedReader is closed to free resources
            if (br != null) 
            {
                try 
                {
                    br.close();
                } 
                catch (IOException e) 
                {
                    e.printStackTrace();
                }
            }
        }
    }
}