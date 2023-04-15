import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.function.Function;

public class MazeSolver implements IMazeSolver {
	private static final int TRUE_WALL = Integer.MAX_VALUE;
	private static final int EMPTY_SPACE = 0;
	private static final List<Function<Room, Integer>> WALL_FUNCTIONS = Arrays.asList(
			Room::getNorthWall,
			Room::getEastWall,
			Room::getWestWall,
			Room::getSouthWall
	);
	private static final int[][] DELTAS = new int[][] {
			{ -1, 0 }, // North
			{ 0, 1 }, // East
			{ 0, -1 }, // West
			{ 1, 0 } // South
	};

	private Maze maze; //variable to store the maze to be initialized with
	private boolean[][] visited; //array to store whether the room has been visited
	private int[][] fearCount; //array to store the fearcount at the room after following a path
	PriorityQueue<RoomNode> priorityQueue; //priority queue to be used to order the rooms according to their fear level.
	private int startFear = 0;

	//Nested Class RoomNode data structure to store a room and its specific fear level.
	//Implements Comparable such that the priority queue can be utilised.
	public static class RoomNode implements Comparable<RoomNode> {
		private Room room;
		private int row;
		private int col;
		private int fearLevel;

		public RoomNode(Room room, int row, int col, int fearLevel) {
			this.room = room;
			this.row = row;
			this.col = col;
			this.fearLevel = fearLevel;
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

		@Override
		public int compareTo(RoomNode roomNode) {
			RoomNode curr = roomNode;
			if (this.fearLevel < curr.fearLevel) {
				return -1;
			} else if (this.fearLevel > curr.fearLevel) {
				return 1;
			} else {
				return 0;
			}
		}
	}

	public MazeSolver() {
		this.maze = null;
	}

	@Override
	public void initialize(Maze maze) {
		this.maze = maze;
		this.visited = new boolean[maze.getRows()][maze.getColumns()];
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
		fearCount = new int[this.maze.getRows()][this.maze.getColumns()]; //initialise the fearCount upon every pathSearch.
		priorityQueue = new PriorityQueue<>(maze.getRows() * maze.getColumns()); //initialise the priority queue upon every pathSearch.
		for (int i = 0; i < maze.getRows(); i++) {
			for (int j = 0; j < maze.getColumns(); j++) {
				this.visited[i][j] = false; //set all rooms to have not been visited first.
				this.fearCount[i][j] = TRUE_WALL; //set the fearCount of every room to be the max.
			}
		}
		Room startingRoom = this.maze.getRoom(startRow, startCol); //get the starting room to begin the path search.
		RoomNode startingNode = new RoomNode(startingRoom, startRow, startCol, startFear); //initialise the starting room node.
		priorityQueue.add(startingNode); //add the starting node to the priority queue.
		this.fearCount[startRow][startCol] = startFear; //as this is the starting room, the fearCount would be 0 as we started from here.

		while (!priorityQueue.isEmpty()) {
			RoomNode curr = priorityQueue.remove(); //get the curr RoomNode that we are at.
			int row = curr.getRow();
			int col = curr.getCol();

			//Check if the room has already been visited.
			//If it has not been, visit it.
			if (!visited[row][col]) {
				visited[row][col] = true; //set it to be visited.
				visitAllDirections(curr, row, col, curr.fearLevel); //recurse from the room to get all the directions to find the path with the lowest fear count.
			}

		}
		//Check if it is possible to reach the end room
		//If not possible, return null
		//Otherwise, return the value within the array which is the minimum fear count.
		return fearCount[endRow][endCol] == TRUE_WALL ? null : fearCount[endRow][endCol];
	}

	//Helper function to help abstract the checking of all directions.
	public void visitAllDirections(RoomNode roomNode, int row, int col, int fearLevel) {
		int currWallFearLevel; //variable to store the current fear level
		int newWallFearLevel; //variable to store the new fear level at the new node.

		for (int i = 0; i < 4; i++) {
			//get the next row and col for the room/fake wall
			int nextRow = row + DELTAS[i][0];
			int nextCol = col + DELTAS[i][1];

			if (!(nextRow < 0 || nextRow >= this.maze.getRows())) {
				if (!(nextCol < 0 || nextCol >= this.maze.getColumns())) {
					Function<Room, Integer> function = WALL_FUNCTIONS.get(i); //get the function to get the wall fear.
					int wallFear = function.apply(roomNode.getRoom()); //get the wallfear of the "room" that we are trying to go to.

					//If the wall is not a real wall and we have not visited that spot yet, then continue.
					if(!visited[nextRow][nextCol] && wallFear != TRUE_WALL) {
						//Get the current wall fear level at the current "room"
						//If it is not a wall, then return 1 as fear level increases by 1 as we move through the maze in empty spaces
						//Otherwise, return the wall's actual fear level
						currWallFearLevel = wallFear == EMPTY_SPACE ? 1 : wallFear;
						//Next, calculate the new wall fear level by adding the current one with the existing fearCount
						newWallFearLevel = currWallFearLevel + fearCount[row][col];
						//The fearCount at the next particular room/space will be the minimum between the newWallFearLevel and the original wall fear value.
						fearCount[nextRow][nextCol] = Math.min(newWallFearLevel, fearCount[nextRow][nextCol]);
						//Then retrieve the next room and add it to the priority queue.
						Room nextRoom = this.maze.getRoom(nextRow, nextCol);
						RoomNode nextNode = new RoomNode(nextRoom, nextRow, nextCol, fearCount[nextRow][nextCol]);
						priorityQueue.add(nextNode);
					}
				}
			}
		}
	}

	@Override
	public Integer bonusSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		// TODO: Find minimum fear level given new rules.
		if (maze == null) {
			throw new Exception("Oh no! You cannot call me without initialising the maze!");
		}
		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||
		endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}
		fearCount = new int[maze.getRows()][maze.getColumns()];
		visited = new boolean[maze.getRows()][maze.getColumns()];
		priorityQueue = new PriorityQueue<>(maze.getRows() * maze.getColumns());
		for (int i = 0; i < maze.getRows(); i++) {
			for (int j = 0; j < maze.getColumns(); j++) {
				fearCount[i][j] = TRUE_WALL;
				visited[i][j] = false;
			}
		}
		priorityQueue.add(new RoomNode(maze.getRoom(startRow, startCol), startRow, startCol, startFear));
		fearCount[startRow][startCol] = startFear;
		while (!priorityQueue.isEmpty()) {
			RoomNode curr = priorityQueue.remove();
			if (!visited[curr.row][curr.col]) {
				visited[curr.row][curr.col] = true;
				visitAllDirectionBonus(curr, curr.row, curr.col);
			}
		}
		return fearCount[endRow][endCol] == TRUE_WALL ? null : fearCount[endRow][endCol];
	}

	public void visitAllDirectionBonus(RoomNode roomNode, int row, int col) {
		int newWallFearLevel;

		for (int i = 0; i < 4; i++) {
			int nextRow = row + DELTAS[i][0];
			int nextCol = col + DELTAS[i][1];
			if (!(nextRow < 0 || nextRow >= maze.getRows())) {
				if (!(nextCol < 0 || nextCol >= maze.getColumns())) {
					Function<Room, Integer> function = WALL_FUNCTIONS.get(i);
					int wallFear = function.apply(roomNode.getRoom());
					if (!visited[nextRow][nextCol] && wallFear != TRUE_WALL) {
						newWallFearLevel = Math.max(wallFear, fearCount[row][col]);
						fearCount[nextRow][nextCol] = wallFear == 0 ? Math.min(1 + fearCount[row][col], 
						fearCount[nextRow][nextCol]) : Math.min(newWallFearLevel, fearCount[nextRow][nextCol]);
						RoomNode next = new RoomNode(maze.getRoom(nextRow, nextCol), nextRow, nextCol, fearCount[nextRow][nextCol]);
						priorityQueue.add(next);
					}
				}
			}
		}
	}

	@Override
	public Integer bonusSearch(int startRow, int startCol, int endRow, int endCol, int sRow, int sCol) throws Exception {
		if (maze == null) {
			throw new Exception("Oh no! You cannot call me without initialising the maze!");
		}
		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||
		endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}

		Integer normalPath = bonusSearch(startRow, startCol, endRow, endCol);

		if (normalPath == null) {
			return null;
		}

		Integer fromSpecial = bonusSearch(startRow, startCol, sRow, sCol);

		if (fromSpecial == null) {
			return normalPath;
		}

		this.startFear = -1;
		Integer specialPath = bonusSearch(sRow, sCol, endRow, endCol);
		this.startFear = 0;

		return specialPath != null ? Math.min(normalPath, specialPath) : normalPath;
	}

	public static void main(String[] args) {
		try {
			Maze maze = Maze.readMaze("haunted-maze-sample.txt");
			IMazeSolver solver = new MazeSolver();
			solver.initialize(maze);

			System.out.println(solver.pathSearch(0, 0, 0, 1));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
