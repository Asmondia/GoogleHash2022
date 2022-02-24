
import java.util.ArrayList;

public class Project {
    String name;
    int days;
    int score;
    int bestBefore;
    ArrayList<Skill> roles;
    int daysWorkedOn;
    boolean isFinished = false;
    ArrayList<WorkingOn> woking;
    Project(String name, int days, int score, int endDay, ArrayList<Skill> roles) {
        this.name = name;
        this.days = days;
        this.bestBefore = endDay;
        this.roles = roles;

    }
    public int getLastestFullDay(){
        return bestBefore-days;
    }
    public boolean getComplete(){
        if (days == daysWorkedOn){
            return true;
        } else {
            return false;
        }
    }
    public int getScore(int currentDay){
        int scoreTwo;
        for (int i = 0; i < woking.size();i++){
            woking.get(i).contrib.isWorking = false;
        }
        this.isFinished = true;
        if (currentDay <= bestBefore){
            return score;
        } else {
            scoreTwo = score - (currentDay- bestBefore);
            if (scoreTwo < 1 ){
                scoreTwo = 1;
            }
            return scoreTwo;
        }
    }
    public boolean readyToGo(){
        if (woking.size() != roles.size()){
            return false;
        } else {
            return true;
        }
    }

}
