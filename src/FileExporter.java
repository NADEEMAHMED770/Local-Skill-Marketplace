import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FileExporter {

    public static String exportReport() {
        File directory = new File("reports");
        if (!directory.exists()) {
            directory.mkdir();
        }

        LocalDateTime now = LocalDateTime.now();
        String dateOnly = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        
        // CHANGE 1: Save as .csv instead of .txt
        String filePath = "reports/skills_dataset_" + dateOnly + ".csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            
            List<Skill> skills = DatabaseManager.getAllSkills();

            // CHANGE 2: Write the CSV Column Headers
            writer.write("Skill_ID,Provider_Name,Category,Skill_Name,Price_Rs,Area,Is_Available\n");

            // CHANGE 3: Write the data separated by commas
            for (Skill s : skills) {
                Person p = DatabaseManager.getPersonById(s.getUserId());
                String providerName = (p != null) ? p.getName() : "Unknown";
                String area = (p != null) ? p.getArea() : "Unknown";
                
                // Format: 1,Ali Khan,Education,Math Tutoring,200.0,Sukkur,true
                writer.write(s.getSkillId() + "," + 
                             providerName + "," + 
                             s.getCategory() + "," + 
                             s.getSkillName() + "," + 
                             s.getPricePerHour() + "," + 
                             area + "," + 
                             s.isAvailable() + "\n");
            }

            return filePath; 

        } catch (IOException e) {
            System.out.println("Could not save CSV file: " + e.getMessage());
            return null; 
        }
    }
}