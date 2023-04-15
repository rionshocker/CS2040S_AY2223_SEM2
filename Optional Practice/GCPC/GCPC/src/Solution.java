import java.util.TreeSet;

public class Solution {
    TreeSet<Team> teams = new TreeSet<>();
    Team[] listOfTeams;

    public class Team implements Comparable<Team>{
        int teamNum;
        int numOfSolutions;
        long penalty;

        public Team(int num) {
            this.teamNum = num;
            this.numOfSolutions = 0;
            this.penalty = 0;
        }

        @Override
        public int compareTo(Team t) {
            if (numOfSolutions != t.numOfSolutions) {
                return t.numOfSolutions - numOfSolutions;
            } else {
                if (penalty != t.penalty) {
                    return (int) (penalty - t.penalty);
                } else {
                    return teamNum - t.teamNum;
                }
            }
        }
    }

    public Solution(int numTeams) {
        listOfTeams = new Team[numTeams];
        for (int i = 0; i < listOfTeams.length; i++) {
            listOfTeams[i] = new Team(i + 1);
        }
    }

    public int update(int team, long newPenalty){
        teams.remove(listOfTeams[team - 1]);
        listOfTeams[team - 1].numOfSolutions++;
        listOfTeams[team - 1].penalty += newPenalty;

        if (team == 1) {
            while (!teams.isEmpty()) {
                Team t = teams.last();
                if (listOfTeams[0].compareTo(t) < 0) {
                    teams.remove(t);
                } else {
                    break;
                }
            }
        } else {
            if (listOfTeams[0].compareTo(listOfTeams[team - 1]) > 0) {
                teams.add(listOfTeams[team - 1]);
            }
        }
        return teams.size() + 1;
    }

}
