import java.util.ArrayList;

public class BellmanFord {
    // DO NOT MODIFY THE TWO STATIC VARIABLES BELOW
    public static int INF = 20000000;
    public static int NEGINF = -20000000;

    // TODO: add additional attributes and/or variables needed here, if any
    public ArrayList<ArrayList<IntPair>> adjList;
    public int[] distance;

    public BellmanFord(ArrayList<ArrayList<IntPair>> adjList) {
        this.adjList = adjList;
        distance = new int[adjList.size()];
    }

    // TODO: add additional methods here, if any
    public void computeShortestPaths(int source) {
        distance[source] = 0;
        for (int i = 0; i < distance.length; i++) {
            if (i != source) {
                distance[i] = BellmanFord.INF;
            }
        }

        for (int j = 1; j < adjList.size(); j++) {
            for (int k = 0; k < adjList.size(); k++) {
                for (IntPair intPair : adjList.get(k)) {
                    if (distance[k] != BellmanFord.INF) {
                        if (distance[intPair.first] > intPair.second + distance[k] && distance[intPair.first] != BellmanFord.NEGINF) {
                            distance[intPair.first] = intPair.second + distance[k];
                        }
                    }
                }
            }
        }

        for (int k = 0; k < adjList.size(); k++) {
            for (IntPair intPair : adjList.get(k)) {
                if (distance[intPair.first] > intPair.second + distance[k] && distance[intPair.first] != BellmanFord.NEGINF) {
                    for (int j = 1; j <= adjList.size(); j++) {
                        for (int n = 0; n < adjList.size(); n++) {
                            for (IntPair pair : adjList.get(n)) {
                                if (distance[n] != BellmanFord.INF && distance[pair.first] > pair.second + distance[n] && distance[pair.first] != BellmanFord.NEGINF) {
                                    distance[pair.first] = NEGINF;
                                } 
                            }
                        }
                    }
                }
            }
        }
    }

    public int getDistance(int node) { 
        if (node >= 0 && node < adjList.size()) {
            return distance[node];
        } else {
            return INF;
        }
    }

}
