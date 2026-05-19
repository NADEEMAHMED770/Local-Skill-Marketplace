import java.util.ArrayList;
import java.util.List;

public class RecommendationEngine {

    // Simple keyword-matching algorithm
    public static List<Skill> findMatchesForRequest(String skillNeeded) {
        List<Skill> allSkills = DatabaseManager.getAllSkills();
        List<Skill> matchedSkills = new ArrayList<>();
        
        // Convert the request to lowercase and split it into individual words
        String[] keywords = skillNeeded.toLowerCase().split(" ");
        
        for (Skill skill : allSkills) {
            // Skip if the skill is currently marked as unavailable
            if (!skill.isAvailable()) continue;
            
            String skillName = skill.getSkillName().toLowerCase();
            String category = skill.getCategory().toLowerCase();
            
            for (String word : keywords) {
                // Ignore tiny words like "a", "an", "is", "to"
                if (word.length() > 2) {
                    // If the skill name or category contains the keyword, it's a match!
                    if (skillName.contains(word) || category.contains(word)) {
                        // Ensure we don't add the exact same skill twice to our list
                        if (!matchedSkills.contains(skill)) {
                            matchedSkills.add(skill);
                        }
                    }
                }
            }
        }
        return matchedSkills;
    }
}