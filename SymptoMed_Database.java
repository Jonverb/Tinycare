package javaapplication1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import javax.swing.JOptionPane;


public class SymptoMed_Database {
    public static void main(String[] args) {
                 
        Scanner input = new Scanner(System.in); // For user input
        
  
      int answer = JOptionPane.showConfirmDialog(null, "Do you need assistance with your infant child?", "SymptoMed Finder", JOptionPane.YES_NO_CANCEL_OPTION);
      
            //CONDITION FOR YES OR NO STATEMENT
      if (answer == 1)
      {
         JOptionPane.showMessageDialog(null, "Feel free to ask us anytime for any concern regarding your infant child.");
            System.exit(0);
      }
          else if (answer == 2)
              {
                   JOptionPane.showMessageDialog(null, "Feel free to ask us anytime for any concern regarding your infant child.");
            System.exit(0);
              }
            else if (answer == -1)
              {
                  JOptionPane.showMessageDialog(null, "Feel free to ask us anytime for any concern regarding your infant child.");
            System.exit(0);
              }
        
            // USER INPUT IN JOPTIONPANE INPUT DIALOG        
     String userInput = JOptionPane.showInputDialog(null, "Enter the symptoms (Ex.: fever, cough, headache): ", "SymptoMed Finder", JOptionPane.QUESTION_MESSAGE);
      String[] userSymptoms = userInput.toLowerCase().split(",\\s*"); // Convert input to lowercase and split
        
       
      
            // DATABASE SRC
       String filePath = "C:\\Users\\Andrew\\OneDrive\\AutoSave\\NetBeansProjects\\JavaApplication1\\build\\classes\\javaapplication1\\SymptoMed_Database - Sheet1.csv"; // Update with your CSV file path
       BufferedReader br = null; // BufferedReader for reading the CSV file
        Map<String, Double> illnessMatchPercentage = new HashMap<String, Double>(); // Map to store illnesses and match percentage
        Map<String, String[]> illnessDetails = new HashMap<String, String[]>(); // Map to store details for each illness
        
        try {
            br = new BufferedReader(new FileReader(filePath)); // To reference the csv file for reading
            String line;
            
            while ((line = br.readLine()) != null) {
                String[] data = line.split(","); // Delimiter for the data fields in the text
                if (data.length >= 5) { // Ensure the array has enough elements
                    String[] csvSymptoms = data[0].toLowerCase().split(";"); // Symptoms from CSV
                    String illness = data[1];
                    String medication = data[2];
                    String instruction = data[3];
                    String reminder = data[4];
                    
                    int matchCount = 0;
                    for (String csvSymptom : csvSymptoms) { // Count matches between user symptoms and CSV symptoms
                        for (String userSymptom : userSymptoms) {
                            if (csvSymptom.trim().equals(userSymptom.trim())) {
                                matchCount++;
                            }
                        }
                    }
                    
                    if (matchCount > 0) {
                        double matchPercentage = (double) matchCount / csvSymptoms.length * 100;
                        illnessMatchPercentage.put(illness, matchPercentage);
                        illnessDetails.put(illness, new String[]{medication, instruction, reminder});
                    }
                }
            }

            // Sort illnesses by match percentage in descending order
            List<Map.Entry<String, Double>> sortedEntries = new ArrayList<Map.Entry<String, Double>>(illnessMatchPercentage.entrySet());
            Collections.sort(sortedEntries, new Comparator<Map.Entry<String, Double>>() {
                public int compare(Map.Entry<String, Double> e1, Map.Entry<String, Double> e2) {
                    return e2.getValue().compareTo(e1.getValue()); // Sort by match percentage descending
                }
            });
            

            if (!sortedEntries.isEmpty()) {
                for (Map.Entry<String, Double> entry : sortedEntries) {
                    String illness = entry.getKey();
                    double matchPercentage = entry.getValue();
                    String[] details = illnessDetails.get(illness);

                    
            // DISPLAYS THE OUTPUT UPON ENTERING THE ILLNESS IN JOPTIONPANE        
                StringBuilder resultMessage = new StringBuilder();
resultMessage.append("Illness: ").append(illness)
             .append(" (").append(String.format("%.2f", matchPercentage)).append("% match)\n")
             .append("Recommended Medication/Treatment: ").append(details[0]).append("\n")
             .append("Instruction: ").append(details[1]).append("\n")
             .append("Reminder: ").append(details[2]).append("\n");

// Display all information in one JOptionPane
JOptionPane.showMessageDialog(null, resultMessage.toString(), "Results", JOptionPane.INFORMATION_MESSAGE);
                  

            // OUTPUT DISPLAY IN NETBEANS w/o JOPTIONPANE
                    System.out.println("Illness: " + illness + " (" + String.format("%.2f", matchPercentage) + "% match)");
                    System.out.println("Recommended Medication/Treatment: " + details[0]);
                    System.out.println("Instruction: " + details[1]);
                    System.out.println("Reminder: " + details[2]);
                }
            } 
         
            // CONDITION IF THE INPUT DIALOG IS BLANK AND WRONG ILLNESS IS PROVIDED
            else 
            {
                JOptionPane.showMessageDialog(null, "No specific illness identified. Please consult a healthcare professional for further advice.");
            }
           
            
        } catch (IOException e) {
            e.printStackTrace(); // To handle file reading errors
        } finally {
            // Ensure the BufferedReader is closed to free resources
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}



