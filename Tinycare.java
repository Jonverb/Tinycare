package javaapplication1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import javax.swing.JOptionPane;

public class Tinycare 
{
    public static void main(String[] args) 
    {
        // Disclaimer Message
         String disclaimer = "DISCLAIMER:\n"
                + "This program acknowledges the laws under the Constitution of the Philippines that prohibit\n" 
                + "individuals who are not licensed physicians from diagnosing, prescribing, or treating illnesses.\n\n"
                + "This program is intended solely for first-aid purposes, designed to assist users who may not have the financial means\n" 
                + "or access to hospitals for check-ups.\n\n" 
                + "Rest assured that the data used by this program has been thoroughly researched by the team and verified by medical professionals.\n\n"
                + "Relevant Laws:\n"
                + "- Republic Act No. 2382 (The Medical Act of 1959): Prohibits the practice of medicine by unlicensed individuals.\n"
                + "- Republic Act No. 7394 (The Consumer Act of the Philippines): Protects consumers from deceptive practices.\n"
                + "- Republic Act No. 10354 (Responsible Parenthood and Reproductive Health Act of 2012): Provisions against unlicensed individuals\n"
                + "providing health-related advice.\n"
                + "- Republic Act No. 10918 (Philippine Pharmacy Act): Restricts unqualified individuals from prescribing medications.\n\n"
                + "Users are strongly advised to consult a licensed medical professional\n"
                + "for proper diagnosis and treatment.\n\n"
                + "By using this program, you acknowledge that the authors are not liable\n"
                + "for any harm, damage, or consequences arising from its use.";
        JOptionPane.showMessageDialog(null, disclaimer, "Program Disclaimer", JOptionPane.INFORMATION_MESSAGE);
        
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
       String filePath = "C:\\Users\\Yjareb\\OneDrive\\Documents\\NetBeansProjects\\JavaApplication1\\SymptoMed_Database - Sheet1.csv"; // Update with your CSV file path
       BufferedReader br = null; // BufferedReader for reading the CSV file
        Map<String, Double> illnessMatchPercentage = new HashMap<String, Double>(); // Map to store illnesses and match percentage
        Map<String, String[]> illnessDetails = new HashMap<String, String[]>(); // Map to store details for each illness
        
        try {
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
                    for (String csvSymptom : csvSymptoms) { // Count matches between user symptoms and CSV symptoms
                        for (String userSymptom : userSymptoms) {
                            if (csvSymptom.trim().equals(userSymptom.trim())) {
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
                    System.out.println("---------------------");
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

// This program acknowledges the laws under the Constitution of the Philippines that prohibit 
// individuals who are not licensed physicians from diagnosing, prescribing, or treating illnesses.

// This program is intended solely for first-aid purposes, designed to assist users who may not have the financial means or access to hospitals for check-ups. 
// Rest assured that the data used by this program has been thoroughly researched by the team and verified by medical professionals.

//----------------------------------------------------------------------------------------------------------------------------------------------------//
//Republic Act No. 2382 (Medical Act of 1959)
//- This law governs the practice of medicine in the Philippines.
//- Section 28 explicitly prohibits individuals who are not licensed physicians from diagnosing, prescribing, or treating illnesses.
//- Penalties for violations include fines and imprisonment.

//Republic Act No. 7394 (Consumer Act of the Philippines)
//- Under this act, false, misleading, or unauthorized claims related to health or medical advice can lead to legal consequences.
//- It protects consumers from potential harm caused by unqualified individuals providing medical opinions.

//Republic Act No. 10354 (Responsible Parenthood and Reproductive Health Act of 2012)
//- This act includes provisions against unlicensed individuals providing health-related advice, especially concerning reproductive health.

//Republic Act No. 10918 (Philippine Pharmacy Act)
//- It restricts unqualified individuals from prescribing medications, which is often tied to a diagnosis.
//-----------------------------------------------------------------------------------------------------------------------------------------------------//

// Team Members:
// Alonzo, Jonreb C.
// Ballesteros, Fernando Jr. M.
// Borlongan, Nichole Christine D. 
// Chao, John Christian A.
// Lawrence, Allen Lorenzo S.
// Navilla, Saul Y. 
// Pasadilla, Seanna D.
// 2ITSE01 
// (ITCC131/ITCC131LAB - Data Structures & Algorithms (Lec) (Lab)) Mr. Marc Raphael S. Piñero
// (ITPC131/ITPC131LAB - Integrative Programming and Technologies 1 (Lec) (Lab)) Mrs. Suzette C. Crisostomo 
