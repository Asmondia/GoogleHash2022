import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Combiner {
    public Combiner(ArrayList<Project> projects, ArrayList<Contributor> contributors){
        boolean print = false;
        int projectsComplete = 0;
        int days = 0;
        int score = 0;
        while(projectsComplete < projects.size()){
            System.out.println("Days: " + days + " -  Projects Complete: " +  projectsComplete);
            ArrayList<Project> incompleteProjs = new ArrayList<>();
            for (int i = 0; i < projects.size(); i++){
                if(projects.get(i).getComplete()){
                    score = score + projects.get(i).getScore(days);
                    projectsComplete += 1;
                } else if (projects.get(i).woking.size() == 0){
                    incompleteProjs.add(projects.get(i));
                }
            }

            // Code here for assigning people



            //sort incompleteProjs
            Collections.sort(incompleteProjs, Comparator.comparingDouble(proj -> -proj.scorePerDay()));


            for (Project i : incompleteProjs) {
                boolean cantComplete = false;
                boolean isComplete = false;
                int roleLooking = 0;
                boolean panic = false;
                while (!isComplete) {
                    ArrayList<Contributor> availableConts = new ArrayList<>();
                    for (Contributor j : contributors) {
                        if (!j.isWorking) {
                            availableConts.add(j);
                        }
                    }


                    Collections.shuffle(availableConts);
                    boolean personFound = false;

                    boolean canMentor = false;
                    int skillReq = i.roles.get(roleLooking).skillLevel;
                    for (int people = 0; people < i.woking.size(); people++) {
                       // System.out.println("Checking here " + i.roles.get(roleLooking).skillName);
                        if (i.woking.get(people).contrib.getSkill(i.roles.get(roleLooking).skillName).skillLevel >= skillReq) {
                            canMentor = true;
                        }
                    }
                    int valueLookingFor;
                    if (canMentor) {
                        valueLookingFor = 1;
                    } else {
                        valueLookingFor = 2;
                    }

//                    if (!print){
//                        print = true;
//                        System.out.println("I hate this");
//                        System.out.println(availableConts.size());
//                        for (int fuckThis = 0; fuckThis < availableConts.size(); fuckThis++){
//                            availableConts.get(fuckThis).printSkills();
//                        }

                    int increment = 0;

                    while (!personFound) {
                        //System.out.println("Increment: "+ increment);
//                       // System.out.println(availableConts.get(increment).suitability(i.roles.get(roleLooking)) );
//                        System.out.println(availableConts.size() + " - " + increment);
//                        System.out.println(i.roles.size() + " - " + roleLooking);
                        if (availableConts.size() != 0 ) {
                            if (availableConts.get(increment).suitability(i.roles.get(roleLooking)) == valueLookingFor) {
                                personFound = true;

                                i.woking.add(new WorkingOn(availableConts.get(increment), i.roles.get(roleLooking)));
                                availableConts.get(increment).isWorking = true;
                            }
                        }
                        increment += 1;
                        if (increment >= availableConts.size()) {
                            if (valueLookingFor == 1) {
                                valueLookingFor = 2;
                                increment = 0;
                            } else {
                                personFound = true;
                                cantComplete = true;
                            }
                        }

                    }

                    roleLooking = roleLooking + 1;
                    if (roleLooking >= i.roles.size()){
                        isComplete = true;
                    }

                }
                if (cantComplete) {
                    for (WorkingOn contrib : i.woking) {
                        contrib.contrib.isWorking = false;
                    }
                    i.woking = new ArrayList<WorkingOn>();
                } else{
                    Output.projectsDone.add(i);
                }

            }




            for (int i = 0; i < projects.size(); i++){
                if(!projects.get(i).getComplete()){
                    if(projects.get(i).readyToGo()){
                        projects.get(i).daysWorkedOn += 1;
                    }
                }
            }



            days = days+1;
        }
    }
}
