import java.util.LinkedList;
import java.util.Queue;

public class MazeSolver implements IMazeSolver {
	private static final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3;
	private static int[][] DELTAS = new int[][] {
		{ -1, 0 }, // North
		{ 1, 0 }, // South
		{ 0, 1 }, // East
		{ 0, -1 } // West
	};

	private Maze maze; //field to store the Maze to be solved
	private boolean solved; //boolean to signify if the Maze has been solved
	private boolean[][] visited; //boolean 2d array to denote whether a particular room has been visited
	private LinkedList<Integer> listOfSteps; //LinkedList to help store the number of rooms available at each step

	/**
	 * RoomNode class created to be stored within the Queue.
	 */
	static class RoomNode {
		private Room room; //the room in question
		private int row; //the row that the room is at
		private int col; //the column that the room is at
		private RoomNode parent; //the previous room before entering this room

		//Constructor for the RoomNode
		public RoomNode(Room room, int row, int col, RoomNode parent) {
			this.room = room;
			this.row = row;
			this.col = col;
			this.parent = parent;
		}

		public int getRow() {
			return this.row;
		}

		public int getCol() {
			return this.col;
		}

		public Room getRoom() {
			return this.room;
		}
	}

	//Constructor for the MazeSolver
	public MazeSolver() {
		this.solved = false;
		this.maze = null;
	}

	@Override
	public void initialize(Maze maze) {
		this.maze = maze;
		this.visited = new boolean[this.maze.getRows()][this.maze.getColumns()];
		this.solved = false;
	}

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		if (maze == null) {
			throw new Exception("Oh no! You cannot call me without initialising the maze!");
		}

		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||
		endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}

		//For loop to initialise the pathSearch by putting everything as not visited.
		for (int i = 0; i < maze.getRows(); i++) {
			for (int j = 0; j < maze.getColumns(); j++) {
				this.visited[i][j] = false;
				maze.getRoom(i, j).onPath = false;
			}
		}

		//Create a new list and mark the maze as unsolved.
		this.listOfSteps = new LinkedList<>();
		this.solved = false;
		int noOfSteps = 0;
		Queue<RoomNode> frontier = new LinkedList<>(); //Queue created to determine the frontier of the BFS
		Room startingRoom = maze.getRoom(startRow, startCol); //to get the starting room of the maze
		this.visited[startRow][startCol] = true; //set the starting room to be visited
		RoomNode start = new RoomNode(startingRoom, startRow, startCol, null); //create a node for the starting room
		Room endingRoom = maze.getRoom(endRow, endCol); //get the ending room of the maze
		frontier.add(start); //add the starting room to the queue.
		RoomNode endPoint = null; //set the endPoint as null, which will then change to the endingRoom if path reaches it
		int endcount = 0; //the number of steps taken to reach the endPoint.

		//While there is a frontier for the BFS, then add the number of frontiers at that point to the listofSteps, as these are the number of rooms that can be traversed.
		while (!frontier.isEmpty()) {
			this.listOfSteps.add(frontier.size());

			Queue<RoomNode> nextFrontier = new LinkedList<>(); //create a new Queue for the nextFrontier
			for (RoomNode roomNode : frontier) { //for the roomNodes within the frontier Queue,
				Room room = roomNode.getRoom(); //get the room, row, and column of that roomNode.
				int row = roomNode.getRow();
				int col = roomNode.getCol();

				if (room.equals(endingRoom)) {
					this.solved = true; //if the room is equivalent to the endingRoom, then marked as solved
					endcount = noOfSteps; //record the number of steps as the steps taken to reach the endPoint.
					endPoint = roomNode; //set the endPoint to be the roomNode, which is the endingRoom.
				}

				//Otherwise, check for the nextFrontiers by checking its North, South, East and West.
				if (!room.hasNorthWall() && !visited[row - 1][col]) {
					Room north = maze.getRoom(row - 1, col);
					nextFrontier.add(new RoomNode(north, row - 1, col, roomNode));
					visited[row - 1][col] = true;
				}

				if (!room.hasSouthWall() && !visited[row + 1][col]) {
					Room south = maze.getRoom(row + 1, col);
					nextFrontier.add(new RoomNode(south, row + 1, col, roomNode));
					visited[row + 1][col] = true;
				}

				if (!room.hasEastWall() && !visited[row][col + 1]) {
					Room east = maze.getRoom(row, col + 1);
					nextFrontier.add(new RoomNode(east, row, col + 1, roomNode));
					visited[row][col + 1] = true;
				}

				if (!room.hasWestWall() && !visited[row][col - 1]) {
					Room west = maze.getRoom(row, col - 1);
					nextFrontier.add(new RoomNode(west, row, col - 1, roomNode));
					visited[row][col - 1] = true;
				}
			}
			noOfSteps++;
			frontier = nextFrontier; //then go through the nextFrontiers as the current frontier.
		}
		//If maze is solved, then set the OnPath for both starting and endingPoint as true.
		if (solved) {
			maze.getRoom(startRow, startCol).onPath = true;
			endPoint.getRoom().onPath = true;
			while (endPoint.parent != null) {
				endPoint.parent.getRoom().onPath = true; //then work back up to set the parent to be on path
				endPoint = endPoint.parent;
			}
			return endcount; //return the number of steps taken for this shortest path.
		} else {
			return null; //otherwise return null if there is no valid path
		}
	}

	@Override
	public Integer numReachable(int k) throws Exception {
		if (k < 0) {
			throw new Exception("Invalid number of steps!");
		} else if (k >= this.listOfSteps.size()) {
			return 0; //if k exceeds the number of steps available to be made, then return 0.
		} else { 
			//otherwise return the number of rooms whose min number of steps to reach it from the most recent pathSearch
			//is k.
			return this.listOfSteps.get(k); 
		}
	}

	public static void main(String[] args) {
		// Do remember to remove any references to ImprovedMazePrinter before submitting
		// your code!
		try {
			Maze maze = Maze.readMaze("maze-sample.txt");
			IMazeSolver solver = new MazeSolver();
			solver.initialize(maze);

			System.out.println(solver.pathSearch(0, 0, 2, 3));
			MazePrinter.printMaze(maze);

			for (int i = 0; i <= 9; ++i) {
				System.out.println("Steps " + i + " Rooms: " + solver.numReachable(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
